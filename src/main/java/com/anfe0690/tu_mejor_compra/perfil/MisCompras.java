/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra.perfil;

import com.anfe0690.tu_mejor_compra.Compra;
import com.anfe0690.tu_mejor_compra.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.Producto;
import com.anfe0690.tu_mejor_compra.SesionController;
import com.anfe0690.tu_mejor_compra.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	@Inject
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

	public void actualizar(){
		Usuario usuarioComprador = sc.getUsuario();
		
		logger.info("########## actualizar");
		for(Fila fila:filas){
			logger.info("########## " + fila);
		}
	}
	
	public List<Fila> getFilas() {
		return filas;
	}

	public void setFilas(List<Fila> filas) {
		this.filas = filas;
	}

}
