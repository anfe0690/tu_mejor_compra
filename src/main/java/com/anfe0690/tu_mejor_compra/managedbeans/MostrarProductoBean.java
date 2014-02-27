package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Venta;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class MostrarProductoBean implements Serializable {

	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;
	@EJB
	private ManejadorDeCompras manejadorDeCompras;
	@EJB
	private ManejadorDeVentas manejadorDeVentas;
	@Inject
	private SesionBean sc;
	private Usuario usuarioVendedor;
	private Producto producto;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(MostrarProductoBean.class.getName()).log(Level.INFO, "postConstruct");
		Map<String, String> pm = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String productoId = pm.get("pid");
		producto = manejadorDeProductos.obtenerProductoPorId(Long.parseLong(productoId));
		usuarioVendedor = manejadorDeUsuarios.getUsuarioPadreDeProducto(Long.parseLong(productoId));
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(MostrarProductoBean.class.getName()).log(Level.INFO, "preDestroy");
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
		// Compra
		Usuario usuarioComprador = sc.getUsuario();
		Compra compra = new Compra();
		compra.setVendedor(usuarioVendedor.getNombre());
		compra.setProducto(producto);
		compra.setEstado(Estado.ESPERANDO_PAGO);
		manejadorDeCompras.guardarCompra(compra);
		Logger.getLogger(MostrarProductoBean.class.getName()).log(Level.INFO, "compra : " + compra);
		usuarioComprador.getCompras().add(compra);
		manejadorDeUsuarios.mergeUsuario(usuarioComprador);
		// Venta
		Venta venta = new Venta();
		venta.setComprador(usuarioComprador.getNombre());
		venta.setProducto(producto);
		venta.setEstado(Estado.ESPERANDO_PAGO);
		manejadorDeVentas.guardarVenta(venta);
		Logger.getLogger(MostrarProductoBean.class.getName()).log(Level.INFO, "venta: " + venta);
		usuarioVendedor.getVentas().add(venta);
		manejadorDeUsuarios.mergeUsuario(usuarioVendedor);

		Logger.getLogger(MostrarProductoBean.class.getName()).log(Level.INFO,
				"Usuario " + usuarioComprador.getNombre() + " compro " + producto.getNombre());
		return "perfil";
	}
}
