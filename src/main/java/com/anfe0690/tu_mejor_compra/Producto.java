/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Andres
 */
@Embeddable
public class Producto implements Serializable {

	@Column(length = 30)
	private String nombreImagen;
	@NotNull
	@Column(length = 80)
	private String nombre;
	@Column(length = 20)
	private String precio;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDeCreacion;
	@Column(length = 30)
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Producto{" + "nombreImagen=" + nombreImagen + ", nombre=" + nombre + ", precio=" + precio + '}';
	}

}
