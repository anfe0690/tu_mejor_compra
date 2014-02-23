package com.anfe0690.tu_mejor_compra;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebContainerListener implements ServletContextListener {

	public static final String DIR_DATOS = "dir_datos";
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger.getLogger(WebContainerListener.class.getName()).log(Level.INFO, "contextInitialized");
		if (System.getenv("OPENSHIFT_DATA_DIR") != null) {
			System.setProperty(DIR_DATOS, System.getenv("OPENSHIFT_DATA_DIR"));
		} else {
			System.setProperty(DIR_DATOS, "C:\\Users\\Andres\\Downloads\\test\\TuMejorCompra\\img\\");
		}
		// TypedQuery<Usuario> typedQuery = entityManager.createQuery(
		// "SELECT u FROM Usuario u JOIN u.productos p WHERE p.id = :productoId"
		// , Usuario.class).setParameter("productoId", 5L);
		// Logger.getLogger(WebContainerListener.class.getName()).log(Level.INFO,
		// "typedQuery.getSingleResult() = " + typedQuery.getSingleResult());
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Logger.getLogger(WebContainerListener.class.getName()).log(Level.INFO, "contextDestroyed");
	}
}
