/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class Index implements Serializable {

    // Logger
    private static final MiLogger miLogger = new MiLogger(Index.class);

    // Modelo
    private List<ProductoReciente> productosRecientes = new ArrayList<>();

    @PostConstruct
    public void postConstruct() {
        miLogger.log("postConstruct");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        try {
            List<Usuario> rl = q.getResultList();
            for (Usuario u : rl) {
                //logger.info("usuario: " + u.getNombre());

                for (Producto p : u.getProductos()) {

                    ProductoReciente pr = new ProductoReciente();
                    pr.setUsuario(u);
                    pr.setProducto(p);
                    productosRecientes.add(pr);
                }

            }

            Collections.sort(productosRecientes, new Comparator<ProductoReciente>() {

                @Override
                public int compare(ProductoReciente o1, ProductoReciente o2) {
                    Date date1 = o1.getProducto().getFechaDeCreacion();
                    Date date2 = o2.getProducto().getFechaDeCreacion();
                    return date1.compareTo(date2);
                }
            });
            Collections.reverse(productosRecientes);

            productosRecientes = productosRecientes.subList(0, 3);
        } catch (Exception e) {
            miLogger.error(e);
        }

        em.close();
        emf.close();

    }

    @PreDestroy
    public void preDestroy() {
        miLogger.log("preDestroy");
    }

    public List<ProductoReciente> getProductosRecientes() {
        return productosRecientes;
    }

    public void setProductosRecientes(List<ProductoReciente> productosRecientes) {
        this.productosRecientes = productosRecientes;
    }

}
