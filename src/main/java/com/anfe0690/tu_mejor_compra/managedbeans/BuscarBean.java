package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class BuscarBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(BuscarBean.class);

	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	private List<Producto> resultados;
	private String valor;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void buscar() {
		logger.debug("buscar {}", valor);
		TypedQuery<Producto> tq = em.createQuery("SELECT p FROM Producto p WHERE p.nombre LIKE '%" + valor + "%'", Producto.class);
		List<Producto> res = tq.getResultList();
		resultados = res;
	}

	// Getters and setters
	public List<Producto> getResultados() {
		return resultados;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
