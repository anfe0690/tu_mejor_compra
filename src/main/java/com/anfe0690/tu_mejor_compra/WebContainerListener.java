package com.anfe0690.tu_mejor_compra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebContainerListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(WebContainerListener.class);

	public static final String DIR_DATOS = "dir_datos";
	@PersistenceContext
	private EntityManager entityManager;

	// TODO 03: Habiliar el uso de logs de niveles inferiores a INFO
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("contextInitialized");
		if (System.getenv("OPENSHIFT_DATA_DIR") != null) {
			System.setProperty(DIR_DATOS, System.getenv("OPENSHIFT_DATA_DIR"));
		} else {
			System.setProperty(DIR_DATOS, "C:\\Users\\Andres\\Downloads\\test\\TuMejorCompra\\img\\");
		}
		// TypedQuery<Usuario> typedQuery = entityManager.createQuery(
		// "SELECT u FROM Usuario u JOIN u.productos p WHERE p.id = :productoId"
		// , Usuario.class).setParameter("productoId", 5L);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("contextDestroyed");
	}
}
