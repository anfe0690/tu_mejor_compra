
$(document).ready(function() {

	var btnContrasenas = $("#form-sesion\\:contrasenas")[0];
	if (btnContrasenas !== undefined) {
		$("#form-sesion\\:contrasenas")[0].onclick = null;
		btnContrasenas.onclick = function() {
			alert("Los usuarios y sus respectivas contraseñas son los siguientes:\n\n"
					+ "andres = '123'\n"
					+ "carlos = '456'\n"
					+ "fernando = '789'\n");
		};
	}

});