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
public class ManejadorDeProductos {

    private static final MiLogger miLogger = new MiLogger(ManejadorDeProductos.class);

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

    public Producto obtenerProductoPorId(long id){
        return em.find(Producto.class, id);
    }
    
    public void removerProducto(Producto producto){
        em.remove(em.find(Producto.class, producto.getId()));
    }
    
    public void mergeProducto(Producto producto){
        em.merge(producto);
    }

}
