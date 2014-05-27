package com.anfe0690.tu_mejor_compra.managedbeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GlobalFacesMessages {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(GlobalFacesMessages.class);

    @PostConstruct
    public void postConstruct() {
        logger.trace("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        logger.trace("preDestroy");
    }

    public List<FacesMessage> getGlobalFacesMessages(){
        return FacesContext.getCurrentInstance().getMessageList(null);
    }

}
