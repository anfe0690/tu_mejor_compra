package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeTransacciones;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.managedbeans.datos.SelProducto;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO 096: Evaluar el uso de objetos que tras obtenerse tal vez ya no existan
@Named
@RequestScoped
public class MisProductos {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MisProductos.class);
	@Inject
	private SesionBean sesionBean;
	private List<SelProducto> selProductos;
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;
	@EJB
	private ManejadorDeTransacciones manejadorDeTransacciones;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
		selProductos = new ArrayList<>();
		logger.debug("Productos del usuario en sesion");
		for (Producto producto : sesionBean.getUsuario().getProductos()) {
			selProductos.add(new SelProducto(producto));
			logger.debug("{}", producto);
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public List<SelProducto> getSelProductos() {
		return selProductos;
	}

	// TODO 095: Problema al ejecutar el metodo eliminarProductos() con Wildfly (Hibernate)
	public void eliminarProductos() {
		Usuario usuario = sesionBean.getUsuario();
		Iterator<SelProducto> it = selProductos.iterator();
		while (it.hasNext()) {
			SelProducto sp = it.next();
			if (sp.isSeleccionado()) {
				Producto producto = sp.getProducto();
				// Remover transacciones relacionadas
				List<Transaccion> ts = manejadorDeTransacciones.obtenerTransaccionesRelacionadasConProducto(producto);
				for (Transaccion t : ts) {
					manejadorDeTransacciones.removerTransaccion(t);
					logger.info("Removida transaccion: {}", t);
				}
				// Remover de selProductos
				it.remove();
				// Remover del usuario
				Iterator<Producto> itp = usuario.getProductos().iterator();
				while (itp.hasNext()) {
					Producto p = itp.next();
					if (p.getId() == producto.getId()) {
						itp.remove();
						logger.debug("Removido producto del usuario: {}", p);
					}
				}
				logger.debug("Comprobar eliminacion de colleccion");
				for (Producto p : usuario.getProductos()) {
					logger.debug("{}", p);
				}
				manejadorDeUsuarios.mergeUsuario(usuario);
				manejadorDeProductos.removerProducto(producto);
				logger.info("Removido producto: {}", producto);

				File f = new File(System.getProperty(WebContainerListener.K_DIR_DATOS) + producto.getDireccionImagen());
				try {
					if (Files.deleteIfExists(f.toPath())) {
						logger.info("Removida imagen: {}", f);
					}
				} catch (IOException ex) {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(null, new FacesMessage(ex.toString()));
					logger.error(null, ex);
				}
			}
		}
	}

}
