/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class ManejadorDeCompras implements Serializable{
	
	private static final Logger logger = Logger.getLogger(ManejadorDeCompras.class.getName());
	
	@PostConstruct
	public void postConstruct() {
		logger.info("########## postConstruct");
	}
	
	@PreDestroy
	public void preDestroy() {
		logger.info("########## preDestroy");
	}
}
