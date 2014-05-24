package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeTransacciones;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MostrarProductoBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MostrarProductoBean.class);
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;
	@EJB
	private ManejadorDeTransacciones manejadorDeTransacciones;
	@Inject
	private SesionBean sc;
	private Usuario usuarioVendedor;
	private Producto producto;
	//
	private String productoId;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void leerProducto() {
		producto = manejadorDeProductos.obtenerProductoPorId(Long.parseLong(productoId));
		if (producto != null) {
			usuarioVendedor = manejadorDeUsuarios.getUsuarioPadreDeProducto(Long.parseLong(productoId));
			if (usuarioVendedor == null) {
				// Redireccionar si no encontro el usuario
				logger.warn("No se encontro el Usuario dueño del producto \"{}\", se redirecciona a index.xhtml", producto);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				String outcome = "index.xhtml?faces-redirect=true";
				facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
				return;
			}
		} else {
			// Redireccionar si la id es invalida
			logger.warn("Id \"{}\" invalida de producto, se redirecciona a index.xhtml", productoId);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String outcome = "index.xhtml?faces-redirect=true";
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
		}
	}

	public String comprar() {
		Usuario usuarioComprador = sc.getUsuario();
		Transaccion t = new Transaccion();
		t.setProducto(producto);
		t.setEstado(Estado.ESPERANDO_PAGO);
		t.setUsuarioVendedor(usuarioVendedor);
		t.setUsuarioComprador(usuarioComprador);
		manejadorDeTransacciones.guardarTransaccion(t);
		logger.info("El usuario {} compro \"{}\", con la transaccion {}", usuarioComprador.getNombre(), producto.getNombre(), t);
		return "perfil.xhtml?faces-redirect=true";
	}

	// Getters and setters
	public String getProductoId() {
		return productoId;
	}

	public void setProductoId(String productoId) {
		this.productoId = productoId;
	}

	public Usuario getUsuarioVendedor() {
		return usuarioVendedor;
	}

	public Producto getProducto() {
		return producto;
	}

}
