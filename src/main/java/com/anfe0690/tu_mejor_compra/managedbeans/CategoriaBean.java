package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.entity.Categoria;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import com.anfe0690.tu_mejor_compra.managedbeans.datos.Resultado;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class CategoriaBean {

	private static final Logger logger = LoggerFactory.getLogger(CategoriaBean.class);
	@PersistenceContext
	private EntityManager em;
	// 
	private String valor;
	private List<Resultado> resultados;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}
	
	public void buscar() {
		logger.debug("buscar categoria {}", valor);
		List<Resultado> res = new ArrayList<>();

		TypedQuery<Producto> qps = em.createQuery("SELECT p FROM Producto p WHERE p.categoria = :categoria", Producto.class)
				.setParameter("categoria", Categoria.valueOf(valor));
		List<Producto> pros = qps.getResultList();
		for (Producto p : pros) {
			TypedQuery<Usuario> qu = em.createQuery("SELECT u FROM Usuario u JOIN u.productos p WHERE p.id = :pid", Usuario.class)
					.setParameter("pid", p.getId());
			Usuario u = qu.getSingleResult();
			res.add(new Resultado(u, p));
		}
		resultados = res;
	}

	public String getCategoriaEstetica(){
		return Categoria.valueOf(valor).getValorEstetico();
	}
	
	public Categoria[] getCategorias(){
		return Categoria.values();
	}
	
	// Getters and Setters
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

}
