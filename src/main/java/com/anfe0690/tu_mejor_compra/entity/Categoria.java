package com.anfe0690.tu_mejor_compra.entity;

import java.io.Serializable;

public enum Categoria implements Serializable {

	CONSOLAS_VIDEO_JUEGOS("Consolas de video juegos"),
    TABLETAS("Tabletas"),
    TELEFONOS_INTELIGENTES("Telefonos inteligentes");

	private final String valorEstetico;

	private Categoria(String valorEstetico) {
		this.valorEstetico = valorEstetico;
	}

	public String getValorEstetico() {
		return valorEstetico;
	}

}
