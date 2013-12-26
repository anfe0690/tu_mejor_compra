/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class Buscar implements Serializable {

	private static final Logger logger = Logger.getLogger(Buscar.class.getName());
	private List<Resultado> resultados;
	private String texto;

	@PostConstruct
	public void postConstruct() {
		logger.info("############ postConstruct");
		resultados = buscarProductos();
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############ preDestroy");
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

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> rl = q.getResultList();

		List<Resultado> res = new ArrayList<>();
		for (Usuario u : rl) {
			for (Producto pro : u.getProductos()) {
				if (pro.getNombre().toLowerCase().contains(texto.toLowerCase())) {
					res.add(new Resultado(u, pro));
					//logger.log(Level.INFO, "Usuario: {0} con Producto: {1}", new Object[]{u, pro});
				}
			}
		}
		return res;
	}

}
