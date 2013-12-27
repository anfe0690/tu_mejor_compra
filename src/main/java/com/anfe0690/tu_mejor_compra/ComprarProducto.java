/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class ComprarProducto implements Serializable {

	private static final Logger logger = Logger.getLogger(ComprarProducto.class.getName());
	@Inject
	private ManejadorDeUsuarios mdu;
	@Inject
	private SesionController sc;
	private Usuario usuarioVendedor;
	private Producto producto;

	@PostConstruct
	public void postConstruct() {
		logger.info("############ postConstruct");
		Map<String, String> pm = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//logger.info("usuario: " + pm.get("u") + " - indiceProducto: " + pm.get("p"));
		String nombreUsuario = pm.get("u");
		String indiceProducto = pm.get("p");

		usuarioVendedor = mdu.buscarUsuarioPorNombre(nombreUsuario);
		producto = usuarioVendedor.getProductos().get(Integer.parseInt(indiceProducto));
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############ preDestroy");
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
		compra.setIndiceProducto(usuarioVendedor.getProductos().indexOf(producto));
		compra.setEstado(EstadoCompraVenta.ESPERANDO_PAGO);
		usuarioComprador.getCompras().add(compra);
		
		Venta venta = new Venta();
		venta.setComprador(usuarioComprador.getNombre());
		venta.setIndiceProducto(usuarioVendedor.getProductos().indexOf(producto));
		venta.setEstado(EstadoCompraVenta.ESPERANDO_PAGO);
		usuarioVendedor.getVentas().add(venta);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.merge(usuarioComprador);
		em.merge(usuarioVendedor);
		et.commit();

		em.close();
		emf.close();
		
		logger.info("########### " + usuarioComprador.getNombre() + " compro \"" + producto.getNombre() + "\" de " + usuarioVendedor.getNombre());
		return "perfil";
	}

}
