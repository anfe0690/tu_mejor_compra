package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class Perfil {

	private static final Logger logger = LoggerFactory.getLogger(Perfil.class);
    @EJB
    private ManejadorDeUsuarios mu;

    @PostConstruct
    public void postConstruct() {
		logger.debug("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
		logger.debug("preDestroy");
    }

    public Producto getProductoPorIndice(String usuario, int indice) {
        Usuario u = mu.buscarUsuarioPorNombre(usuario);
        return new ArrayList<>(u.getProductos()).get(indice);
//        return u.getProductos().get(indice);
    }

    public boolean compararEnumCompra(Compra compra, String strEstado) {
		logger.debug("compra.getEstado(): \"{}\" strEstado: \"{}\"", compra.getEstado(), strEstado);
        return compra.getEstado().toString().equals(strEstado);
    }

}
