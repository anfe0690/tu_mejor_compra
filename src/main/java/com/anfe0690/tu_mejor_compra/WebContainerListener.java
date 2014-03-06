package com.anfe0690.tu_mejor_compra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// TODO 103: Terminar de configurar el logger de Glassfish
@WebListener
public class WebContainerListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(WebContainerListener.class);

	// Keys
	public static final String K_DIR_DATOS = "K_DIR_DATOS";
	public static final String K_SERVIDOR = "K_SERVIDOR";
	// Values
	public static final String V_SERVIDOR_GLASSFISH = "V_SERVIDOR_GLASSFISH";
	public static final String V_SERVIDOR_WILDFLY = "V_SERVIDOR_WILDFLY";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("contextInitialized");
		if (System.getenv("OPENSHIFT_DATA_DIR") != null) {
			System.setProperty(K_DIR_DATOS, System.getenv("OPENSHIFT_DATA_DIR"));
		} else {
			System.setProperty(K_DIR_DATOS, "C:\\Users\\Andres\\Downloads\\test\\TuMejorCompra\\img\\");
		}

		System.setProperty(K_SERVIDOR, V_SERVIDOR_GLASSFISH);
		logger.info("{} = {}", K_SERVIDOR, System.getProperty(K_SERVIDOR));

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("contextDestroyed");
	}
}
