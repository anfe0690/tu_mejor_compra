package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class CategoriaBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CategoriaBean.class);
	//
	@PersistenceContext
	private EntityManager em;
	//
	private static final int PRODUCTOS_POR_PAGINA = 3;
	// 
	private String valor;
	private List<Producto> resultados;
	private int numeroPaginas;
	private int pagina = 1;

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
		int numeroProductos = qps.getResultList().size();
		numeroPaginas = (numeroProductos / PRODUCTOS_POR_PAGINA) + ((numeroProductos % PRODUCTOS_POR_PAGINA > 0) ? 1 : 0);
		if (pagina < 1 || pagina > numeroPaginas) {
			logger.warn("Numero de pagina invalido: \"{}\"", pagina);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String outcome = "index.xhtml?faces-redirect=true";
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
			return;
		}
		qps.setFirstResult((pagina - 1) * PRODUCTOS_POR_PAGINA);
		qps.setMaxResults(PRODUCTOS_POR_PAGINA);

		try {
			resultados = qps.getResultList();
		} catch (Exception ex) {
			logger.error(null, ex);
		}
	}

	public String getCategoriaEstetica() {
		return Categoria.valueOf(valor).getValorEstetico();
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

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

}
