/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
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
public class ManejadorDeProductos implements Serializable {

	private static final Logger logger = Logger.getLogger(ManejadorDeProductos.class.getName());

	@Inject
	private SesionController sesionController;
	private List<SelProducto> selProductos = new ArrayList<>();

	@PostConstruct
	public void postConstruct() {
		logger.info("############## postConstruct");
		for (Producto producto : sesionController.getUsuario().getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############## preDestroy");
	}

	public List<SelProducto> getSelProductos() {
		return selProductos;
	}

	public void eliminarProductos() {
		StringBuilder sb = new StringBuilder();
		/*
		 sb.append("############## ");
		 for (SelProducto sp : selProductos) {
		 sb.append(selProductos.indexOf(sp) + ". " + sp.isSeleccionado() + " ");
		 }
		 logger.info(sb.toString());
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Usuario usuario = sesionController.getUsuario();
		et.begin();
		Iterator<SelProducto> it = selProductos.iterator();
		while (it.hasNext()) {
			SelProducto sp = it.next();
			if (sp.isSeleccionado()) {
				Producto producto = sp.getProducto();
				usuario.getProductos().remove(producto);
				it.remove();
			}
		}
		em.merge(usuario);
		et.commit();

		em.close();
		emf.close();
	}

	public void restaurar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Usuario usuario = sesionController.getUsuario();
		et.begin();
		usuario.getProductos().clear();
		List<Producto> productos = new ArrayList<>();
		// 1
		Producto p = new Producto();
		p.setNombreImagen("samsung-galaxy-s4.jpg");
		p.setNombre("Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me");
		p.setPrecio("1.139.000");
		productos.add(p);
		// 2
		p = new Producto();
		p.setNombreImagen("iphone-5s.jpg");
		p.setNombre("Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella");
		p.setPrecio("1.619.900");
		productos.add(p);
		// 3
		p = new Producto();
		p.setNombreImagen("lg-g2.jpg");
		p.setNombre("Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram");
		p.setPrecio("1.349.990");
		productos.add(p);

		usuario.setProductos(productos);
		em.merge(usuario);
		et.commit();

		em.close();
		emf.close();
		
		selProductos.clear();
		for (Producto producto : usuario.getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
		
		//logger.info("############## restaurados los productos de andres");
	}

}
