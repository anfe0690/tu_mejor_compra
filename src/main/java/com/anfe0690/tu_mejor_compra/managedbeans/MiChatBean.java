package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.WebContainerListener;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MiChatBean {

	public String getHost() {
		return System.getProperty(WebContainerListener.K_HOST);
	}

	public String getPuertoWebsockets() {
		return System.getProperty(WebContainerListener.K_PUERTO_WEBSOCKETS);
	}

}
