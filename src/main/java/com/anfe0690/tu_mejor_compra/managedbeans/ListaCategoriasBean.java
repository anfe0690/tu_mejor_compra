package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Categoria;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class ListaCategoriasBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ListaCategoriasBean.class);
	//
	private String valor;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	// Getters and setters
	public String getCategoriaEstetica() {
		return Categoria.valueOf(valor).getValorEstetico();
	}

	public Categoria[] getCategorias() {
		return Categoria.values();
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
