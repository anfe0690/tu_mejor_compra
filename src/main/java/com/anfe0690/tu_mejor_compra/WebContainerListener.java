package com.anfe0690.tu_mejor_compra;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebContainerListener implements ServletContextListener {
	
	public static final String DIR_DATOS = "dir_datos";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger.getLogger(WebContainerListener.class.getName()).log(Level.INFO, "contextInitialized");
		System.setProperty(DIR_DATOS, "C:\\Users\\Andres\\Downloads\\test\\TuMejorCompra\\img\\");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Logger.getLogger(WebContainerListener.class.getName()).log(Level.INFO, "contextDestroyed");
	}

}
