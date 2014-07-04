package com.anfe0690.tu_mejor_compra.managedbeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class HeaderBean {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(HeaderBean.class);
    //
    private static final List<String> palabrasMasUsadas = Arrays.asList("Ram","Tablet","Android","Samsung","Lte","Nintendo");
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

    public List<String> completar(String s) {
        logger.trace("completar \"{}\"", s);
        List<String> rs = new ArrayList<>();
        for(String smu:palabrasMasUsadas){
            if(smu.toLowerCase().contains(s.toLowerCase())){
                rs.add(smu);
            }
        }
        return rs;
    }

	public String buscar(){
		return "buscar?faces-redirect=true&amp;valor=" + valor;
	}
	
    // Getters and setters
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
