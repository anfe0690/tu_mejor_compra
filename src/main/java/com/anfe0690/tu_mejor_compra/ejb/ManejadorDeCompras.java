package com.anfe0690.tu_mejor_compra.ejb;

import com.anfe0690.tu_mejor_compra.entity.Compra;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class ManejadorDeCompras implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ManejadorDeCompras.class);
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

    public void guardarCompra(Compra compra) {
        em.persist(compra);
    }

    public void removeCompra(Compra compra){
        em.remove(em.find(Compra.class, compra.getId()));
    }

	public int removerTodasLasCompras(){
		Query q = em.createQuery("DELETE FROM Compra c");
		return q.executeUpdate();
	}
	
    public void mergeCompra(Compra compra){
        em.merge(compra);
    }
}
