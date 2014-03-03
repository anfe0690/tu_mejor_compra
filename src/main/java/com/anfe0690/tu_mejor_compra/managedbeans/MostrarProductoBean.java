package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeTransacciones;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
		Map<String, String> pm = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String productoId = pm.get("pid");
		producto = manejadorDeProductos.obtenerProductoPorId(Long.parseLong(productoId));
		usuarioVendedor = manejadorDeUsuarios.getUsuarioPadreDeProducto(Long.parseLong(productoId));
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public Usuario getUsuarioVendedor() {
		return usuarioVendedor;
	}

	public void setUsuarioVendedor(Usuario usuarioVendedor) {
		this.usuarioVendedor = usuarioVendedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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
		return "perfil";
	}
}
