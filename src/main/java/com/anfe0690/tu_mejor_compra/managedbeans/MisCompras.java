package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.managedbeans.datos.Fila;
import com.anfe0690.tu_mejor_compra.entity.Compra;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeCompras;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeVentas;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.managedbeans.SesionBean;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.entity.Venta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class MisCompras implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(MisCompras.class);
    // Otros
    @Inject
    private SesionBean sc;
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
		logger.trace("postConstruct");

        for (Compra compra : sc.getUsuario().getCompras()) {
            Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
            Producto producto = compra.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/imgs/" + producto.getDireccionImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(compra.getEstado().toString());
            filas.add(fila);
        }
    }

    @PreDestroy
    public void preDestroy() {
		logger.trace("preDestroy");
    }

    public String actualizar() {
        Usuario usuarioComprador = sc.getUsuario();
        for (Compra compra : usuarioComprador.getCompras()) {
            Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
            Producto producto = compra.getProducto();

            for (Fila fila : filas) {
                if (producto.getNombre().equals(fila.getNombreProducto())) {
                    if (!compra.getEstado().toString().equals(fila.getEstado())) {

                        compra.setEstado(Estado.TERMINADO);
                        manejadorDeCompras.mergeCompra(compra);
						logger.debug("Cambio de estado a TERMINADO de la compra = {}", compra);
 
                        for (Venta venta : usuarioVendedor.getVentas()) {
                            if (compra.getProducto().getId() == venta.getProducto().getId()) {
                                venta.setEstado(Estado.TERMINADO);
                                manejadorDeVentas.mergeVenta(venta);
								logger.debug("Cambio de estado a TERMINADO de la venta = {}", venta);
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
            Producto producto = compra.getProducto();
            Fila fila = new Fila();
            fila.setDireccionImagen("/imgs/" + producto.getDireccionImagen());
            fila.setNombreProducto(producto.getNombre());
            fila.setEstado(compra.getEstado().toString());
            filas.add(fila);
        }
		logger.info("actualizada MisCompras");
        return "";
    }

    public List<Fila> getFilas() {
        return filas;
    }

    public void setFilas(List<Fila> filas) {
        this.filas = filas;
    }

}
