
$(document).ready(function() {
	
	var btnContrasenas = $("#contrasenas").get(0);
	if (btnContrasenas !== null) {
		btnContrasenas.onclick = function() {
			alert("Los usuarios y sus respectivas contraseñas son los siguientes:\n\n"
					+ "andres = '123'\n"
					+ "carlos = '456'\n"
					+ "fernando = '789'\n");
		};
	}

});