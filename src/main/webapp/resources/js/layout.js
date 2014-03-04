
function doLayout() {

	var btnContrasenas = document.getElementById("contrasenas");
	if (btnContrasenas !== null) {
		btnContrasenas.onclick = function() {
			alert("Los usuarios y sus respectivas contraseñas son los siguientes:\n\n"
					+ "andres = '123'\n"
					+ "carlos = '456'\n"
					+ "fernando = '789'\n");
		};
	}
}
;

window.onload = function() {
	doLayout();
};