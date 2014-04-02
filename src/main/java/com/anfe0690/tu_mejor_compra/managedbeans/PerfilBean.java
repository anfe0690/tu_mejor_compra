package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeTransacciones;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.managedbeans.datos.FilaTransaccion;
import com.anfe0690.tu_mejor_compra.managedbeans.datos.SelProducto;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class PerfilBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PerfilBean.class);
	@Inject
	private SesionBean sesionBean;
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;
	@EJB
	private ManejadorDeTransacciones manejadorDeTransacciones;
	// Modelo
	private List<SelProducto> selProductos;
	private List<FilaTransaccion> misVentas;
	private List<FilaTransaccion> misCompras;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");

		// Mis productos
		selProductos = new ArrayList<>();
		List<Producto> lps = sesionBean.getUsuario().getProductosOrdenados();
		Collections.reverse(lps);
		for (Producto producto : lps) {
			selProductos.add(new SelProducto(producto));
		}

		// Mis transacciones
		misVentas = new ArrayList<>();
		for (Transaccion t : manejadorDeTransacciones.obtenerTransaccionesTipoVentaDeUsuario(sesionBean.getUsuario())) {
			FilaTransaccion ft = new FilaTransaccion();
			ft.setTransaccion(t);
			ft.setNuevoEstado(t.getEstado());
			misVentas.add(ft);
		}
		misCompras = new ArrayList<>();
		for (Transaccion t : manejadorDeTransacciones.obtenerTransaccionesTipoCompraDeUsuario(sesionBean.getUsuario())) {
			FilaTransaccion ft = new FilaTransaccion();
			ft.setTransaccion(t);
			ft.setNuevoEstado(t.getEstado());
			misCompras.add(ft);
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

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
					// Remover de misVentas
					Iterator<FilaTransaccion> itFt = misVentas.iterator();
					while (itFt.hasNext()) {
						FilaTransaccion ft = itFt.next();
						if (ft.getTransaccion().getId() == t.getId()) {
							itFt.remove();
						}
					}
					logger.info("Removida transaccion: {}", t);
				}
				// Remover del usuario
				manejadorDeUsuarios.removerProductoDeUsuario(usuario, producto);
				// Remover producto
				manejadorDeProductos.removerProducto(producto);
				logger.info("Removido producto: {}", producto);
				// Remover de selProductos
				it.remove();

				File f = new File(System.getProperty(WebContainerListener.K_DIR_DATOS) + producto.getDireccionImagen());
				try {
					if (Files.deleteIfExists(f.toPath())) {
						logger.info("Removida la imagen: {}", f);
					}
				} catch (IOException ex) {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(null, new FacesMessage(ex.toString()));
					logger.error(null, ex);
				}
			}
		}
	}

	public void actualizarVentas() {
		logger.trace("actualizarVentas()");
		
		Iterator<FilaTransaccion> it = misVentas.iterator();
		while (it.hasNext()) {
			FilaTransaccion ft = it.next();
			// Comprobar que el estado este sincronizado
			Transaccion t = manejadorDeTransacciones.obtenerTransaccionPorId(ft.getTransaccion().getId());
			if(!ft.getTransaccion().getEstado().equals(t.getEstado())){
				ft.getTransaccion().setEstado(t.getEstado());
				ft.setNuevoEstado(t.getEstado());
			}
		}
		
		for (FilaTransaccion ft : misVentas) {
			if (!ft.getNuevoEstado().equals(ft.getTransaccion().getEstado())) {
				ft.getTransaccion().setEstado(Estado.EN_ENVIO);
				manejadorDeTransacciones.mergeTransaccion(ft.getTransaccion());
				logger.info("Actualizada transaccion {}", ft.getTransaccion());
			}
		}
	}

	public void actualizarCompras() {
		logger.trace("actualizarCompras()");

		Iterator<FilaTransaccion> it = misCompras.iterator();
		while (it.hasNext()) {
			FilaTransaccion ft = it.next();
			// Comprobar que el producto referenciado exista todavia
			Producto p = manejadorDeProductos.obtenerProductoPorId(ft.getTransaccion().getProducto().getId());
			if (p == null) {
				it.remove();
				continue;
			}
			// Comprobar que el estado este sincronizado
			Transaccion t = manejadorDeTransacciones.obtenerTransaccionPorId(ft.getTransaccion().getId());
			if(!ft.getTransaccion().getEstado().equals(t.getEstado())){
				ft.getTransaccion().setEstado(t.getEstado());
				ft.setNuevoEstado(t.getEstado());
			}
		}

		for (FilaTransaccion ft : misCompras) {
			if (!ft.getNuevoEstado().equals(ft.getTransaccion().getEstado())) {
				ft.getTransaccion().setEstado(Estado.TERMINADO);
				manejadorDeTransacciones.mergeTransaccion(ft.getTransaccion());
				logger.info("Actualizada transaccion {}", ft.getTransaccion());
			}
		}
	}

	// Getters and Setters
	public List<SelProducto> getSelProductos() {
		return selProductos;
	}

	public boolean deshabilitadoBotonEliminarProductos() {
		for (SelProducto sp : selProductos) {
			if (sp.isSeleccionado()) {
				return false;
			}
		}
		return true;
	}

	public List<FilaTransaccion> getMisVentas() {
		return misVentas;
	}

	public List<FilaTransaccion> getMisCompras() {
		return misCompras;
	}
}
