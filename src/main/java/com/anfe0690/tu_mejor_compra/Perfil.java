/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named
@RequestScoped
public class Perfil {

	private static final Logger logger = Logger.getLogger(Perfil.class.getName());

	@Inject
	private ManejadorDeUsuarios mu;

	@PostConstruct
	public void postConstruct() {
		logger.info("######### postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("######### preDestroy");
	}

	public Producto getProductoPorIndice(String usuario, int indice) {
		Usuario u = mu.buscarUsuarioPorNombre(usuario);
		return u.getProductos().get(indice);
	}

}
