package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class SesionBean implements Serializable {

	private static final long serialVersionUID = 42L;
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SesionBean.class);

	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	// Campos
	private String campoNombreUsuario;
	private String campoContrasena;
	// Datos
	private Usuario usuario;
	private boolean sesionIniciada = false;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
//		campoNombreUsuario = "andres";
//		campoContrasena = "123";
//		try {
//			Usuario usuario = manejadorDeUsuarios.buscarUsuarioPorNombre(campoNombreUsuario);
//			if (usuario != null && usuario.getContrasena().equals(campoContrasena)) {
//				sesionIniciada = true;
//				this.usuario = usuario;
//				logger.info("Sesion iniciada: {}", usuario.getNombre());
//				for (Producto p : usuario.getProductos()) {
//					logger.debug("{}", p);
//				}
//			} else {
//			}
//		} catch (Exception e) {
//		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	// Acciones
	public String iniciarSesion() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Usuario usuario = manejadorDeUsuarios.buscarUsuarioPorNombre(campoNombreUsuario);
			if (usuario != null && usuario.getContrasena().equals(campoContrasena)) {
				sesionIniciada = true;
				this.usuario = usuario;
				logger.info("Sesion iniciada: {}", usuario.getNombre());
			} else {
				fc.addMessage("sesion_form:input_sesion", new FacesMessage("Usuario y/o contraseña incorrectos"));
			}
		} catch (Exception e) {
			fc.addMessage("sesion_form:input_sesion", new FacesMessage(e.toString()));
		}
		return fc.getViewRoot().getViewId().substring(1) + "?faces-redirect=true&amp;includeViewParams=true";
	}

	public String cerrarSesion() throws IOException {
		sesionIniciada = false;
		this.usuario = null;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
		return "index.xhtml?faces-redirect=true";
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
