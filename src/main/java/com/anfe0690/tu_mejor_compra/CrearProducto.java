/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class CrearProducto implements Serializable {

	@Inject
	private SesionController sesionController;
	private Part file;
	private String nombre;
	private String precio;
	private String categoria;

	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(CrearProducto.class.getName()).log(Level.INFO, "postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(CrearProducto.class.getName()).log(Level.INFO, "preDestroy");
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String crearProducto() {
		nombre = nombre.trim();
		precio = precio.trim();
        //logger.info("############## file.getHeader(\"content-disposition\")=" + file.getHeader("content-disposition"));
		// form-data; name="form_crear_producto:file"; filename="Crysis.jpg"
		Pattern p = Pattern.compile(".*filename\\=\"(.*)\"");
		Matcher m = p.matcher(file.getHeader("content-disposition"));
		m.find();
		//logger.info("############## m.find()=" + m.find());
		String fileName = m.group(1);
		//logger.info("############## fileName=" + fileName);

		Usuario usuario = sesionController.getUsuario();
		for (Producto producto : usuario.getProductos()) {
			if (producto.getNombre().equalsIgnoreCase(nombre) || producto.getNombreImagen().equalsIgnoreCase(fileName)) {
				Logger.getLogger(CrearProducto.class.getName()).log(Level.INFO, "Intento de agregar un producto con valores ya encontrados en la base de datos: \"" + nombre + "\" y \"" + fileName + "\"");
				return "perfil";
			}
		}

		File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + sesionController.getUsuario().getNombre() + "/" + fileName);
		Logger.getLogger(CrearProducto.class.getName()).log(Level.INFO, "f = " + f);
		try {
			f.createNewFile();
			OutputStream os = new FileOutputStream(f);
			InputStream is = file.getInputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.close();
			is.close();
		} catch (IOException ex) {
			Logger.getLogger(CrearProducto.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
		Producto producto = new Producto();
		producto.setNombreImagen(fileName);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setFechaDeCreacion(new Date());
		producto.setCategoria(Categoria.valueOf(categoria));

		usuario.getProductos().add(producto);
		manejadorDeUsuarios.mergeUsuario(usuario);
		usuario.setProductos(manejadorDeUsuarios.buscarUsuarioPorNombre(usuario.getNombre()).getProductos());

		Logger.getLogger(CrearProducto.class.getName()).log(Level.INFO, "Creado producto: " + producto.getNombre());

		return "perfil";
	}
}
