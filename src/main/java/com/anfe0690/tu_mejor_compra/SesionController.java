/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andres
 */
@Named
@SessionScoped
public class SesionController implements Serializable {

	private static final long serialVersionUID = 42L;
    private static final MiLogger miLogger = new MiLogger(SesionController.class);

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
        miLogger.log("postConstruct");
		// TODO: Cuando se crean los usuarios por primera vez, no se crean las imagenes
		// ANDRES
		Usuario usu = null;
		try {
			usu = manejadorDeUsuarios.buscarUsuarioPorNombre("andres");
		} catch (IllegalArgumentException e) {
			miLogger.error(e);
		}
        // Usuarios
        Usuario usuarioAndres = null;
        Usuario usuarioCarlos= null;
        Usuario usuarioFernando = null;
        // Productos
		List<Producto> productosAndres = null;
		List<Producto> productosCarlos = null;
		List<Producto> productosFernando = null;
		if (usu == null) {
			// ################## Andres
			productosAndres = new ArrayList<>();
			// 1
			Producto p = new Producto("samsung-galaxy-s4.jpg", "Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me", "1.139.000", Categoria.TELEFONOS_INTELIGENTES);
			productosAndres.add(p);
			// 2
			p = new Producto("iphone-5s.jpg", "Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella", "1.619.900", Categoria.TELEFONOS_INTELIGENTES);
			productosAndres.add(p);
			// 3
			p = new Producto("lg-g2.jpg", "Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram", "1.349.990", Categoria.TELEFONOS_INTELIGENTES);
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
			usuarioAndres.setProductos(productosAndres);
            manejadorDeUsuarios.guardarUsuario(usuarioAndres);

			// ################## Carlos
			productosCarlos = new ArrayList<>();
			// 1
			p = new Producto("playstation-4.jpg", "Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus", "1.400.000", Categoria.CONSOLAS_VIDEO_JUEGOS);
			productosCarlos.add(p);
			// 2
			p = new Producto("wii-u.jpg", "Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base", "704.990", Categoria.CONSOLAS_VIDEO_JUEGOS);
			productosCarlos.add(p);
			// 3
			p = new Producto("xbox-one.jpg", "Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2", "1.449.990", Categoria.CONSOLAS_VIDEO_JUEGOS);
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
			usuarioCarlos.setProductos(productosCarlos);
			manejadorDeUsuarios.guardarUsuario(usuarioCarlos);

			// ################## Fernando
			productosFernando = new ArrayList<>();
			// 1
			p = new Producto("google-nexus-10.jpg", "Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb", "919.000", Categoria.TABLETAS);
			productosFernando.add(p);
			// 2
			p = new Producto("tablet-sony-xperia-z.jpg", "Xperia Tablet Sony Z 32gb", "840.000", Categoria.TABLETAS);
			productosFernando.add(p);
			// 3
			p = new Producto("toshiba-excite.jpg", "Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1", "598.000", Categoria.TABLETAS);
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
			usuarioFernando.setProductos(productosFernando);
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
            //
            usuarioAndres.setCompras(compras);
            usuarioAndres.setVentas(ventas);
//            for(Compra compra:compras){
//                manejadorDeCompras.guardarCompra(compra);
//            }
//            for(Venta venta:ventas){
//                manejadorDeVentas.guardarVenta(venta);
//            }
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
            usuarioCarlos.setCompras(compras);
            usuarioCarlos.setVentas(ventas);
//            for(Compra compra:compras){
//                manejadorDeCompras.guardarCompra(compra);
//            }
//            for(Venta venta:ventas){
//                manejadorDeVentas.guardarVenta(venta);
//            }
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
            usuarioFernando.setCompras(compras);
            usuarioFernando.setVentas(ventas);
//            for(Compra compra:compras){
//                manejadorDeCompras.guardarCompra(compra);
//            }
//            for(Venta venta:ventas){
//                manejadorDeVentas.guardarVenta(venta);
//            }
            manejadorDeUsuarios.mergeUsuario(usuarioFernando);
            
            miLogger.log("Usuarios inicializados.");
		}
	}

	@PreDestroy
	public void preDestroy() {
		miLogger.log("preDestroy");
	}

	public void iniciarSesion() {
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

}
