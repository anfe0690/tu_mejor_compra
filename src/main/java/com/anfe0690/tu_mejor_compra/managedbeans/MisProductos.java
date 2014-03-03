package com.anfe0690.tu_mejor_compra.managedbeans;

import com.anfe0690.tu_mejor_compra.managedbeans.datos.SelProducto;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeUsuarios;
import com.anfe0690.tu_mejor_compra.ejb.ManejadorDeProductos;
import com.anfe0690.tu_mejor_compra.entity.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class MisProductos implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MisProductos.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private SesionBean sesionController;
	private final List<SelProducto> selProductos = new ArrayList<>();
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;

	@PostConstruct
	public void postConstruct() {
		logger.trace("postConstruct");
		for (Producto producto : sesionController.getUsuario().getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("preDestroy");
	}

	public List<SelProducto> getSelProductos() {
		return selProductos;
	}

//	public void eliminarProductos() {
//		Usuario usuarioVendedor = sesionController.getUsuario();
//		Iterator<SelProducto> it = selProductos.iterator();
//		while (it.hasNext()) {
//			SelProducto sp = it.next();
//			if (sp.isSeleccionado()) {
//				Producto producto = sp.getProducto();
//				// Remover ventas relacionadas
//				Iterator<Venta> ventasIt = usuarioVendedor.getVentas().iterator();
//				while (ventasIt.hasNext()) {
//					Venta venta = ventasIt.next();
//					if (venta.getProducto().equals(producto)) {
//						Usuario usuarioComprador = manejadorDeUsuarios.buscarUsuarioPorNombre(venta.getComprador());
//						Iterator<Compra> comprasIt = usuarioComprador.getCompras().iterator();
//						// Remover compras relacionadas
//						while (comprasIt.hasNext()) {
//							Compra compra = comprasIt.next();
//							if (compra.getVendedor().equals(usuarioVendedor.getNombre())
//									&& compra.getProducto().getId() == venta.getProducto().getId()) {
//								// Remover de la colleccion la compra
//								comprasIt.remove();
//								manejadorDeUsuarios.mergeUsuario(usuarioComprador);
//								manejadorDeCompras.removeCompra(compra);
//								logger.debug("Eliminada compra: {}", compra);
//							}
//						}
//						// Remover de la colleccion la venta
////						Logger.getLogger(MisProductos.class.getName()).log(Level.INFO,
////								"Ventas: " + Arrays.toString(usuarioVendedor.getVentas().toArray()));
//						ventasIt.remove();
//						manejadorDeUsuarios.mergeUsuario(usuarioVendedor);
//						manejadorDeVentas.removeVenta(venta);
//						logger.debug("Eliminada venta: {}", venta);
//					}
//				}
//				
//				usuarioVendedor.setVentas(manejadorDeUsuarios.buscarUsuarioPorNombre(usuarioVendedor.getNombre()).getVentas());
////				manejadorDeUsuarios.refreshUsuario(usuarioVendedor);
//				// Remover de selProductos
//				it.remove();
//				// Remover de la colleccion el producto
//				boolean res = usuarioVendedor.getProductos().remove(producto);
////				Logger.getLogger(MisProductos.class.getName()).log(Level.INFO,
////						"Ventas: " + Arrays.toString(usuarioVendedor.getVentas().toArray()));
//				manejadorDeUsuarios.mergeUsuario(usuarioVendedor);
//				manejadorDeProductos.removerProducto(producto);
//				
//				File f =
//						new File(System.getProperty(WebContainerListener.DIR_DATOS) + producto.getDireccionImagen());
//				try {
//					Files.deleteIfExists(f.toPath());
//				} catch (Exception ex) {
//					FacesContext fc = FacesContext.getCurrentInstance();
//					fc.addMessage(null, new FacesMessage(ex.toString()));
//					logger.error(null, ex);
//				}
//			}
//		}
//		// Actualizar ventas en pagina
//		misVentas.postConstruct();
//	}

}
