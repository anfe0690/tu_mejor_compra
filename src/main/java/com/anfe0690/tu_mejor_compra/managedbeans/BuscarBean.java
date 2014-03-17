package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
public class BuscarBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BuscarBean.class);
	//
	private static final long serialVersionUID = 1L;
	private static final int PRODUCTOS_POR_PAGINA = 3;
	//
	@PersistenceContext
	private EntityManager em;
	//
	private List<Producto> resultados;
	private String valor;
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
		logger.debug("buscar {}", valor);
		TypedQuery<Producto> tq = em.createQuery("SELECT p FROM Producto p WHERE p.nombre LIKE :valor",
				Producto.class).setParameter("valor", "%" + valor + "%");
		int numeroProductos = tq.getResultList().size();
		numeroPaginas = (numeroProductos / PRODUCTOS_POR_PAGINA) + ((numeroProductos % PRODUCTOS_POR_PAGINA > 0) ? 1 : 0);
		if (pagina < 1 || pagina > numeroPaginas) {
			logger.warn("Numero de pagina invalido: \"{}\"", pagina);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String outcome = "index.xhtml?faces-redirect=true";
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
			return;
		}
		tq.setFirstResult((pagina - 1) * PRODUCTOS_POR_PAGINA);
		tq.setMaxResults(PRODUCTOS_POR_PAGINA);

		try {
			resultados = tq.getResultList();
		} catch (Exception ex) {
			logger.error(null, ex);
		}
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
