/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra.entity;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public enum Estado implements Serializable {

	ESPERANDO_PAGO("Esperando pago"),
	EN_ENVIO("En envio"),
	TERMINADO("Terminado");

	private final String valorEstetico;

	private Estado(String valorEstetico) {
		this.valorEstetico = valorEstetico;
	}

	public String getValorEstetico() {
		return valorEstetico;
	}
}
