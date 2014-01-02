/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class MisCompras implements Serializable {

	// Logger
	private static final Logger logger = Logger.getLogger(MisCompras.class.getName());

	// Otros
	@Inject
	private SesionController sc;
	@EJB
	private ManejadorDeUsuarios mu;

	// Modelo
	private List<Fila> filas = new ArrayList<>();

	@PostConstruct
	public void postConstruct() {
		logger.info("########## postConstruct");

		for (Compra compra : sc.getUsuario().getCompras()) {
			Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
			Producto producto = usuarioVendedor.getProductos().get(compra.getIndiceProducto());
			Fila fila = new Fila();
			fila.setDireccionImagen("/img/" + usuarioVendedor.getNombre() + "/" + producto.getNombreImagen());
			fila.setNombreProducto(producto.getNombre());
			fila.setEstado(compra.getEstado().toString());
			//logger.info("######### " + fila);
			filas.add(fila);
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("########## preDestroy");
	}

	public void actualizar() {
		Usuario usuarioComprador = sc.getUsuario();
		logger.info("########## actualizar");
		for (Compra compra : usuarioComprador.getCompras()) {
			Usuario usuarioVendedor = mu.buscarUsuarioPorNombre(compra.getVendedor());
			Producto producto = usuarioVendedor.getProductos().get(compra.getIndiceProducto());

			for (Fila fila : filas) {
				if (producto.getNombre().equals(fila.getNombreProducto())) {
					if (!compra.getEstado().toString().equals(fila.getEstado())) {
						logger.info(fila.toString());

						compra.setEstado(Estado.TERMINADO);

						for (Venta venta : usuarioVendedor.getVentas()) {
							if (compra.getIndiceProducto() == venta.getIndiceProducto()) {
								venta.setEstado(Estado.TERMINADO);
							}
						}

						EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
						EntityManager em = emf.createEntityManager();
						EntityTransaction et = em.getTransaction();

						et.begin();
						em.merge(usuarioComprador);
						em.merge(usuarioVendedor);
						et.commit();

						em.close();
						emf.close();
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
