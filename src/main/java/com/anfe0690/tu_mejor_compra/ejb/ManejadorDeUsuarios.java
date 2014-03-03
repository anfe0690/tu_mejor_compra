package com.anfe0690.tu_mejor_compra.ejb;

import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class ManejadorDeUsuarios implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ManejadorDeUsuarios.class);
	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager entityManager;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void guardarUsuario(Usuario usuario) {
		entityManager.persist(usuario);
	}

	public void mergeUsuario(Usuario usuario) {
		entityManager.flush();
		entityManager.merge(usuario);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario buscarUsuarioPorNombre(String nombre) throws IllegalArgumentException {
		try {
			return entityManager.find(Usuario.class, nombre.toLowerCase());
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	public void removeUsuario(Usuario usuario) {
		entityManager.remove(entityManager.find(Usuario.class, usuario.getNombre()));
	}

	public int removerTodosLosUsuarios(){
		Query q = entityManager.createQuery("DELETE FROM Usuario u");
		return q.executeUpdate();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario getUsuarioPadreDeProducto(long productoId) {
		TypedQuery<Usuario> typedQuery =
				entityManager.createQuery("SELECT u FROM Usuario u JOIN u.productos p WHERE p.id = :productoId", Usuario.class)
						.setParameter("productoId", productoId);
		return typedQuery.getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void refreshUsuario(Usuario usuario){
		Usuario usuarioRefrescado = entityManager.find(Usuario.class, usuario.getNombre());
		try {
			PropertyUtils.copyProperties(usuario, usuarioRefrescado);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error(null, e);
		}
	}
}
