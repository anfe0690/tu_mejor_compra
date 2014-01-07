/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Andres
 */
@Entity
public class Compra implements Serializable {

	@Id
	@GeneratedValue
	private long id;
	@Column(length = 20)
	private String vendedor;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Producto producto; 
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private Estado estado;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Compra{" + "id=" + id + ", vendedor=" + vendedor + ", producto=" + producto.getId() + ", estado=" + estado + '}';
	}

}
