package com.anfe0690.tu_mejor_compra.managedbeans.datos;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ProductoId {

	private int id;

	public ProductoId() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
