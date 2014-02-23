package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Named
@RequestScoped
public class Index implements Serializable {

	private static final long serialVersionUID = 1L;
	// Modelo
	private List<ProductoReciente> productosRecientes = new ArrayList<>();
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager em;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(Index.class.getName()).log(Level.INFO, "postConstruct");
		TypedQuery<Usuario> q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		try {
			List<Usuario> rl = q.getResultList();
			for (Usuario u : rl) {
				// logger.info("usuario: " + u.getNombre());
				for (Producto p : u.getProductos()) {
					ProductoReciente pr = new ProductoReciente();
					pr.setUsuario(u);
					pr.setProducto(p);
					productosRecientes.add(pr);
				}
			}
			Collections.sort(productosRecientes, new Comparator<ProductoReciente>() {

				@Override
				public int compare(ProductoReciente o1, ProductoReciente o2) {
					Date date1 = o1.getProducto().getFechaDeCreacion();
					Date date2 = o2.getProducto().getFechaDeCreacion();
					return date1.compareTo(date2);
				}
			});
			Collections.reverse(productosRecientes);
			productosRecientes = productosRecientes.subList(0, 3);
		} catch (Exception e) {
			Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(Index.class.getName()).log(Level.INFO, "preDestroy");
	}

	public List<ProductoReciente> getProductosRecientes() {
		return productosRecientes;
	}

	public void setProductosRecientes(List<ProductoReciente> productosRecientes) {
		this.productosRecientes = productosRecientes;
	}
}
