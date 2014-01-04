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
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class ComprarProducto implements Serializable {

	private static final Logger logger = Logger.getLogger(ComprarProducto.class.getName());
	@EJB
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
		compra.setEstado(Estado.ESPERANDO_PAGO);
		usuarioComprador.getCompras().add(compra);
		
		Venta venta = new Venta();
		venta.setComprador(usuarioComprador.getNombre());
		venta.setIndiceProducto(usuarioVendedor.getProductos().indexOf(producto));
		venta.setEstado(Estado.ESPERANDO_PAGO);
		usuarioVendedor.getVentas().add(venta);
		
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		//EntityManager em = emf.createEntityManager();
		//EntityTransaction et = em.getTransaction();
		
		//et.begin();
		//em.merge(usuarioComprador);
		mdu.mergeUsuario(usuarioComprador);
		//em.merge(usuarioVendedor);
		mdu.mergeUsuario(usuarioVendedor);
		//et.commit();

		//em.close();
		//emf.close();
		
		logger.info("########### " + usuarioComprador.getNombre() + " compro \"" + producto.getNombre() + "\" de " + usuarioVendedor.getNombre());
		return "perfil";
	}

}
