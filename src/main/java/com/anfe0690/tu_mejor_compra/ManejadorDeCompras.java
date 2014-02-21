package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
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
public class ManejadorDeCompras implements Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "tuMejorCompra")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
		Logger.getLogger(ManejadorDeCompras.class.getName()).log(Level.INFO, "postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
		Logger.getLogger(ManejadorDeCompras.class.getName()).log(Level.INFO, "preDestroy");
    }

    public void guardarCompra(Compra compra) {
        em.persist(compra);
    }

    public void removeCompra(Compra compra){
        em.remove(em.find(Compra.class, compra.getId()));
    }

    public void mergeCompra(Compra compra){
        em.merge(compra);
    }
}
