package com.anfe0690.tu_mejor_compra.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// TODO 02: Continuar con el reemplazo de las entidades Compra y Venta por Transaccion
@Entity
public class Transaccion implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	@OneToOne
	private Producto producto;
	@OneToOne
	private Usuario usuarioVendedor;
	@OneToOne
	private Usuario usuarioComprador;

	public Transaccion() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Usuario getUsuarioVendedor() {
		return usuarioVendedor;
	}

	public void setUsuarioVendedor(Usuario usuarioVendedor) {
		this.usuarioVendedor = usuarioVendedor;
	}

	public Usuario getUsuarioComprador() {
		return usuarioComprador;
	}

	public void setUsuarioComprador(Usuario usuarioComprador) {
		this.usuarioComprador = usuarioComprador;
	}

}
