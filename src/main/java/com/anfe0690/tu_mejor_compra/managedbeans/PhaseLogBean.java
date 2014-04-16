package com.anfe0690.tu_mejor_compra.managedbeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class PhaseLogBean {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(PhaseLogBean.class);

    public void beforePhase(PhaseEvent e) {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        logger.trace("Session-id=\"{}\" INICIA  {} {}", sessionId, e.getPhaseId(), e.getFacesContext().getViewRoot().getViewId());
    }

    public void afterPhase(PhaseEvent e) {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        logger.trace("Session-id=\"{}\" TERMINA {} {}", sessionId, e.getPhaseId(), e.getFacesContext().getViewRoot().getViewId());
    }

}
