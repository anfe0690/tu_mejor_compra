package com.anfe0690.tu_mejor_compra.managedbeans.datos;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;

public class ProductoReciente implements Serializable{

	private Usuario usuario;
	private Producto producto;

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

	public int indiceDeProducto(Producto producto){
		return new ArrayList<>(usuario.getProductos()).indexOf(producto);
	}
	
}
