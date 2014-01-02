/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function() {
	
	var inputFile = document.getElementById("form_crear_producto:file");
	inputFile.setAttribute("required","required");
	inputFile.setAttribute("accept","image/*");
	inputFile.onchange = function (){
		if(this.files && this.files[0]){
			var reader = new FileReader();
			
			reader.onload = function (e){
				document.getElementById("form_crear_producto:imagen").setAttribute("src",e.target.result);
			};
			
			reader.readAsDataURL(this.files[0]);
		}
	};
	
	var inputNombre = document.getElementById("form_crear_producto:nombre_producto");
	inputNombre.setAttribute("required","required");
	
	var inputPrecio = document.getElementById("form_crear_producto:precio_producto");
	inputPrecio.setAttribute("required","required");
	
};
