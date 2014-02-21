package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ComprarProducto implements Serializable {

	@EJB
	private ManejadorDeUsuarios mdu;
	@EJB
	private ManejadorDeCompras manejadorDeCompras;
	@EJB
	private ManejadorDeVentas manejadorDeVentas;
	@Inject
	private SesionController sc;
	private Usuario usuarioVendedor;
	private Producto producto;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(ComprarProducto.class.getName()).log(Level.INFO, "postConstruct");
		Map<String, String> pm = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//logger.info("usuario: " + pm.get("u") + " - indiceProducto: " + pm.get("p"));
		String nombreUsuario = pm.get("u");
		String indiceProducto = pm.get("p");

		usuarioVendedor = mdu.buscarUsuarioPorNombre(nombreUsuario);
		producto = new ArrayList<>(usuarioVendedor.getProductos()).get(Integer.parseInt(indiceProducto));
//		producto = usuarioVendedor.getProductos().get(Integer.parseInt(indiceProducto));
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(ComprarProducto.class.getName()).log(Level.INFO, "preDestroy");

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
		Compra compra = new Compra();
		compra.setVendedor(usuarioVendedor.getNombre());
		compra.setProducto(producto);
		compra.setEstado(Estado.ESPERANDO_PAGO);
		usuarioComprador.getCompras().add(compra);

		Venta venta = new Venta();
		venta.setComprador(usuarioComprador.getNombre());
		venta.setProducto(producto);
		venta.setEstado(Estado.ESPERANDO_PAGO);
		usuarioVendedor.getVentas().add(venta);

		//manejadorDeCompras.guardarCompra(compra);
		mdu.mergeUsuario(usuarioComprador);

		//manejadorDeVentas.guardarVenta(venta);
		mdu.mergeUsuario(usuarioVendedor);

		Logger.getLogger(ComprarProducto.class.getName()).log(Level.INFO, usuarioComprador.getNombre() + " compro \"" + producto.getNombre() + "\" de " + usuarioVendedor.getNombre());
		return "perfil";
	}

}
