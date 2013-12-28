/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Andres
 */
@Embeddable
public class Venta implements Serializable{

	@Column(length = 20)
	private String comprador;
	private int indiceProducto;
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private Estado estado;

	public String getComprador() {
		return comprador;
	}

	public void setComprador(String comprador) {
		this.comprador = comprador;
	}

	public int getIndiceProducto() {
		return indiceProducto;
	}

	public void setIndiceProducto(int indiceProducto) {
		this.indiceProducto = indiceProducto;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
