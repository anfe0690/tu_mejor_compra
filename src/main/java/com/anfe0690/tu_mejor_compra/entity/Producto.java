package com.anfe0690.tu_mejor_compra.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

// TODO 098: Agregar relacion bidireccional con el usuario dueño
@Entity
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String nombre;
	private String precio;
	private String direccionImagen;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDeCreacion;
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	public Producto() {
	}

	public Producto(String nombre, String precio, String direccionImagen, Categoria categoria) {
		this.nombre = nombre;
		this.precio = precio;
		this.direccionImagen = direccionImagen;
		this.fechaDeCreacion = new Date();
		this.categoria = categoria;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDireccionImagen() {
		return direccionImagen;
	}

	public void setDireccionImagen(String direccionImagen) {
		this.direccionImagen = direccionImagen;
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
		return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", direccionImagen=" + direccionImagen + ", fechaDeCreacion=" + fechaDeCreacion + ", categoria=" + categoria + '}';
	}

}
