package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named
@ViewScoped
public class Buscar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	private List<Resultado> resultados;
	private String texto;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(Buscar.class.getName()).log(Level.INFO, "postConstruct");
		resultados = buscarProductos();
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(Buscar.class.getName()).log(Level.INFO, "preDestroy");
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	private List<Resultado> buscarProductos() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		texto = ec.getRequestParameterMap().get("header_form:texto_buscar");

		List<Resultado> res = new ArrayList<>();

		Query q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> rl = q.getResultList();

		if (texto != null) {
			for (Usuario u : rl) {
				for (Producto pro : u.getProductos()) {
					if (pro.getNombre().toLowerCase().contains(texto.toLowerCase())) {
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
		return res;
	}

}
