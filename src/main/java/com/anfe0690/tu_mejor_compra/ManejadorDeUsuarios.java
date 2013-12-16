/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

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
public class ManejadorDeUsuarios {

	private static ManejadorDeUsuarios instancia = new ManejadorDeUsuarios();

	public static ManejadorDeUsuarios getInstancia() {
		return instancia;
	}

	private ManejadorDeUsuarios() {
	}

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

	public Usuario buscarUsuarioPorNombre(String nombre) throws Exception {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		System.out.println("Nombre: '" + nombre + "'");
		Query q = entityManager.createQuery("SELECT u FROM Usuario u WHERE LOWER(u.nombre) = '" + nombre.toLowerCase() + "'", Usuario.class);
		//Query q = entityManager.createNativeQuery("SELECT * FROM USUARIO");
		try {
			Usuario usuario = (Usuario) q.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw e;
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

}
