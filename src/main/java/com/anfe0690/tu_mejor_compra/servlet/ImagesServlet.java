package com.anfe0690.tu_mejor_compra.servlet;

import com.anfe0690.tu_mejor_compra.WebContainerListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/imgs/*")
public class ImagesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
//	private final int BUFFER_LENGTH = 4096;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(System.getProperty(WebContainerListener.K_DIR_DATOS) + filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String archivo = request.getParameter("a");
//		File file = new File(System.getProperty(WebContainerListener.DIR_DATOS) + archivo);
//		InputStream input = new FileInputStream(file);
//		response.setContentLength((int) file.length());
//		response.setContentType(new MimetypesFileTypeMap().getContentType(file));
//		OutputStream output = response.getOutputStream();
//		byte[] bytes = new byte[BUFFER_LENGTH];
//		int read = 0;
//		while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
//			output.write(bytes, 0, read);
//			output.flush();
//		}
//		input.close();
//		output.close();
//	}
	
	
}
