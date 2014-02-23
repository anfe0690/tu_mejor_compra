package com.anfe0690.tu_mejor_compra;

import com.anfe0690.tu_mejor_compra.perfil.MisVentas;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;

@Named
@ViewScoped
public class MisProductos implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private SesionController sesionController;
	private final List<SelProducto> selProductos = new ArrayList<>();
	@EJB
	private ManejadorDeUsuarios manejadorDeUsuarios;
	@EJB
	private ManejadorDeProductos manejadorDeProductos;
	@EJB
	private ManejadorDeVentas manejadorDeVentas;
	@EJB
	private ManejadorDeCompras manejadorDeCompras;
	@Inject
	private MisVentas misVentas;

	@PostConstruct
	public void postConstruct() {
		Logger.getLogger(MisProductos.class.getName()).log(Level.INFO, "postConstruct");
		for (Producto producto : sesionController.getUsuario().getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
	}

	@PreDestroy
	public void preDestroy() {
		Logger.getLogger(MisProductos.class.getName()).log(Level.INFO, "preDestroy");
	}

	public List<SelProducto> getSelProductos() {
		return selProductos;
	}

	public void eliminarProductos() {
		Usuario usuarioVendedor = sesionController.getUsuario();
		Iterator<SelProducto> it = selProductos.iterator();
		while (it.hasNext()) {
			SelProducto sp = it.next();
			if (sp.isSeleccionado()) {
				Producto producto = sp.getProducto();
				// Remover ventas relacionadas
				Iterator<Venta> ventasIt = usuarioVendedor.getVentas().iterator();
				while (ventasIt.hasNext()) {
					Venta venta = ventasIt.next();
					if (venta.getProducto().equals(producto)) {
						Usuario usuarioComprador = manejadorDeUsuarios.buscarUsuarioPorNombre(venta.getComprador());
						Iterator<Compra> comprasIt = usuarioComprador.getCompras().iterator();
						// Remover compras relacionadas
						while (comprasIt.hasNext()) {
							Compra compra = comprasIt.next();
							if (compra.getVendedor().equals(usuarioVendedor.getNombre())
									&& compra.getProducto().getId() == venta.getProducto().getId()) {
								// Remover de la colleccion la compra
								comprasIt.remove();
								manejadorDeUsuarios.mergeUsuario(usuarioComprador);
								manejadorDeCompras.removeCompra(compra);
								Logger.getLogger(MisProductos.class.getName()).log(Level.INFO, "Eliminada compra: " + compra);
							}
						}
						// Remover de la colleccion la venta
//						Logger.getLogger(MisProductos.class.getName()).log(Level.INFO,
//								"Ventas: " + Arrays.toString(usuarioVendedor.getVentas().toArray()));
						ventasIt.remove();
						manejadorDeUsuarios.mergeUsuario(usuarioVendedor);
						manejadorDeVentas.removeVenta(venta);
						Logger.getLogger(MisProductos.class.getName()).log(Level.INFO, "Eliminada venta: " + venta);
					}
				}
				usuarioVendedor.setVentas(manejadorDeUsuarios.buscarUsuarioPorNombre(usuarioVendedor.getNombre()).getVentas());
//				manejadorDeUsuarios.refreshUsuario(usuarioVendedor);
				// Remover de selProductos
				it.remove();
				// Remover de la colleccion el producto
				boolean res = usuarioVendedor.getProductos().remove(producto);
//				Logger.getLogger(MisProductos.class.getName()).log(Level.INFO,
//						"Ventas: " + Arrays.toString(usuarioVendedor.getVentas().toArray()));
				manejadorDeUsuarios.mergeUsuario(usuarioVendedor);
				manejadorDeProductos.removerProducto(producto);
				
				File f =
						new File(System.getProperty(WebContainerListener.DIR_DATOS) + sesionController.getUsuario().getNombre() + "/"
								+ producto.getNombreImagen());
				try {
					Files.deleteIfExists(f.toPath());
				} catch (Exception ex) {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(null, new FacesMessage(ex.toString()));
					Logger.getLogger(MisProductos.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		// Actualizar ventas en pagina
		misVentas.postConstruct();
	}

	public void restaurar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Usuario usuario = sesionController.getUsuario();
		for (Producto p : usuario.getProductos()) {
			File f = new File(System.getProperty(WebContainerListener.DIR_DATOS) + usuario.getNombre() + "\\" + p.getNombreImagen());
			try {
				Files.deleteIfExists(f.toPath());
			} catch (Exception ex) {
				fc.addMessage(null, new FacesMessage(ex.toString()));
				Logger.getLogger(MisProductos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		usuario.getProductos().clear();
		List<Producto> productos = new ArrayList<>();
		if (usuario.getNombre().equalsIgnoreCase("andres")) {
			productos.add(restaurarProducto("samsung-galaxy-s4.jpg", "Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me",
					"1.139.000", Categoria.TELEFONOS_INTELIGENTES));
			productos.add(restaurarProducto("iphone-5s.jpg", "Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella", "1.619.900",
					Categoria.TELEFONOS_INTELIGENTES));
			productos.add(restaurarProducto("lg-g2.jpg", "Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram", "1.349.990",
					Categoria.TELEFONOS_INTELIGENTES));
		} else if (usuario.getNombre().equalsIgnoreCase("carlos")) {
			productos.add(restaurarProducto("playstation-4.jpg", "Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus",
					"1.400.000", Categoria.CONSOLAS_VIDEO_JUEGOS));
			productos.add(restaurarProducto("wii-u.jpg", "Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base", "704.990",
					Categoria.CONSOLAS_VIDEO_JUEGOS));
			productos.add(restaurarProducto("xbox-one.jpg", "Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2",
					"1.449.990", Categoria.CONSOLAS_VIDEO_JUEGOS));
		} else if (usuario.getNombre().equalsIgnoreCase("fernando")) {
			productos.add(restaurarProducto("google-nexus-10.jpg", "Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb",
					"919.000", Categoria.TABLETAS));
			productos.add(restaurarProducto("tablet-sony-xperia-z.jpg", "Xperia Tablet Sony Z 32gb", "840.000", Categoria.TABLETAS));
			productos.add(restaurarProducto("toshiba-excite.jpg", "Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1",
					"598.000", Categoria.TABLETAS));
		} else {
			Logger.getLogger(MisProductos.class.getName()).log(Level.INFO,
					"ERROR. Usuario desconocido: \"" + usuario.getNombre() + "\"");
			return;
		}
		usuario.setProductos(new HashSet<>(productos));
		// usuario.setProductos(productos);
		manejadorDeUsuarios.mergeUsuario(usuario);
		selProductos.clear();
		for (Producto producto : usuario.getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
		Logger.getLogger(MisProductos.class.getName()).log(Level.INFO,
				"Restaurados los productos de " + sesionController.getUsuario().getNombre());
	}

	private Producto restaurarProducto(String nombreImagen, String nombre, String precio, Categoria categoria) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String dirOrigenBase =
				ec.getRealPath("resources\\images\\restaurar") + "\\" + sesionController.getUsuario().getNombre() + "\\";
		String dirDestinoBase = System.getProperty(WebContainerListener.DIR_DATOS) + sesionController.getUsuario().getNombre() + "\\";
		Producto p = new Producto();
		p.setNombreImagen(nombreImagen);
		p.setNombre(nombre);
		p.setPrecio(precio);
		p.setFechaDeCreacion(new Date());
		p.setCategoria(categoria);
		File fOrigen = new File(dirOrigenBase + nombreImagen);
		File fDestino = new File(dirDestinoBase + nombreImagen);
		if (!fDestino.exists()) {
			try {
				Files.copy(fOrigen.toPath(), fDestino.toPath());
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
				Logger.getLogger(MisProductos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return p;
	}
}
