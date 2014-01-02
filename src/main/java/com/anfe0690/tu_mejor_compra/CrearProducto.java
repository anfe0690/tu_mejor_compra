/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.Part;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class CrearProducto implements Serializable {

	private static final Logger logger = Logger.getLogger(CrearProducto.class.getName());

	@Inject
	private SesionController sesionController;
	private Part file;
	private String nombre;
	private String precio;
	private String categoria;

	@PostConstruct
	public void postConstruct() {
		logger.info("############## postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############## preDestroy");
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
				logger.log(Level.WARNING, "Intento de agregar un producto con valores ya encontrados en la base de datos: \"{0}\" y \"{1}\"", new Object[]{nombre, fileName});
				return "perfil";
			}
		}

		File f = new File("C:\\var\\tuMejorCompra\\img\\" + sesionController.getUsuario().getNombre() + "\\" + fileName);
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
			logger.log(Level.SEVERE, null, ex);
			return null;
		}
		Producto producto = new Producto();
		producto.setNombreImagen(fileName);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setFechaDeCreacion(new Date());
		producto.setCategoria(Categoria.valueOf(categoria));

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		usuario.getProductos().add(producto);
		em.merge(usuario);
		et.commit();

		em.close();
		emf.close();

		logger.log(Level.INFO, "############## Producto \"{0}\" creado", producto.getNombre());

		return "perfil";
	}
}
