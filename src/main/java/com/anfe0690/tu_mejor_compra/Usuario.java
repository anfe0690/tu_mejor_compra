package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	// Campos generales
	@Id
	@Column(length = 20)
	private String nombre;
	@Column(length = 10)
	private String contrasena;
	@Column(length = 30)
	private String nombreContacto;
	@Column(length = 30)
	private String correo;
	@Column(length = 30)
	private String telefonos;
	// Campos de comprador
	@Column(length = 20)
	private String ciudad;
	@Column(length = 30)
	private String direccion;
	// Campos de vendedor;
	@Column(length = 20)
	private String banco;
	@Column(length = 20)
	private String numeroCuenta;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Producto> productos;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Compra> compras;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Venta> ventas;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public Set<Compra> getCompras() {
		return compras;
	}

	public void setCompras(Set<Compra> compras) {
		this.compras = compras;
	}

	public Set<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(Set<Venta> ventas) {
		this.ventas = ventas;
	}

	@Override
	public String toString() {
		return "Usuario{" + "nombre=" + nombre + ", contrasena=" + contrasena + ", nombreContacto=" + nombreContacto + '}';
	}

}
