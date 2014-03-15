package com.anfe0690.tu_mejor_compra.managedbeans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class TestBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(TestBean.class);

	private String valorPrueba;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public String getValorPrueba() {
		logger.trace("getValorPrueba() = {}", valorPrueba);
		return valorPrueba;
	}

	public void setValorPrueba(String valorPrueba) {
		logger.trace("setValorPrueba(\"{}\")", valorPrueba);
		this.valorPrueba = valorPrueba;
	}

}
