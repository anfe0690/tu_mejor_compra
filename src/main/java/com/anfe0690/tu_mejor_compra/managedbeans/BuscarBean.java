package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.managedbeans.datos.Navegacion;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class BuscarBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BuscarBean.class);
	//
	private static final long serialVersionUID = 1L;
	//
	@PersistenceContext
	private EntityManager em;
	//
	private List<Producto> resultados;
	private String valor;
	private final Navegacion navegacion = new Navegacion();

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
		TypedQuery<Producto> tq = em.createQuery("SELECT p FROM Producto p WHERE p.nombre LIKE :valor",
				Producto.class).setParameter("valor", "%" + valor + "%");
		resultados = navegacion.calcular(tq);
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

	public Navegacion getNavegacion() {
		return navegacion;
	}

}
