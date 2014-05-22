package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.io.IOException;
import java.io.Serializable;

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
		logger.trace("validarUsuarioContrasena()");
		UIComponent form = e.getComponent();
		UIInput inputUsuario = (UIInput) form.findComponent("nombre-usuario");
		UIInput inputContraseña = (UIInput) form.findComponent("contrasena-usuario");

		FacesContext context = FacesContext.getCurrentInstance();
		// Comprobar si el nombre de usuario esta vacio
		if (inputUsuario.getLocalValue() == null || inputUsuario.getLocalValue().toString().trim().isEmpty()) {
			logger.warn("Nombre de usuario vacio!.");
            inputUsuario.setValid(false);
			context.addMessage(form.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario vacio!.", null));
		}

		// Comprobar si la contraseña esta vacia
		if (inputContraseña.getLocalValue() == null || inputContraseña.getLocalValue().toString().trim().isEmpty()) {
			logger.warn("Contraseña vacia!.");
            inputContraseña.setValid(false);
			context.addMessage(form.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña vacia!.", null));
			return;
		}

		// Comprobar la relacion de usuario y contraseña
		Usuario u = manejadorDeUsuarios.buscarUsuarioPorNombre(inputUsuario.getLocalValue().toString());
		if (u == null || !u.getContrasena().equals(inputContraseña.getLocalValue().toString())) {
            logger.warn("Usuario y/o contraseña incorrectos: u=\"{}\" c=\"{}\".", inputUsuario.getLocalValue(), inputContraseña.getLocalValue());
            inputUsuario.setValid(false);
            inputContraseña.setValid(false);
            context.addMessage(form.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña incorrectos.", null));
		}
	}

	// Acciones
	public String iniciarSesion() {
		logger.trace("iniciarSesion()");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Usuario u = manejadorDeUsuarios.buscarUsuarioPorNombre(campoNombreUsuario);
		sesionIniciada = true;
		this.usuario = u;
		logger.info("Sesion iniciada: {}", u.getNombre());
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido " + u.getNombre() + ".", null));
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return facesContext.getViewRoot().getViewId().substring(1) + "?faces-redirect=true&amp;includeViewParams=true";
	}

	public String cerrarSesion() throws IOException {
		sesionIniciada = false;
        String nombre = usuario.getNombre();
		this.usuario = null;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
        logger.info("Sesion cerrada: {}", nombre);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesion cerrada.", null));
        context.getExternalContext().getFlash().setKeepMessages(true);
		return "index.xhtml?faces-redirect=true";
	}

	public void redireccionarSinSesion(ComponentSystemEvent e) throws AbortProcessingException {
		if (!sesionIniciada) {
			logger.warn("Intento de ingresar a la pagina perfil.xhtml sin haber iniciado sesion, se redirecciona a index.xhtml");
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
