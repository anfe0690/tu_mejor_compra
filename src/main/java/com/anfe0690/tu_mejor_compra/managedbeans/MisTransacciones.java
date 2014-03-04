package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeTransacciones;
import com.anfe0690.tu_mejor_compra.entity.Estado;
import com.anfe0690.tu_mejor_compra.entity.Transaccion;
import com.anfe0690.tu_mejor_compra.managedbeans.datos.FilaTransaccion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO 101: Buscar alternativa a cargar los beans en los constructores, evaluar si se cambia a ViewScoped

@Named
@RequestScoped
public class MisTransacciones {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MisTransacciones.class);
	// Datos
	private List<FilaTransaccion> misVentas;
	private List<FilaTransaccion> misCompras;
	// Injecciones
	@Inject
	private SesionBean sesionBean;
	@EJB
	private ManejadorDeTransacciones manejadorDeTransacciones;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
		misVentas = new ArrayList<>();
		for (Transaccion t : manejadorDeTransacciones.obtenerTransaccionesTipoVentaDeUsuario(sesionBean.getUsuario())) {
			FilaTransaccion ft = new FilaTransaccion();
			ft.setTransaccion(t);
			ft.setNuevoEstado(t.getEstado());
			misVentas.add(ft);
		}
		misCompras = new ArrayList<>();
		for (Transaccion t : manejadorDeTransacciones.obtenerTransaccionesTipoCompraDeUsuario(sesionBean.getUsuario())) {
			FilaTransaccion ft = new FilaTransaccion();
			ft.setTransaccion(t);
			ft.setNuevoEstado(t.getEstado());
			misCompras.add(ft);
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public void actualizarCompras() {
		for (FilaTransaccion ft : misCompras) {
			if (!ft.getNuevoEstado().equals(ft.getTransaccion().getEstado())) {
				ft.getTransaccion().setEstado(Estado.TERMINADO);
				manejadorDeTransacciones.mergeTransaccion(ft.getTransaccion());
				logger.info("Actualizada transaccion {}", ft.getTransaccion());
			}
		}
		logger.info("Actualizadas transacciones tipo compra");
	}

	public void actualizarVentas() {
		for (FilaTransaccion ft : misVentas) {
			if (!ft.getNuevoEstado().equals(ft.getTransaccion().getEstado())) {
				ft.getTransaccion().setEstado(Estado.EN_ENVIO);
				manejadorDeTransacciones.mergeTransaccion(ft.getTransaccion());
				logger.info("Actualizada transaccion {}", ft.getTransaccion());
			}
		}
		logger.info("Actualizadas transacciones tipo venta");
	}

	// Getters Setters
	public List<FilaTransaccion> getMisVentas() {
		return misVentas;
	}

	public List<FilaTransaccion> getMisCompras() {
		return misCompras;
	}

}
