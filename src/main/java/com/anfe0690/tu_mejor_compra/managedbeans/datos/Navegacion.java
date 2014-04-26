package com.anfe0690.tu_mejor_compra.managedbeans.datos;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class Navegacion implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(Navegacion.class);
	// Constantes
	private static final long serialVersionUID = 1L;
	private static final int PRODUCTOS_POR_PAGINA = 9;
	//
	private int numeroPaginas;
	private int pagina = 1;

	public List<Producto> calcular(TypedQuery<Producto> tq) {
		int numeroProductos = tq.getResultList().size();
		numeroPaginas = (numeroProductos / PRODUCTOS_POR_PAGINA) + ((numeroProductos % PRODUCTOS_POR_PAGINA > 0) ? 1 : 0);
		if (pagina < 1 || pagina > numeroPaginas) {
			logger.warn("Numero de pagina invalido: \"{}\"", pagina);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String outcome = "index.xhtml?faces-redirect=true";
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
			return null;
		}
		tq.setFirstResult((pagina - 1) * PRODUCTOS_POR_PAGINA);
		tq.setMaxResults(PRODUCTOS_POR_PAGINA);

		try {
			return tq.getResultList();
		} catch (Exception ex) {
			logger.error(null, ex);
			return null;
		}
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
