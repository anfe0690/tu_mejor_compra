/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function() {
	
	var inputFile = document.getElementById("form_crear_producto:file");
	inputFile.setAttribute("required","required");
	
	var inputNombre = document.getElementById("form_crear_producto:nombre_producto");
	inputNombre.setAttribute("required","required");
	
	var inputPrecio = document.getElementById("form_crear_producto:precio_producto");
	inputPrecio.setAttribute("required","required");
	
};

