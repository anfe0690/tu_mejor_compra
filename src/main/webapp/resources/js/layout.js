/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function() {
	
	var inputBuscar = document.getElementById("header_form:texto_buscar");
	inputBuscar.setAttribute("required","required");
	
	var form = document.getElementById("header_form");
	form.onsubmit = function (e){
		if(inputBuscar.value.trim().length===0){
			e.preventDefault();
		}
		
	};
	
};