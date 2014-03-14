package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class IndexBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(IndexBean.class);
	private static final long serialVersionUID = 1L;
	// Modelo
	private final List<Producto> productosRecientes = new ArrayList<>();
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager em;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
		TypedQuery<Producto> qps = em.createQuery("SELECT p FROM Producto p ORDER BY p.fechaDeCreacion DESC", Producto.class);
		try {
			productosRecientes.clear();
			List<Producto> ps = qps.getResultList();
			for(int i=0;i<ps.size() && i<3;i++){
				productosRecientes.add(ps.get(i));
			}
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	// Getters and setters
	public List<Producto> getProductosRecientes() {
		return productosRecientes;
	}

}
