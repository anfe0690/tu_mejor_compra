package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class CrearProducto implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(CrearProducto.class);
	@Inject
	private SesionBean sesionController;
	private Part file;
	private String nombre;
	private String precio;
	private String categoria;

	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
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
		// form-data; name="form_crear_producto:file"; filename="Crysis.jpg"
		Pattern p = Pattern.compile(".*filename\\=\"(.*)\"");
		Matcher m = p.matcher(file.getHeader("content-disposition"));
		m.find();
		String fileName = m.group(1);

		Usuario usuario = sesionController.getUsuario();
		for (Producto producto : usuario.getProductos()) {
			if (producto.getNombre().equalsIgnoreCase(nombre)) {
				logger.warn("Intento de agregar un producto con valores ya encontrados en la base de datos: \"{}\"", nombre);
				return "perfil";
			}
		}

		String direccionImagen = sesionController.getUsuario().getNombre() + "/" + fileName;
		File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + direccionImagen);
		logger.debug("f = {}", f);
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
			logger.error(null, ex);
			return null;
		}
		Producto producto = new Producto();
		producto.setDireccionImagen(direccionImagen);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setFechaDeCreacion(new Date());
		producto.setCategoria(Categoria.valueOf(categoria));

		usuario.getProductos().add(producto);
		manejadorDeUsuarios.mergeUsuario(usuario);
		usuario.setProductos(manejadorDeUsuarios.buscarUsuarioPorNombre(usuario.getNombre()).getProductos());

		logger.info("Creado producto: {}", producto.getNombre());

		return "perfil";
	}
}
