package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.entity.Venta;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
	private boolean identificado = true;
	private boolean baseDeDatosLimpiada;
	// Campos
	private String password;
	// Beans
	@PersistenceContext
	private EntityManager em;
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
		logger.debug("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.debug("preDestroy");
	}

	public String identificarse() {
		if (password.equalsIgnoreCase("asd")) {
			identificado = true;
			logger.debug("Identificado como Administrador");
		}
		return null;
	}

	public void limpiarBaseDeDatos() {
		logger.info("Inicia limpieza de base de datos...");
		logger.debug("Eliminadas {} entidades del tipo Usuario", manejadorDeUsuarios.removerTodosLosUsuarios());
		logger.debug("Eliminadas {} entidades del tipo Compra", manejadorDeCompras.removerTodasLasCompras());
		logger.debug("Eliminadas {} entidades del tipo Venta", manejadorDeVentas.removerTodasLasVentas());

		// TODO 05: Eliminar los archivos de imagenes
//		for (Producto p : manejadorDeProductos.obtenerTodosLosProductos()) {
//			File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + u.getNombre() + "\\" + p.getNombreImagen());
//			try {
//				Files.deleteIfExists(f.toPath());
//			} catch (Exception ex) {
//				logger.error(null, ex);
//			}
//		}
		logger.debug("Eliminadas {} entidades del tipo Producto", manejadorDeProductos.removerTodosLosProductos());

		baseDeDatosLimpiada = true;
		logger.info("Base de datos limpiada.");
	}

	public String limpiarRestaurarBaseDeDatos() {
		limpiarBaseDeDatos();
		logger.info("Inicia restauracion de base de datos...");
		TypedQuery<Usuario> q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		for (Usuario u : q.getResultList()) {
//			logger.debug(u.toString());
//			List<Venta> vs = new ArrayList<>(u.getVentas());
//			u.setVentas(null);
//			manejadorDeUsuarios.mergeUsuario(u);
//			for (Venta v : vs) {
//				manejadorDeVentas.removeVenta(v);
//			}
//			vs.clear();
//			List<Compra> cs = new ArrayList<>(u.getCompras());
//			u.setCompras(null);
//			manejadorDeUsuarios.mergeUsuario(u);
//			for (Compra c : cs) {
//				manejadorDeCompras.removeCompra(c);
//			}
//			cs.clear();
//			for (Producto p : u.getProductos()) {
//				File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + u.getNombre() + "\\" + p.getNombreImagen());
//				try {
//					Files.deleteIfExists(f.toPath());
//				} catch (Exception ex) {
//					logger.error(null, ex);
//				}
//			}
		}
//		for (Usuario u : q.getResultList()) {
//			manejadorDeUsuarios.removeUsuario(u);
//		}
		logger.info("La base de datos fue restaurada.");
		return "index.xhtml?faces-redirect=true";
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
