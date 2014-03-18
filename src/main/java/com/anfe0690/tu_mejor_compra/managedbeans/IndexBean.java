package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.managedbeans.datos.Navegacion;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class IndexBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(IndexBean.class);
	// Modelo
	private List<Producto> productosRecientes;
	private final Navegacion navegacion = new Navegacion();
	//
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager em;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void buscarProductosRecientes(ComponentSystemEvent e) {
		logger.trace("buscarProductosRecientes");
		TypedQuery<Producto> qps = em.createQuery("SELECT p FROM Producto p ORDER BY p.fechaDeCreacion DESC, p.id DESC", Producto.class);
		productosRecientes = navegacion.calcular(qps);
	}

	// Getters and setters
	public List<Producto> getProductosRecientes() {
		return productosRecientes;
	}

	public Navegacion getNavegacion() {
		return navegacion;
	}

}
