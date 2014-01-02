/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function() {
	
	/*
	var btnAyuda = document.getElementById("ayuda");
	btnAyuda.onclick = function (){
		alert("Los usuarios y sus respectivas contraseñas son los siguientes:\n\n" +
				"andres = '123'\n" +
				"carlos = '456'\n" +
				"fernando = '789'\n"
				);
	};
	*/
	
	var inputBuscar = document.getElementById("header_form:texto_buscar");
	inputBuscar.setAttribute("required","required");
	
	var form = document.getElementById("header_form");
	form.onsubmit = function (e){
		if(inputBuscar.value.trim().length===0){
			e.preventDefault();
		}
		
	};
	
};