/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra.perfil;

import com.anfe0690.tu_mejor_compra.Compra;
import com.anfe0690.tu_mejor_compra.Estado;
import com.anfe0690.tu_mejor_compra.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.MiLogger;
import com.anfe0690.tu_mejor_compra.Producto;
import com.anfe0690.tu_mejor_compra.SesionController;
import com.anfe0690.tu_mejor_compra.Usuario;
import com.anfe0690.tu_mejor_compra.Venta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class MisVentas implements Serializable {

    // Logger
    private static final MiLogger miLogger = new MiLogger(MisVentas.class);

    // Otros
    @Inject
    private SesionController sc;
    @EJB
    private ManejadorDeUsuarios mu;

    // Modelo
    private List<Fila> filas = new ArrayList<>();

    @PostConstruct
    public void postConstruct() {
        miLogger.log("postConstruct");

        filas.clear();
        for (Venta venta : sc.getUsuario().getVentas()) {
            //Usuario usuarioComprador = mu.buscarUsuarioPorNombre(venta.getComprador());
            Producto producto = venta.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/img/" + sc.getUsuario().getNombre() + "/" + producto.getNombreImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(venta.getEstado().toString());
            filas.add(fila);
        }
    }

    @PreDestroy
    public void preDestroy() {
        miLogger.log("preDestroy");
    }

    public void actualizar() {
        miLogger.log("actualizar");

        for (Venta venta : sc.getUsuario().getVentas()) {
            Usuario usuarioComprador = mu.buscarUsuarioPorNombre(venta.getComprador());

            Producto producto = venta.getProducto();

            for (Fila fila : filas) {
                if (producto.getNombre().equals(fila.getNombreProducto())) {
                    if (!venta.getEstado().toString().equals(fila.getEstado())) {
                        miLogger.log(fila.toString());

                        venta.setEstado(Estado.EN_ENVIO);

                        for (Compra compra : usuarioComprador.getCompras()) {
                            if (compra.getProducto().equals(venta.getProducto())) {
                                compra.setEstado(Estado.EN_ENVIO);
                            }
                        }

						//EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
                        //EntityManager em = emf.createEntityManager();
                        //EntityTransaction et = em.getTransaction();
						//et.begin();
                        //em.merge(usuarioComprador);
                        mu.mergeUsuario(usuarioComprador);
                        //em.merge(sc.getUsuario());
                        mu.mergeUsuario(sc.getUsuario());
						//et.commit();

						//em.close();
                        //emf.close();
                    }
                }
            }
        }
    }

    public List<Fila> getFilas() {
        return filas;
    }

    public void setFilas(List<Fila> filas) {
        this.filas = filas;
    }

}
