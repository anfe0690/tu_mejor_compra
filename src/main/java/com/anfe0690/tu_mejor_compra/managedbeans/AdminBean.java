package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.entity.Venta;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class AdminBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AdminBean.class);
	// Datos
	private boolean identificado;
	private boolean baseDeDatosLimpiada;
	// Campos
	private String password;
	// Beans
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeCompras manejadorDeCompras;
	@EJB
	private ManejadorDeVentas manejadorDeVentas;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public String identificarse() {
		if (password.equalsIgnoreCase("asd123")) {
			identificado = true;
			logger.info("Identificado como Administrador");
		}
		return null;
	}

	public void limpiarBaseDeDatos() {
		logger.info("Inicia limpieza de base de datos...");
		logger.debug("Eliminadas {} entidades del tipo Usuario", manejadorDeUsuarios.removerTodosLosUsuarios());
		logger.debug("Eliminadas {} entidades del tipo Compra", manejadorDeCompras.removerTodasLasCompras());
		logger.debug("Eliminadas {} entidades del tipo Venta", manejadorDeVentas.removerTodasLasVentas());

		for (Producto p : manejadorDeProductos.obtenerTodosLosProductos()) {
			File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + p.getDireccionImagen());
			try {
				Files.deleteIfExists(f.toPath());
				logger.debug("Eliminada la imagen \"{}\"", f);
			} catch (IOException ex) {
				logger.error(null, ex);
			}
		}
		logger.debug("Eliminadas {} entidades del tipo Producto", manejadorDeProductos.removerTodosLosProductos());

		baseDeDatosLimpiada = true;
		logger.info("Base de datos limpiada.");
	}

	public String limpiarRestaurarBaseDeDatos() {
		limpiarBaseDeDatos();
		logger.info("Inicia restauracion de base de datos...");

		// Crear usuario Andres
		Usuario usuarioAndres = crearUsuarioAndres();
		List<Producto> productosAndres = new ArrayList<>(usuarioAndres.getProductos());

		// Crear usuario Carlos
		Usuario usuarioCarlos = crearUsuarioCarlos();
		List<Producto> productosCarlos = new ArrayList<>(usuarioCarlos.getProductos());

		// Crear usuario Fernando
		Usuario usuarioFernando = crearUsuarioFernando();
		List<Producto> productosFernando = new ArrayList<>(usuarioFernando.getProductos());

		// Crear compras y ventas
		crearComprasVentasAndres(usuarioAndres, productosAndres, productosCarlos, productosFernando);
		crearComprasVentasCarlos(usuarioCarlos, productosAndres, productosCarlos);
		crearComprasVentasFernando(usuarioFernando, productosAndres, productosFernando);

		logger.info("La base de datos fue restaurada.");
		return "index.xhtml?faces-redirect=true";
	}

	private Usuario crearUsuarioAndres() {
		List<Producto> productosAndres = new ArrayList<>();
		// 1
		Producto p = new Producto("Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me", "1.139.000",
				"andres/" + "samsung-galaxy-s4.jpg", Categoria.TELEFONOS_INTELIGENTES);
		productosAndres.add(p);
		// 2
		p = new Producto("Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella", "1.619.900", "andres/" + "iphone-5s.jpg",
				Categoria.TELEFONOS_INTELIGENTES);
		productosAndres.add(p);
		// 3
		p = new Producto("Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram", "1.349.990", "andres/" + "lg-g2.jpg",
				Categoria.TELEFONOS_INTELIGENTES);
		productosAndres.add(p);
		//
		Usuario usuarioAndres = new Usuario();
		usuarioAndres.setNombre("andres");
		usuarioAndres.setContrasena("123");
		usuarioAndres.setNombreContacto("Andres Felipe Munoz");
		usuarioAndres.setCorreo("andresfe@gmail.com");
		usuarioAndres.setTelefonos("310 234 5678");
		usuarioAndres.setCiudad("Cali");
		usuarioAndres.setDireccion("Carrera 24 #45-05");
		usuarioAndres.setBanco("Banco Bogota");
		usuarioAndres.setNumeroCuenta("123-4623");
		usuarioAndres.setProductos(new HashSet<>(productosAndres));
		for (Producto producto : usuarioAndres.getProductos()) {
			crearImagen(producto);
		}
		manejadorDeUsuarios.guardarUsuario(usuarioAndres);
		return usuarioAndres;
	}

	private Usuario crearUsuarioCarlos() {
		List<Producto> productosCarlos = new ArrayList<>();
		// 1
		Producto p
				= new Producto("Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus", "1.400.000", "carlos/" + "playstation-4.jpg",
						Categoria.CONSOLAS_VIDEO_JUEGOS);
		productosCarlos.add(p);
		// 2
		p
				= new Producto("Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base", "704.990", "carlos/" + "wii-u.jpg",
						Categoria.CONSOLAS_VIDEO_JUEGOS);
		productosCarlos.add(p);
		// 3
		p
				= new Producto("Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2", "1.449.990", "carlos/" + "xbox-one.jpg",
						Categoria.CONSOLAS_VIDEO_JUEGOS);
		productosCarlos.add(p);
		//
		Usuario usuarioCarlos = new Usuario();
		usuarioCarlos.setNombre("carlos");
		usuarioCarlos.setContrasena("456");
		usuarioCarlos.setNombreContacto("Carlos Jose Perez");
		usuarioCarlos.setCorreo("carlosperez@gmail.com");
		usuarioCarlos.setTelefonos("311 934 3045");
		usuarioCarlos.setCiudad("Medellin");
		usuarioCarlos.setDireccion("Calle 56 #83-41");
		usuarioCarlos.setBanco("Bancolombia");
		usuarioCarlos.setNumeroCuenta("456-1856");
		usuarioCarlos.setProductos(new HashSet<>(productosCarlos));
		for (Producto producto : usuarioCarlos.getProductos()) {
			crearImagen(producto);
		}
		manejadorDeUsuarios.guardarUsuario(usuarioCarlos);
		return usuarioCarlos;
	}

	private Usuario crearUsuarioFernando() {
		List<Producto> productosFernando = new ArrayList<>();
		// 1
		Producto p = new Producto("Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb", "919.000", "fernando/" + "google-nexus-10.jpg",
				Categoria.TABLETAS);
		productosFernando.add(p);
		// 2
		p = new Producto("Xperia Tablet Sony Z 32gb", "840.000", "fernando/" + "tablet-sony-xperia-z.jpg", Categoria.TABLETAS);
		productosFernando.add(p);
		// 3
		p = new Producto("Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1", "598.000", "fernando/" + "toshiba-excite.jpg",
				Categoria.TABLETAS);
		productosFernando.add(p);
		//
		Usuario usuarioFernando = new Usuario();
		usuarioFernando.setNombre("fernando");
		usuarioFernando.setContrasena("789");
		usuarioFernando.setNombreContacto("Fernando Rodriguez");
		usuarioFernando.setCorreo("fernandorodriguez@gmail.com");
		usuarioFernando.setTelefonos("315 285 0948");
		usuarioFernando.setCiudad("Bogota");
		usuarioFernando.setDireccion("Carrera 89 #34-84");
		usuarioFernando.setBanco("Avvillas");
		usuarioFernando.setNumeroCuenta("845-3029");
		usuarioFernando.setProductos(new HashSet<>(productosFernando));
		// usuarioFernando.setProductos(productosFernando);
		for (Producto producto : usuarioFernando.getProductos()) {
			crearImagen(producto);
		}
		manejadorDeUsuarios.guardarUsuario(usuarioFernando);
		return usuarioFernando;
	}

	private void crearComprasVentasAndres(Usuario usuarioAndres, List<Producto> productosAndres, List<Producto> productosCarlos,
			List<Producto> productosFernando) {
		// Compras
		List<Compra> compras = new ArrayList<>();
		// 1
		Compra c = new Compra();
		c.setVendedor("carlos");
		c.setProducto(productosCarlos.get(0));
		c.setEstado(Estado.ESPERANDO_PAGO);
		compras.add(c);
		// 2
		c = new Compra();
		c.setVendedor("fernando");
		c.setProducto(productosFernando.get(2));
		c.setEstado(Estado.EN_ENVIO);
		compras.add(c);
		// Ventas
		List<Venta> ventas = new ArrayList<>();
		// 1
		Venta v = new Venta();
		v.setComprador("carlos");
		v.setProducto(productosAndres.get(1));
		v.setEstado(Estado.ESPERANDO_PAGO);
		ventas.add(v);
		// 2
		v = new Venta();
		v.setComprador("fernando");
		v.setProducto(productosAndres.get(2));
		v.setEstado(Estado.TERMINADO);
		ventas.add(v);
		usuarioAndres.setCompras(new HashSet<>(compras));
		usuarioAndres.setVentas(new HashSet<>(ventas));
		for (Compra compra : compras) {
			manejadorDeCompras.guardarCompra(compra);
		}
		for (Venta venta : ventas) {
			manejadorDeVentas.guardarVenta(venta);
		}
		manejadorDeUsuarios.mergeUsuario(usuarioAndres);
	}

	private void crearComprasVentasCarlos(Usuario usuarioCarlos, List<Producto> productosAndres, List<Producto> productosCarlos) {
		// Compras
		List<Compra> compras = new ArrayList<>();
		// 1
		Compra c = new Compra();
		c.setVendedor("andres");
		c.setProducto(productosAndres.get(1));
		c.setEstado(Estado.ESPERANDO_PAGO);
		compras.add(c);
		// Ventas
		List<Venta> ventas = new ArrayList<>();
		// 1
		Venta v = new Venta();
		v.setComprador("andres");
		v.setProducto(productosCarlos.get(0));
		v.setEstado(Estado.ESPERANDO_PAGO);
		ventas.add(v);
		//
		usuarioCarlos.setCompras(new HashSet<>(compras));
		usuarioCarlos.setVentas(new HashSet<>(ventas));
		for (Compra compra : compras) {
			manejadorDeCompras.guardarCompra(compra);
		}
		for (Venta venta : ventas) {
			manejadorDeVentas.guardarVenta(venta);
		}
		manejadorDeUsuarios.mergeUsuario(usuarioCarlos);
	}

	private void crearComprasVentasFernando(Usuario usuarioFernando, List<Producto> productosAndres, List<Producto> productosFernando) {
		// Compras
		List<Compra> compras = new ArrayList<>();
		// 1
		Compra c = new Compra();
		c.setVendedor("andres");
		c.setProducto(productosAndres.get(2));
		c.setEstado(Estado.TERMINADO);
		compras.add(c);
		// Ventas
		List<Venta> ventas = new ArrayList<>();
		// 1
		Venta v = new Venta();
		v.setComprador("andres");
		v.setProducto(productosFernando.get(2));
		v.setEstado(Estado.EN_ENVIO);
		ventas.add(v);
		//
		usuarioFernando.setCompras(new HashSet<>(compras));
		usuarioFernando.setVentas(new HashSet<>(ventas));
		for (Compra compra : compras) {
			manejadorDeCompras.guardarCompra(compra);
		}
		for (Venta venta : ventas) {
			manejadorDeVentas.guardarVenta(venta);
		}
		manejadorDeUsuarios.mergeUsuario(usuarioFernando);
	}

	private void crearImagen(Producto producto) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//		String dirOrigenBase = ec.getRealPath("resources\\images\\restaurar") + "\\" + usuario.getNombre() + "\\";
		String dirOrigenBase = null;
		try {
			dirOrigenBase = ec.getResource("/resources/images/restaurar/").getPath();
		} catch (MalformedURLException e) {
			logger.error(null, e);
		}
		logger.debug("dirOrigenBase = {}", dirOrigenBase);
		String dirDestinoBase = System.getProperty(WebContainerListener.DIR_DATOS);
		logger.debug("dirDestinoBase = {}", dirDestinoBase);
		File fOrigen = new File(dirOrigenBase + producto.getDireccionImagen());
		File fDestino = new File(dirDestinoBase + producto.getDireccionImagen());
		if (!fDestino.exists()) {
			try {
				Files.copy(fOrigen.toPath(), fDestino.toPath());
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
				logger.error(null, ex);
			}
		}
	}

	// Getters y Setters
	public boolean isIdentificado() {
		return identificado;
	}

	public boolean isBaseDeDatosLimpiada() {
		return baseDeDatosLimpiada;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
