package com.anfe0690.tu_mejor_compra.ejb;

import com.anfe0690.tu_mejor_compra.entity.Venta;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class ManejadorDeVentas implements Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "tuMejorCompra")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
		Logger.getLogger(ManejadorDeVentas.class.getName()).log(Level.INFO, "postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
		Logger.getLogger(ManejadorDeVentas.class.getName()).log(Level.INFO, "preDestroy");
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
