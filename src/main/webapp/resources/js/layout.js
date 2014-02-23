window.onload = function() {

	var btnAyuda = document.getElementById("contrasenas");
	btnAyuda.onclick = function() {
		alert("Los usuarios y sus respectivas contraseñas son los siguientes:\n\n"
				+ "andres = '123'\n"
				+ "carlos = '456'\n"
				+ "fernando = '789'\n");
	};

	var btnRestaurarBD = document.getElementById("restaurar:boton");
	btnRestaurarBD.onclick = function(e) {
		var r = confirm("Al restaurar la base de datos se perderan toads las modificaciones realizadas.\n¿Desea continuar?");
		if (r == false) {
			e.preventDefault();
		}
	};

	var inputBuscar = document.getElementById("header_form:texto_buscar");
	inputBuscar.setAttribute("required", "required");

	var form = document.getElementById("header_form");
	form.onsubmit = function(e) {
		if (inputBuscar.value.trim().length === 0) {
			e.preventDefault();
		}

	};

};