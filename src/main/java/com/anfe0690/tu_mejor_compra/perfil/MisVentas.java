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

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class MisVentas implements Serializable{

	// Logger
	private static final Logger logger = Logger.getLogger(MisVentas.class.getName());

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

		for (Venta venta : sc.getUsuario().getVentas()) {
			//Usuario usuarioComprador = mu.buscarUsuarioPorNombre(venta.getComprador());
			Producto producto = sc.getUsuario().getProductos().get(venta.getIndiceProducto());
			Fila fila = new Fila();
			fila.setDireccionImagen("/img/" + sc.getUsuario().getNombre() + "/" + producto.getNombreImagen());
			fila.setNombreProducto(producto.getNombre());
			fila.setEstado(venta.getEstado().toString());
			filas.add(fila);
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("########## preDestroy");
	}

	public void actualizar(){
		logger.info("########## actualizar");
		
		for(Venta venta:sc.getUsuario().getVentas()){
			Usuario usuarioComprador = mu.buscarUsuarioPorNombre(venta.getComprador());
			
			Producto producto = sc.getUsuario().getProductos().get(venta.getIndiceProducto());
			
			for(Fila fila:filas){
				if (producto.getNombre().equals(fila.getNombreProducto())) {
					if(!venta.getEstado().toString().equals(fila.getEstado())){
						logger.info(fila.toString());
						
						venta.setEstado(Estado.EN_ENVIO);
						
						for(Compra compra:usuarioComprador.getCompras()){
							if(compra.getIndiceProducto() == venta.getIndiceProducto()){
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
