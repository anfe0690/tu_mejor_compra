/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

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
public class ManejadorDeVentas {

    private static final MiLogger miLogger = new MiLogger(ManejadorDeVentas.class);

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

    public void guardarVenta(Venta venta) {
        em.persist(venta);
    }
    
    public void removeVenta(Venta venta){
        em.remove(em.find(Venta.class, venta.getId()));
    }

    public void mergeVenta(Venta venta){
        em.merge(venta);
    }
}
