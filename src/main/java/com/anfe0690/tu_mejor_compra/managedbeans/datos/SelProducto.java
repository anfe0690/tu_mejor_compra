package com.anfe0690.tu_mejor_compra.managedbeans.datos;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.io.Serializable;

public class SelProducto implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean seleccionado;
	private Producto producto;

	public SelProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
