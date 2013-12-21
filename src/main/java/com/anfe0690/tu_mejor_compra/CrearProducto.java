/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author Andres
 */
@Named
@ViewScoped
public class CrearProducto implements Serializable {

	private static final Logger logger = Logger.getLogger(CrearProducto.class.getName());

	@Inject
	private SesionController sesionController;
	private Part file;

	@PostConstruct
	public void postConstruct() {
		logger.info("############## postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("############## preDestroy");
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void upload() {
		//logger.info("############## file.getHeader(\"content-disposition\")=" + file.getHeader("content-disposition"));
		// form-data; name="form_crear_producto:file"; filename="Crysis.jpg"
		Pattern p = Pattern.compile(".*filename\\=\"(.*)\"");
		Matcher m = p.matcher(file.getHeader("content-disposition"));
		m.find();
		//logger.info("############## m.find()=" + m.find());
		String fileName = m.group(1);
		//logger.info("############## fileName=" + fileName);

		File f = new File("C:\\var\\tuMejorCompra\\img\\" + sesionController.getUsuario().getNombre() + "\\" + fileName);
		try {
			f.createNewFile();
			OutputStream os = new FileOutputStream(f);
			InputStream is = file.getInputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.close();
			is.close();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
			return;
		}
	}
}
