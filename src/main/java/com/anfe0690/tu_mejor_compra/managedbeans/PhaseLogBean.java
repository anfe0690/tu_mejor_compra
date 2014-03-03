package com.anfe0690.tu_mejor_compra.managedbeans;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.PhaseEvent;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class PhaseLogBean {
	
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PhaseLogBean.class);
	
	public void beforePhase(PhaseEvent e){
		logger.trace("INICIA  {} {}", e.getPhaseId(),e.getFacesContext().getViewRoot().getViewId());
	}
	
	public void afterPhase(PhaseEvent e){
		logger.trace("TERMINA {} {}", e.getPhaseId(),e.getFacesContext().getViewRoot().getViewId());
	}
	
}
