/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andres
 */
@Stateless
@LocalBean
public class ManejadorDeCompras implements Serializable {

    private static final MiLogger miLogger = new MiLogger(ManejadorDeCompras.class);

    @PersistenceContext(name = "tuMejorCompra")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        miLogger.log("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        miLogger.log("preDestroy");
    }

    public void guardarCompra(Compra compra) {
        em.persist(compra);
    }

    public void removeCompra(Compra compra){
        em.remove(em.find(Compra.class, compra.getId()));
    }

}
