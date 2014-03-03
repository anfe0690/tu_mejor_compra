package com.anfe0690.tu_mejor_compra.managedbeans.datos;

import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;

public class FilaTransaccion {

	private Transaccion transaccion;
	private Estado nuevoEstado;

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public Estado getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(Estado nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

}
