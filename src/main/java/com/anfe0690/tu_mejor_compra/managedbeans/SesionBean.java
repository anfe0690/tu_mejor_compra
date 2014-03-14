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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO 097: Proteger contra el SQL Injection
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
//			Usuario u = manejadorDeUsuarios.buscarUsuarioPorNombre(campoNombreUsuario);
//			if (u != null && u.getContrasena().equals(campoContrasena)) {
//				sesionIniciada = true;
//				this.usuario = u;
//				logger.info("Sesion iniciada: {}", u.getNombre());
//				for (Producto p : u.getProductos()) {
//					logger.debug("{}", p);
//				}
//			} else {
//			}
//		} catch (IllegalArgumentException e) {
//		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void validarUsuarioContrasena(ComponentSystemEvent e) {
		UIComponent form = e.getComponent();
		UIInput inputUsuario = (UIInput) form.findComponent("nombre-usuario");
		UIInput inputContraseña = (UIInput) form.findComponent("contrasena-usuario");

		FacesContext fc = FacesContext.getCurrentInstance();
		// Comprobar si el nombre de usuario esta vacio
		if(inputUsuario.getLocalValue() == null || inputUsuario.getLocalValue().toString().trim().isEmpty()){
			logger.warn("Nombre de usuario vacio!");
			fc.addMessage(form.getClientId(), new FacesMessage("Nombre de usuario vacio!"));
			fc.renderResponse();
			return;
		}
		
		// Comprobar si la contraseña esta vacia
		if(inputContraseña.getLocalValue() == null || inputContraseña.getLocalValue().toString().trim().isEmpty()){
			logger.warn("Contraseña vacia!");
			fc.addMessage(form.getClientId(), new FacesMessage("Contraseña vacia!"));
			fc.renderResponse();
			return;
		}
		
		
		// Comprobar la relacion de usuario y contraseña
		Usuario u = manejadorDeUsuarios.buscarUsuarioPorNombre(inputUsuario.getLocalValue().toString());
		if (u != null && u.getContrasena().equals(inputContraseña.getLocalValue().toString())) {
			logger.debug("Usuario y contraseña correctos");
		} else {
			logger.warn("Usuario y/o contraseña incorrectos: u=\"{}\" c=\"{}\"", inputUsuario.getLocalValue(), inputContraseña.getLocalValue());
			fc.addMessage(form.getClientId(), new FacesMessage("Usuario y/o contraseña incorrectos"));
			fc.renderResponse();
		}
	}

	// Acciones
	public String iniciarSesion() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Usuario u = manejadorDeUsuarios.buscarUsuarioPorNombre(campoNombreUsuario);
		sesionIniciada = true;
		this.usuario = u;
		logger.info("Sesion iniciada: {}", u.getNombre());
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

	public void redireccionarSinSesion(ComponentSystemEvent e) throws AbortProcessingException {
		if (!sesionIniciada) {
			logger.debug("redireccionarSinSesion - sesionIniciada: {}", sesionIniciada);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String outcome = "index.xhtml?faces-redirect=true";
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
		}
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
