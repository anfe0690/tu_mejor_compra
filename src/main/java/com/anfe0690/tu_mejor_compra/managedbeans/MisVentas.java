package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.managedbeans.md.Fila;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.managedbeans.SesionController;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.entity.Venta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class MisVentas implements Serializable {

    // Otros
    @Inject
    private SesionController sc;
    @EJB
    private ManejadorDeUsuarios mu;
    @EJB
    private ManejadorDeVentas manejadorDeVentas;
    @EJB
    private ManejadorDeCompras manejadorDeCompras;
    
    // Modelo
    private List<Fila> filas = new ArrayList<>();

    @PostConstruct
    public void postConstruct() {
		Logger.getLogger(MisVentas.class.getName()).log(Level.INFO, "postConstruct");

        filas.clear();
        for (Venta venta : sc.getUsuario().getVentas()) {
            //Usuario usuarioComprador = mu.buscarUsuarioPorNombre(venta.getComprador());
            Producto producto = venta.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/imgs/" + sc.getUsuario().getNombre() + "/" + producto.getNombreImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(venta.getEstado().toString());
            filas.add(fila);
        }
    }

    @PreDestroy
    public void preDestroy() {
		Logger.getLogger(MisVentas.class.getName()).log(Level.INFO, "preDestroy");
    }

    public String actualizar() {
		Logger.getLogger(MisVentas.class.getName()).log(Level.INFO, "actualizar");

        for (Venta venta : sc.getUsuario().getVentas()) {
            Usuario usuarioComprador = mu.buscarUsuarioPorNombre(venta.getComprador());

            Producto producto = venta.getProducto();

            for (Fila fila : filas) {
                if (producto.getNombre().equals(fila.getNombreProducto())) {
                    if (!venta.getEstado().toString().equals(fila.getEstado())) {
						Logger.getLogger(MisVentas.class.getName()).log(Level.INFO, fila.toString());

                        venta.setEstado(Estado.EN_ENVIO);
                        manejadorDeVentas.mergeVenta(venta);
                        Logger.getLogger(MisVentas.class.getName()).log(Level.INFO, "venta = " + venta);

                        for (Compra compra : usuarioComprador.getCompras()) {
                            if (compra.getProducto().getId() == venta.getProducto().getId()) {
                                compra.setEstado(Estado.EN_ENVIO);
                                manejadorDeCompras.mergeCompra(compra);
                                Logger.getLogger(MisVentas.class.getName()).log(Level.INFO, "compra = " + compra);
                            }
                        }

                        mu.mergeUsuario(usuarioComprador);
                        mu.mergeUsuario(sc.getUsuario());
                    }
                }
            }
        }
        sc.getUsuario().setVentas(mu.buscarUsuarioPorNombre(sc.getUsuario().getNombre()).getVentas());
        filas.clear();
        for (Venta venta : sc.getUsuario().getVentas()) {
            Producto producto = venta.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/imgs/" + sc.getUsuario().getNombre() + "/" + producto.getNombreImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(venta.getEstado().toString());
            filas.add(fila);
        }
        return "";
    }

    public List<Fila> getFilas() {
        return filas;
    }

    public void setFilas(List<Fila> filas) {
        this.filas = filas;
    }

}
