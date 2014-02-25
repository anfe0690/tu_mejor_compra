package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Perfil {

    @EJB
    private ManejadorDeUsuarios mu;

    @PostConstruct
    public void postConstruct() {
		Logger.getLogger(Perfil.class.getName()).log(Level.INFO, "postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
		Logger.getLogger(Perfil.class.getName()).log(Level.INFO, "preDestroy");
    }

    public Producto getProductoPorIndice(String usuario, int indice) {
        Usuario u = mu.buscarUsuarioPorNombre(usuario);
        return new ArrayList<>(u.getProductos()).get(indice);
//        return u.getProductos().get(indice);
    }

    public boolean compararEnumCompra(Compra compra, String strEstado) {
		Logger.getLogger(Perfil.class.getName()).log(Level.INFO, "compra.getEstado():\"" + compra.getEstado() + "\" strEstado:\"" + strEstado + "\"");
        return compra.getEstado().toString().equals(strEstado);
    }

}
