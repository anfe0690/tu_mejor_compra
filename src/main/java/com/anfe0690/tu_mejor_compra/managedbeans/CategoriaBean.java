package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class CategoriaBean {

	private static final Logger logger = LoggerFactory.getLogger(CategoriaBean.class);
	@PersistenceContext
	private EntityManager em;
	// 
	private String valor;
	private List<Producto> resultados;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void buscar() {
		logger.debug("buscar categoria {}", valor);
		try {
			Categoria.valueOf(valor);
		} catch (IllegalArgumentException e) {
			// Redireccionar si la categoria es ivalida
			logger.warn("La categoria \"{}\" es invalida, se redirecciona a index.xhtml", valor);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String outcome = "index.xhtml?faces-redirect=true";
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
			return;
		}
		TypedQuery<Producto> qps = em.createQuery("SELECT p FROM Producto p WHERE p.categoria = :categoria", Producto.class)
				.setParameter("categoria", Categoria.valueOf(valor));
		List<Producto> res = qps.getResultList();
		resultados = res;
	}

	public String getCategoriaEstetica() {
		return Categoria.valueOf(valor).getValorEstetico();
	}

	public Categoria[] getCategorias() {
		return Categoria.values();
	}

	// Getters and Setters
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<Producto> getResultados() {
		return resultados;
	}

}
