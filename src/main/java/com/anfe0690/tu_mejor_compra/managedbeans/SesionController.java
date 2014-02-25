package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Venta;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class SesionController implements Serializable {

	private static final long serialVersionUID = 42L;
	@PersistenceContext
	private EntityManager em;
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeCompras manejadorDeCompras;
	@EJB
	private ManejadorDeVentas manejadorDeVentas;
	private String campoNombreUsuario;
	private String campoContrasena;
	private boolean sesionIniciada = false;
	private Usuario usuario;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(SesionController.class.getName()).log(Level.INFO, "postConstruct");
		// ANDRES
		Usuario usu = null;
		try {
			usu = manejadorDeUsuarios.buscarUsuarioPorNombre("andres");
		} catch (IllegalArgumentException e) {
			Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, e);
		}
		// Usuarios
		Usuario usuarioAndres = null;
		Usuario usuarioCarlos = null;
		Usuario usuarioFernando = null;
		// Productos
		List<Producto> productosAndres = null;
		List<Producto> productosCarlos = null;
		List<Producto> productosFernando = null;
		if (usu == null) {
			// ################## Andres
			productosAndres = new ArrayList<>();
			// 1
			Producto p =
					new Producto("samsung-galaxy-s4.jpg", "Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me", "1.139.000",
							Categoria.TELEFONOS_INTELIGENTES);
			productosAndres.add(p);
			// 2
			p =
					new Producto("iphone-5s.jpg", "Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella", "1.619.900",
							Categoria.TELEFONOS_INTELIGENTES);
			productosAndres.add(p);
			// 3
			p =
					new Producto("lg-g2.jpg", "Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram", "1.349.990",
							Categoria.TELEFONOS_INTELIGENTES);
			productosAndres.add(p);
			//
			usuarioAndres = new Usuario();
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
			// usuarioAndres.setProductos(productosAndres);
			for (Producto producto : usuarioAndres.getProductos()) {
				crearImagen(usuarioAndres, producto);
			}
			manejadorDeUsuarios.guardarUsuario(usuarioAndres);
			// ################## Carlos
			productosCarlos = new ArrayList<>();
			// 1
			p =
					new Producto("playstation-4.jpg", "Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus", "1.400.000",
							Categoria.CONSOLAS_VIDEO_JUEGOS);
			productosCarlos.add(p);
			// 2
			p =
					new Producto("wii-u.jpg", "Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base", "704.990",
							Categoria.CONSOLAS_VIDEO_JUEGOS);
			productosCarlos.add(p);
			// 3
			p =
					new Producto("xbox-one.jpg", "Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2", "1.449.990",
							Categoria.CONSOLAS_VIDEO_JUEGOS);
			productosCarlos.add(p);
			//
			usuarioCarlos = new Usuario();
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
			// usuarioCarlos.setProductos(productosCarlos);
			for (Producto producto : usuarioCarlos.getProductos()) {
				crearImagen(usuarioCarlos, producto);
			}
			manejadorDeUsuarios.guardarUsuario(usuarioCarlos);
			// ################## Fernando
			productosFernando = new ArrayList<>();
			// 1
			p =
					new Producto("google-nexus-10.jpg", "Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb", "919.000",
							Categoria.TABLETAS);
			productosFernando.add(p);
			// 2
			p = new Producto("tablet-sony-xperia-z.jpg", "Xperia Tablet Sony Z 32gb", "840.000", Categoria.TABLETAS);
			productosFernando.add(p);
			// 3
			p =
					new Producto("toshiba-excite.jpg", "Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1", "598.000",
							Categoria.TABLETAS);
			productosFernando.add(p);
			//
			usuarioFernando = new Usuario();
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
				crearImagen(usuarioFernando, producto);
			}
			manejadorDeUsuarios.guardarUsuario(usuarioFernando);
			// ################## Agregar compras y productos ##################
			// ################## Andres
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
			// ################## Carlos
			// Compras
			compras = new ArrayList<>();
			// 1
			c = new Compra();
			c.setVendedor("andres");
			c.setProducto(productosAndres.get(1));
			c.setEstado(Estado.ESPERANDO_PAGO);
			compras.add(c);
			// Ventas
			ventas = new ArrayList<>();
			// 1
			v = new Venta();
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
			// ################## Fernando
			compras = new ArrayList<>();
			// 1
			c = new Compra();
			c.setVendedor("andres");
			c.setProducto(productosAndres.get(2));
			c.setEstado(Estado.TERMINADO);
			compras.add(c);
			// Ventas
			ventas = new ArrayList<>();
			// 1
			v = new Venta();
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
			Logger.getLogger(SesionController.class.getName()).log(Level.INFO, "Usuarios inicializados.");
		}
	}

	private void crearImagen(Usuario usuario, Producto producto) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//		String dirOrigenBase = ec.getRealPath("resources\\images\\restaurar") + "\\" + usuario.getNombre() + "\\";
		String dirOrigenBase = null;
		try {
			dirOrigenBase = ec.getResource("/resources/images/restaurar/" + usuario.getNombre() + "/").getPath();
		} catch (MalformedURLException e) {
			Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, e);
		}
		Logger.getLogger(SesionController.class.getName()).log(Level.INFO, "dirOrigenBase = " + dirOrigenBase);
		String dirDestinoBase = System.getProperty(WebContainerListener.DIR_DATOS) + usuario.getNombre() + "/";
		Logger.getLogger(SesionController.class.getName()).log(Level.INFO, "dirDestinoBase = " + dirDestinoBase);
		File fOrigen = new File(dirOrigenBase + producto.getNombreImagen());
		File fDestino = new File(dirDestinoBase + producto.getNombreImagen());
		if (!fDestino.exists()) {
			try {
				Files.copy(fOrigen.toPath(), fDestino.toPath());
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
				Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(SesionController.class.getName()).log(Level.INFO, "preDestroy");
	}

	public String iniciarSesion() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Usuario usuario = manejadorDeUsuarios.buscarUsuarioPorNombre(campoNombreUsuario);
			if (usuario != null && usuario.getContrasena().equals(campoContrasena)) {
				sesionIniciada = true;
				this.usuario = usuario;
			} else {
				fc.addMessage("sesion_form:input_sesion", new FacesMessage("Usuario y/o contraseña incorrectos"));
			}
		} catch (Exception e) {
			fc.addMessage("sesion_form:input_sesion", new FacesMessage(e.toString()));
		}
		return "";
	}

	public String cerrarSesion() throws IOException {
		sesionIniciada = false;
		this.usuario = null;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
		return "index?faces-redirect=true";
	}

	// Getters and Setters
	public String getCampoNombreUsuario() {
		return campoNombreUsuario;
	}

	public void setCampoNombreUsuario(String campoNombreUsuario) {
		this.campoNombreUsuario = campoNombreUsuario;
	}

	public String getCampoContrasena() {
		return campoContrasena;
	}

	public void setCampoContrasena(String campoContrasena) {
		this.campoContrasena = campoContrasena;
	}

	public boolean isSesionIniciada() {
		return sesionIniciada;
	}

	public void setSesionIniciada(boolean sesionIniciada) {
		this.sesionIniciada = sesionIniciada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String restaurarTodosLosDatos() {
		TypedQuery<Usuario> q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		for (Usuario u : q.getResultList()) {
			Logger.getLogger(SesionController.class.getName()).log(Level.INFO, u.toString());
			List<Venta> vs = new ArrayList<>(u.getVentas());
			u.setVentas(null);
			manejadorDeUsuarios.mergeUsuario(u);
			for (Venta v : vs) {
				manejadorDeVentas.removeVenta(v);
			}
			vs.clear();
			List<Compra> cs = new ArrayList<>(u.getCompras());
			u.setCompras(null);
			manejadorDeUsuarios.mergeUsuario(u);
			for (Compra c : cs) {
				manejadorDeCompras.removeCompra(c);
			}
			cs.clear();
			for (Producto p : u.getProductos()) {
				File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + u.getNombre() + "\\" + p.getNombreImagen());
				try {
					Files.deleteIfExists(f.toPath());
				} catch (Exception ex) {
					Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		for (Usuario u : q.getResultList()) {
			manejadorDeUsuarios.removeUsuario(u);
		}
		postConstruct();
		Logger.getLogger(SesionController.class.getName()).log(Level.INFO, "La base de datos fue restaurada.");
		return "index?faces-redirect=true";
	}
}
