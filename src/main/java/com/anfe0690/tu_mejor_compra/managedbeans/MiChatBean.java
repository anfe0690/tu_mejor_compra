package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ContextListener;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MiChatBean {

	public String getHost() {
		return System.getProperty(ContextListener.K_HOST);
	}

	public String getPuertoWebsockets() {
		return System.getProperty(ContextListener.K_PUERTO_WEBSOCKETS);
	}

}
