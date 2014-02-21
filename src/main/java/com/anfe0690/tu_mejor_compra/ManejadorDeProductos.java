package com.anfe0690.tu_mejor_compra;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ManejadorDeProductos {

    @PersistenceContext(name = "tuMejorCompra")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
		Logger.getLogger(ManejadorDeProductos.class.getName()).log(Level.INFO, "postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
		Logger.getLogger(ManejadorDeProductos.class.getName()).log(Level.INFO, "preDestroy");
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
