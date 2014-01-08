/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class ComprarProducto implements Serializable {

    private static final MiLogger miLogger = new MiLogger(ComprarProducto.class);

    @EJB
    private ManejadorDeUsuarios mdu;
    @Inject
    private SesionController sc;
    private Usuario usuarioVendedor;
    private Producto producto;

    @PostConstruct
    public void postConstruct() {
        miLogger.log("postConstruct");
        Map<String, String> pm = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        //logger.info("usuario: " + pm.get("u") + " - indiceProducto: " + pm.get("p"));
        String nombreUsuario = pm.get("u");
        String indiceProducto = pm.get("p");

        usuarioVendedor = mdu.buscarUsuarioPorNombre(nombreUsuario);
        producto = usuarioVendedor.getProductos().get(Integer.parseInt(indiceProducto));
    }

    @PreDestroy
    public void preDestroy() {
        miLogger.log("preDestroy");
    }

    public Usuario getUsuarioVendedor() {
        return usuarioVendedor;
    }

    public void setUsuarioVendedor(Usuario usuarioVendedor) {
        this.usuarioVendedor = usuarioVendedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String comprar() {
        Usuario usuarioComprador = sc.getUsuario();
        Compra compra = new Compra();
        compra.setVendedor(usuarioVendedor.getNombre());
        compra.setProducto(producto);
        compra.setEstado(Estado.ESPERANDO_PAGO);
        usuarioComprador.getCompras().add(compra);

        Venta venta = new Venta();
        venta.setComprador(usuarioComprador.getNombre());
        venta.setProducto(producto);
        venta.setEstado(Estado.ESPERANDO_PAGO);
        usuarioVendedor.getVentas().add(venta);

        mdu.mergeUsuario(usuarioComprador);
        mdu.mergeUsuario(usuarioVendedor);

        miLogger.log(usuarioComprador.getNombre() + " compro \"" + producto.getNombre() + "\" de " + usuarioVendedor.getNombre());
        return "perfil";
    }

}
