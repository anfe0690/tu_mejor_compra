package com.anfe0690.tu_mejor_compra.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ManejadorDeTransacciones {
	
	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "tuMejorCompra")
    private EntityManager em;
	
}
