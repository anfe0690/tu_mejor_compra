/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Andres
 */
@Singleton
public class ManejadorDeUsuarios {
	
	public void guardarUsuario(Usuario usuario) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(usuario);
		entityTransaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public Usuario buscarUsuarioPorNombre(String nombre) throws IllegalArgumentException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();

		try {
			return em.find(Usuario.class, nombre.toLowerCase());
		} catch (IllegalArgumentException e) {
			throw e;
		} finally {
			em.close();
			emf.close();
		}
	}

}
