package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
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
	// Constantes
	private static final long serialVersionUID = 1L;
	private static final int PRODUCTOS_POR_PAGINA = 3;
	// Modelo
	private List<Producto> productosRecientes;
	private int numeroPaginas;
	private int pagina = 1;
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

	// TODO 100: Eliminar el codigo duplicado
	public void buscarProductosRecientes(ComponentSystemEvent e) {
		logger.trace("buscarProductosRecientes");
		TypedQuery<Producto> qps = em.createQuery("SELECT p FROM Producto p ORDER BY p.fechaDeCreacion DESC, p.id DESC", Producto.class);
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
			productosRecientes = qps.getResultList();
		} catch (Exception ex) {
			logger.error(null, ex);
		}
	}

	// Getters and setters
	public List<Producto> getProductosRecientes() {
		return productosRecientes;
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
