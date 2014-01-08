/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named
@RequestScoped
public class Perfil {

    private static final MiLogger miLogger = new MiLogger(Perfil.class);

    @EJB
    private ManejadorDeUsuarios mu;

    @PostConstruct
    public void postConstruct() {
        miLogger.log("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        miLogger.log("preDestroy");
    }

    public Producto getProductoPorIndice(String usuario, int indice) {
        Usuario u = mu.buscarUsuarioPorNombre(usuario);
        return u.getProductos().get(indice);
    }

    public boolean compararEnumCompra(Compra compra, String strEstado) {
        miLogger.log("compra.getEstado():\"" + compra.getEstado() + "\" strEstado:\"" + strEstado + "\"");
        return compra.getEstado().toString().equals(strEstado);
    }

}
