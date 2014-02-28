package com.anfe0690.tu_mejor_compra.ejb;

import com.anfe0690.tu_mejor_compra.entity.Venta;
import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class ManejadorDeVentas implements Serializable{

	private static final Logger logger = LoggerFactory.getLogger(ManejadorDeVentas.class);
	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "tuMejorCompra")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
		logger.debug("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
		logger.debug("preDestroy");
    }

    public void guardarVenta(Venta venta) {
        em.persist(venta);
    }
    
    public void removeVenta(Venta venta){
        em.remove(em.find(Venta.class, venta.getId()));
    }

	public int removerTodasLasVentas(){
		Query q = em.createQuery("DELETE FROM Venta v");
		return q.executeUpdate();
	}
	
    public void mergeVenta(Venta venta){
        em.merge(venta);
    }
    
}
