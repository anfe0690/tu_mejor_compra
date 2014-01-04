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
public class Compra implements Serializable {

	@Column(length = 20)
	private String vendedor;
	private int indiceProducto;
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private Estado estado;

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
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

	@Override
	public String toString() {
		return "Compra{" + "vendedor=" + vendedor + ", indiceProducto=" + indiceProducto + ", estado=" + estado + '}';
	}

}
