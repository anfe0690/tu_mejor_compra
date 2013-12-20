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
import java.util.logging.Logger;
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
		Usuario usu = null;
		try {
			usu = manejadorDeUsuarios.buscarUsuarioPorNombre("andres");
		} catch (IllegalArgumentException e) {
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
			manejadorDeUsuarios.guardarUsuario(u);
			logger.info("############## creado usuario andres");
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
