package com.anfe0690.tu_mejor_compra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

	// Keys
	public static final String K_DIR_DATOS = "K_DIR_DATOS";
	public static final String K_SERVIDOR = "K_SERVIDOR";
    public static final String K_HOST = "K_HOST";
    public static final String K_PUERTO_WEBSOCKETS = "K_PUERTO_WEBSOCKETS";
	// Values
	public static final String V_SERVIDOR_GLASSFISH = "V_SERVIDOR_GLASSFISH";
    public static final String V_SERVIDOR_WILDFLY_LOCAL = "V_SERVIDOR_WILDFLY_LOCAL";
    public static final String V_SERVIDOR_WILDFLY_REMOTO = "V_SERVIDOR_WILDFLY_REMOTO";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("contextInitialized");
		if (System.getenv("OPENSHIFT_DATA_DIR") != null) {
			System.setProperty(K_DIR_DATOS, System.getenv("OPENSHIFT_DATA_DIR"));
            System.setProperty(K_SERVIDOR, V_SERVIDOR_WILDFLY_REMOTO);
		} else {
			System.setProperty(K_DIR_DATOS, "C:\\Users\\Andres\\Downloads\\test\\TuMejorCompra\\img\\");
            System.setProperty(K_SERVIDOR, V_SERVIDOR_WILDFLY_LOCAL);
		}
		logger.info("{} = {}", K_SERVIDOR, System.getProperty(K_SERVIDOR));

        switch (System.getProperty(K_SERVIDOR)) {
            case V_SERVIDOR_WILDFLY_LOCAL: {
                System.setProperty(K_HOST, "192.168.0.101");
                System.setProperty(K_PUERTO_WEBSOCKETS, "8080");
                break;
            }
            case V_SERVIDOR_WILDFLY_REMOTO: {
                System.setProperty(K_HOST, "tumejorcompra-anfe0690.rhcloud.com");
                System.setProperty(K_PUERTO_WEBSOCKETS, "8000");
                break;
            }
            case V_SERVIDOR_GLASSFISH:{
                logger.warn("Control de Websockets para Glassfish no implementado!.");
                break;
            }
            default: {
                logger.error("Servidor desconocido! \"{}\"", System.getProperty(K_SERVIDOR));
            }
        }

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("contextDestroyed");
	}
}
