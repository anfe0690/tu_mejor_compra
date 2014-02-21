package com.anfe0690.tu_mejor_compra.perfil;

import com.anfe0690.tu_mejor_compra.Compra;
import com.anfe0690.tu_mejor_compra.Estado;
import com.anfe0690.tu_mejor_compra.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.Producto;
import com.anfe0690.tu_mejor_compra.SesionController;
import com.anfe0690.tu_mejor_compra.Usuario;
import com.anfe0690.tu_mejor_compra.Venta;

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
public class MisCompras implements Serializable {

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
		Logger.getLogger(MisCompras.class.getName()).log(Level.INFO, "postConstruct");

        for (Compra compra : sc.getUsuario().getCompras()) {
            Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
            Producto producto = compra.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/imgs/" + usuarioVendedor.getNombre() + "/" + producto.getNombreImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(compra.getEstado().toString());
            filas.add(fila);
        }
    }

    @PreDestroy
    public void preDestroy() {
		Logger.getLogger(MisCompras.class.getName()).log(Level.INFO, "preDestroy");
    }

    public String actualizar() {
        Usuario usuarioComprador = sc.getUsuario();
		Logger.getLogger(MisCompras.class.getName()).log(Level.INFO, "actualizar");
        for (Compra compra : usuarioComprador.getCompras()) {
            Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
            Producto producto = compra.getProducto();

            for (Fila fila : filas) {
                if (producto.getNombre().equals(fila.getNombreProducto())) {
                    if (!compra.getEstado().toString().equals(fila.getEstado())) {
						Logger.getLogger(MisCompras.class.getName()).log(Level.INFO, fila.toString());

                        compra.setEstado(Estado.TERMINADO);
                        manejadorDeCompras.mergeCompra(compra);
                        Logger.getLogger(MisCompras.class.getName()).log(Level.INFO, "compra = " + compra);
 
                        for (Venta venta : usuarioVendedor.getVentas()) {
                            if (compra.getProducto().getId() == venta.getProducto().getId()) {
                                venta.setEstado(Estado.TERMINADO);
                                manejadorDeVentas.mergeVenta(venta);
                                Logger.getLogger(MisCompras.class.getName()).log(Level.INFO, "venta = " + venta);
                            }
                        }

                        mu.mergeUsuario(usuarioComprador);
                        mu.mergeUsuario(usuarioVendedor);
                    }
                }
            }
        }
        sc.getUsuario().setCompras(mu.buscarUsuarioPorNombre(sc.getUsuario().getNombre()).getCompras());
        filas.clear();
        for (Compra compra : sc.getUsuario().getCompras()) {
            Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
            Producto producto = compra.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/imgs/" + usuarioVendedor.getNombre() + "/" + producto.getNombreImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(compra.getEstado().toString());
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
