package com.anfe0690.tu_mejor_compra.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Usuario implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(Usuario.class);
	private static final long serialVersionUID = 1L;

	// Campos generales
	@Id
	private String nombre;
	private String contrasena;
	private String nombreContacto;
	private String correo;
	private String telefonos;
	// Campos de comprador
	private String ciudad;
	private String direccion;
	// Campos de vendedor;
	private String banco;
	private String numeroCuenta;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Producto> productos;

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

	@Override
	public String toString() {
		return "Usuario{" + "nombre=" + nombre + ", contrasena=" + contrasena + ", nombreContacto=" + nombreContacto + '}';
	}

	public List<Producto> getProductosOrdenados() {
		List<Producto> list = new ArrayList<>(productos);
		Collections.sort(list, new Comparator<Producto>() {

			@Override
			public int compare(Producto o1, Producto o2) {
				int r = o1.getFechaDeCreacion().compareTo(o2.getFechaDeCreacion());
				if (r == 0) {
					if (o1.getId() <= o2.getId()) {
						r = -1;
					} else {
						r = 1;
					}
				}
				return r;
			}
		});
		return list;
	}
}
