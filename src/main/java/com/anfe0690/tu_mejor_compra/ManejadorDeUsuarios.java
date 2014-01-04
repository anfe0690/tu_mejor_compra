/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andres
 */
@Stateless
@LocalBean
public class ManejadorDeUsuarios implements Serializable{
	
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager em;
	
	public void guardarUsuario(Usuario usuario) {
		em.persist(usuario);
	}
	
	public void mergeUsuario(Usuario usuario){
		em.merge(usuario);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario buscarUsuarioPorNombre(String nombre) throws IllegalArgumentException {
		try {
			return em.find(Usuario.class, nombre.toLowerCase());
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

}
