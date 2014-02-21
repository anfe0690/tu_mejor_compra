package com.anfe0690.tu_mejor_compra.perfil;

import com.anfe0690.tu_mejor_compra.Compra;
import com.anfe0690.tu_mejor_compra.Estado;
import com.anfe0690.tu_mejor_compra.ManejadorDeUsuarios;
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
public class MisVentas implements Serializable {

    // Otros
    @Inject
    private SesionController sc;
    @EJB
    private ManejadorDeUsuarios mu;

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

                        for (Compra compra : usuarioComprador.getCompras()) {
                            if (compra.getProducto().equals(venta.getProducto())) {
                                compra.setEstado(Estado.EN_ENVIO);
                            }
                        }

                        mu.mergeUsuario(usuarioComprador);
                        mu.mergeUsuario(sc.getUsuario());
                    }
                }
            }
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
