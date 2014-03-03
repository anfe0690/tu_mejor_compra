package com.anfe0690.tu_mejor_compra.ejb;

import com.anfe0690.tu_mejor_compra.entity.Producto;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;
import com.anfe0690.tu_mejor_compra.entity.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class ManejadorDeTransacciones {

	private static final long serialVersionUID = 1L;
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ManejadorDeTransacciones.class);
	@PersistenceContext(name = "tuMejorCompra")
	private EntityManager em;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void guardarTransaccion(Transaccion t) {
		em.persist(t);
	}

	public void mergeTransaccion(Transaccion t){
		em.merge(t);
	}
	
	public int removerTodasLasTransacciones() {
		Query q = em.createQuery("DELETE FROM Transaccion t");
		return q.executeUpdate();
	}

	public List<Transaccion> obtenerTransaccionesTipoCompraDeUsuario(Usuario usuario) {
		TypedQuery<Transaccion> q = em.createQuery("SELECT t FROM Transaccion t WHERE t.usuarioComprador.nombre = '"
				+ usuario.getNombre() + "'", Transaccion.class);
		return q.getResultList();
	}
	
	public List<Transaccion> obtenerTransaccionesTipoVentaDeUsuario(Usuario usuario) {
		TypedQuery<Transaccion> q = em.createQuery("SELECT t FROM Transaccion t WHERE t.usuarioVendedor.nombre = '" + usuario.getNombre() + "'", Transaccion.class);
		return q.getResultList();
	}
	
	public List<Transaccion> obtenerTransaccionesRelacionadasConUsuario(Usuario usuario) {
		TypedQuery<Transaccion> q = em.createQuery("SELECT t FROM Transaccion t WHERE t.usuarioVendedor.nombre = '" + usuario.getNombre()
				+ "' OR t.usuarioComprador.nombre = '" + usuario.getNombre() + "'", Transaccion.class);
		return q.getResultList();
	}

	public List<Transaccion> obtenerTransaccionesRelacionadasConProducto(Producto producto) {
		TypedQuery<Transaccion> q = em.createQuery("SELECT t FROM Transaccion t WHERE t.producto.id = :productoId", Transaccion.class)
				.setParameter("productoId", producto.getId());
		return q.getResultList();
	}
}
