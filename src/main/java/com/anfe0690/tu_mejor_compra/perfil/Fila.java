/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra.perfil;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class Fila implements Serializable{

	private String direccionImagen;
	private String nombreProducto;
	private String estado;

	public String getDireccionImagen() {
		return direccionImagen;
	}

	public void setDireccionImagen(String direccionImagen) {
		this.direccionImagen = direccionImagen;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "FilaComprasDataTable{" + "dirImagen=" + direccionImagen + ", nombreProducto=" + nombreProducto + ", estado=" + estado + '}';
	}

}
