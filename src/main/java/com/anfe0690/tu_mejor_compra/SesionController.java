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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andres
 */
@Named
@SessionScoped
public class SesionController implements Serializable {

	private static final long serialVersionUID = 42L;
	private static final Logger logger = Logger.getLogger(SesionController.class.getName());

	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;

	private String campoNombreUsuario;
	private String campoContrasena;
	private boolean sesionIniciada = false;
	private Usuario usuario;

	@PostConstruct
	public void postConstruct() {
		logger.info("############## postConstruct");
		// ANDRES
		Usuario usu = null;
		try {
			usu = manejadorDeUsuarios.buscarUsuarioPorNombre("andres");
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		if (usu == null) {
			// Productos
			List<Producto> productos = new ArrayList<>();
			// 1
			Producto p = new Producto();
			p.setNombreImagen("samsung-galaxy-s4.jpg");
			p.setNombre("Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me");
			p.setPrecio("1.139.000");
			productos.add(p);
			// 2
			p = new Producto();
			p.setNombreImagen("iphone-5s.jpg");
			p.setNombre("Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella");
			p.setPrecio("1.619.900");
			productos.add(p);
			// 3
			p = new Producto();
			p.setNombreImagen("lg-g2.jpg");
			p.setNombre("Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram");
			p.setPrecio("1.349.990");
			productos.add(p);
			
			// Compras
			List<Compra> compras = new ArrayList<>();
			// 1
			Compra c = new Compra();
			c.setVendedor("carlos");
			c.setIndiceProducto(0);
			c.setEstado(EstadoCompraVenta.ESPERANDO_PAGO);
			compras.add(c);
			// 2
			c = new Compra();
			c.setVendedor("fernando");
			c.setIndiceProducto(2);
			c.setEstado(EstadoCompraVenta.EN_ENVIO);
			compras.add(c);
			
			// Ventas
			List<Venta> ventas = new ArrayList<>();
			// 1
			Venta v = new Venta();
			v.setComprador("carlos");
			v.setIndiceProducto(1);
			v.setEstado(EstadoCompraVenta.EN_ENVIO);
			ventas.add(v);
			// 2
			v = new Venta();
			v.setComprador("fernando");
			v.setIndiceProducto(2);
			v.setEstado(EstadoCompraVenta.TERMINADO);
			ventas.add(v);
			
			// Usuario
			Usuario u = new Usuario();
			u.setNombre("andres");
			u.setContrasena("123");
			u.setNombreContacto("Andres Felipe Munoz");
			u.setCorreo("andresfe@gmail.com");
			u.setTelefonos("310 234 5678");
			u.setCiudad("Cali");
			u.setDireccion("Carrera 24 #45-05");
			u.setBanco("Banco Bogota");
			u.setNumeroCuenta("123-4623");
			
			u.setProductos(productos);
			u.setCompras(compras);
			u.setVentas(ventas);
			manejadorDeUsuarios.guardarUsuario(u);
			logger.info("############## creado usuario andres");
		}
		// CARLOS
		usu = null;
		try {
			usu = manejadorDeUsuarios.buscarUsuarioPorNombre("carlos");
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		if (usu == null) {
			// Productos
			List<Producto> productos = new ArrayList<>();
			// 1
			Producto p = new Producto();
			p.setNombreImagen("playstation-4.jpg");
			p.setNombre("Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus");
			p.setPrecio("1.400.000");
			productos.add(p);
			// 2
			p = new Producto();
			p.setNombreImagen("wii-u.jpg");
			p.setNombre("Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base");
			p.setPrecio("704.990");
			productos.add(p);
			// 3
			p = new Producto();
			p.setNombreImagen("xbox-one.jpg");
			p.setNombre("Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2");
			p.setPrecio("1.449.990");
			productos.add(p);
			// Usuario
			Usuario u = new Usuario();
			u.setNombre("carlos");
			u.setContrasena("456");
			u.setNombreContacto("Carlos Jose Perez");
			u.setCorreo("carlosperez@gmail.com");
			u.setTelefonos("311 934 3045");
			u.setCiudad("Medellin");
			u.setDireccion("Calle 56 #83-41");
			u.setBanco("Bancolombia");
			u.setNumeroCuenta("456-1856");
			u.setProductos(productos);
			manejadorDeUsuarios.guardarUsuario(u);
			logger.info("############## creado usuario carlos");
		}
		// FERNANDO
		usu = null;
		try {
			usu = manejadorDeUsuarios.buscarUsuarioPorNombre("fernando");
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		if (usu == null) {
			// Productos
			List<Producto> productos = new ArrayList<>();
			// 1
			Producto p = new Producto();
			p.setNombreImagen("google-nexus-10.jpg");
			p.setNombre("Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb");
			p.setPrecio("919.000");
			productos.add(p);
			// 2
			p = new Producto();
			p.setNombreImagen("tablet-sony-xperia-z.jpg");
			p.setNombre("Xperia Tablet Sony Z 32gb");
			p.setPrecio("840.000");
			productos.add(p);
			// 3
			p = new Producto();
			p.setNombreImagen("toshiba-excite.jpg");
			p.setNombre("Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1");
			p.setPrecio("598.000");
			productos.add(p);
			// Usuario
			Usuario u = new Usuario();
			u.setNombre("fernando");
			u.setContrasena("789");
			u.setNombreContacto("Fernando Rodriguez");
			u.setCorreo("fernandorodriguez@gmail.com");
			u.setTelefonos("315 285 0948");
			u.setCiudad("Bogota");
			u.setDireccion("Carrera 89 #34-84");
			u.setBanco("Avvillas");
			u.setNumeroCuenta("845-3029");
			u.setProductos(productos);
			manejadorDeUsuarios.guardarUsuario(u);
			logger.info("############## creado usuario fernando");
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############## preDestroy");
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
