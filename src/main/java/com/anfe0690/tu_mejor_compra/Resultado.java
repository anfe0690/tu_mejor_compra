/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class Resultado {

	private Usuario usuario;
	private Producto producto;
	private int indice;

	public Resultado(Usuario usuario, Producto producto) {
		this.usuario = usuario;
		this.producto = producto;
		this.indice = new ArrayList<>(usuario.getProductos()).indexOf(producto);
//		this.indice = usuario.getProductos().indexOf(producto);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

}
