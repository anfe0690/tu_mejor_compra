package com.anfe0690.tu_mejor_compra;

import java.io.Serializable;

public class Sesion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String contraseña;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	
	
}
