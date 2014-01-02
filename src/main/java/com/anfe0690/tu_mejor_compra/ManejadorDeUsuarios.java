/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Andres
 */
@Stateless
@LocalBean
public class ManejadorDeUsuarios {
	
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager em;
	
	public void guardarUsuario(Usuario usuario) {
		em.persist(usuario);
	}

	public Usuario buscarUsuarioPorNombre(String nombre) throws IllegalArgumentException {
		try {
			return em.find(Usuario.class, nombre.toLowerCase());
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

}
