package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.managedbeans.datos.Resultado;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class BuscarBean implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(BuscarBean.class);
	
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	private List<Resultado> resultados;
	private String valor;

	@PostConstruct
	public void postConstruct() {
		logger.debug("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.debug("preDestroy");
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		logger.debug("setValor()");
		this.valor = valor;
	}

	public void buscar() {
		logger.debug("buscar {}", valor);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//		valor = ec.getRequestParameterMap().get("header_form:texto_buscar");

		List<Resultado> res = new ArrayList<>();

		Query q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> rl = q.getResultList();

		if (valor != null) {
			for (Usuario u : rl) {
				for (Producto pro : u.getProductos()) {
					if (pro.getNombre().toLowerCase().contains(valor.toLowerCase())) {
						res.add(new Resultado(u, pro));
					}
				}
			}
		} else {
			if (ec.getRequestParameterMap().get("form_categorias:telefonos_inteligentes") != null) {
				for (Usuario u : rl) {
					for (Producto pro : u.getProductos()) {
						if (pro.getCategoria().equals(Categoria.TELEFONOS_INTELIGENTES)) {
							res.add(new Resultado(u, pro));
						}
					}
				}
			} else if (ec.getRequestParameterMap().get("form_categorias:consolas_video_juegos") != null) {
				for (Usuario u : rl) {
					for (Producto pro : u.getProductos()) {
						if (pro.getCategoria().equals(Categoria.CONSOLAS_VIDEO_JUEGOS)) {
							res.add(new Resultado(u, pro));
						}
					}
				}
			} else if (ec.getRequestParameterMap().get("form_categorias:tabletas") != null) {
				for (Usuario u : rl) {
					for (Producto pro : u.getProductos()) {
						if (pro.getCategoria().equals(Categoria.TABLETAS)) {
							res.add(new Resultado(u, pro));
						}
					}
				}
			}
		}
		resultados = res;
	}

}
