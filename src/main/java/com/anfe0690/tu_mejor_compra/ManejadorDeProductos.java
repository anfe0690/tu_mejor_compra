/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class ManejadorDeProductos implements Serializable {

	private static final Logger logger = Logger.getLogger(ManejadorDeProductos.class.getName());

	@Inject
	private SesionController sesionController;
	private final List<SelProducto> selProductos = new ArrayList<>();

	@PostConstruct
	public void postConstruct() {
		logger.info("############## postConstruct");
		for (Producto producto : sesionController.getUsuario().getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############## preDestroy");
	}

	public List<SelProducto> getSelProductos() {
		return selProductos;
	}

	public void eliminarProductos() {
		FacesContext fc = FacesContext.getCurrentInstance();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Usuario usuario = sesionController.getUsuario();
		et.begin();
		Iterator<SelProducto> it = selProductos.iterator();
		while (it.hasNext()) {
			SelProducto sp = it.next();
			if (sp.isSeleccionado()) {
				Producto producto = sp.getProducto();
				usuario.getProductos().remove(producto);
				it.remove();
				File f = new File("C:\\var\\tuMejorCompra\\img\\" + sesionController.getUsuario().getNombre() + "\\" + producto.getNombreImagen());
				try {
					Files.deleteIfExists(f.toPath());
				} catch (Exception ex) {
					fc.addMessage(null, new FacesMessage(ex.toString()));
					logger.log(Level.SEVERE, null, ex);
				}
			}
		}
		em.merge(usuario);
		et.commit();

		em.close();
		emf.close();
	}

	public void restaurar() {
		FacesContext fc = FacesContext.getCurrentInstance();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tuMejorCompra");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Usuario usuario = sesionController.getUsuario();
		et.begin();
		for (Producto p : usuario.getProductos()) {
			File f = new File("C:\\var\\tuMejorCompra\\img\\" + usuario.getNombre() + "\\" + p.getNombreImagen());
			try {
				Files.deleteIfExists(f.toPath());
			} catch (Exception ex) {
				fc.addMessage(null, new FacesMessage(ex.toString()));
				logger.log(Level.SEVERE, null, ex);
			}
		}
		usuario.getProductos().clear();
		List<Producto> productos = new ArrayList<>();
		if (usuario.getNombre().equalsIgnoreCase("andres")) {
			productos.add(restaurarProducto("samsung-galaxy-s4.jpg", "Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me", "1.139.000"));
			productos.add(restaurarProducto("iphone-5s.jpg", "Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella", "1.619.900"));
			productos.add(restaurarProducto("lg-g2.jpg", "Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram", "1.349.990"));
		} else if (usuario.getNombre().equalsIgnoreCase("carlos")) {
			productos.add(restaurarProducto("playstation-4.jpg", "Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus", "1.400.000"));
			productos.add(restaurarProducto("wii-u.jpg", "Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base", "704.990"));
			productos.add(restaurarProducto("xbox-one.jpg", "Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2", "1.449.990"));
		} else if (usuario.getNombre().equalsIgnoreCase("fernando")) {
			productos.add(restaurarProducto("google-nexus-10.jpg", "Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb", "919.000"));
			productos.add(restaurarProducto("tablet-sony-xperia-z.jpg", "Xperia Tablet Sony Z 32gb", "840.000"));
			productos.add(restaurarProducto("toshiba-excite.jpg", "Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1", "598.000"));
		} else {
			logger.log(Level.SEVERE, "ERROR. Usuario desconocido: \"{0}\"", usuario.getNombre());
			em.close();
			emf.close();
			return;
		}

		usuario.setProductos(productos);
		em.merge(usuario);
		et.commit();

		em.close();
		emf.close();

		selProductos.clear();
		for (Producto producto : usuario.getProductos()) {
			selProductos.add(new SelProducto(producto));
		}
		logger.info("Restaurados los productos de " + sesionController.getUsuario().getNombre());
	}

	private Producto restaurarProducto(String nombreImagen, String nombre, String precio) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String dirOrigenBase = ec.getRealPath("resources\\images\\restaurar") + "\\" + sesionController.getUsuario().getNombre() + "\\";
		String dirDestinoBase = "C:\\var\\tuMejorCompra\\img\\" + sesionController.getUsuario().getNombre() + "\\";
		Producto p = new Producto();
		p.setNombreImagen(nombreImagen);
		p.setNombre(nombre);
		p.setPrecio(precio);
		File fOrigen = new File(dirOrigenBase + nombreImagen);
		File fDestino = new File(dirDestinoBase + nombreImagen);
		if (!fDestino.exists()) {
			try {
				Files.copy(fOrigen.toPath(), fDestino.toPath());
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
				logger.log(Level.SEVERE, null, ex);
			}
		}
		return p;
	}

}
