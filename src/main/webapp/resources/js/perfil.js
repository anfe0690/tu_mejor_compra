
window.onload = function() {

	// Eliminar productos
	var button_eliminar = document.getElementById("section_mis_productos_form:boton_eliminar");
	button_eliminar.setAttribute("disabled", "disabled");
	button_eliminar.onclick = function(e) {
		var r = confirm("¿Esta seguro de que desea eliminar el/los producto(s) seleccionado(s)?");
		if (!r) {
			e.preventDefault();
		}
	};
	//TODO: Aun cuando hay 0 productos se cuenta 1 producto, el cual no existe
	var numFilas = document.getElementById("section_mis_productos_form:datatable1").getElementsByTagName("tbody")[0].getElementsByTagName("tr").length;

	var sels = new Array();
	for (i = 0; i < numFilas; i++) {
		sels[i] = document.getElementById("section_mis_productos_form:datatable1:" + i + ":seleccion");
		sels[i].onclick = function() {
			if (verificarSiAlgunInputSeleccionado()) {
				button_eliminar.removeAttribute("disabled");
			} else {
				button_eliminar.setAttribute("disabled", "disabled");
			}
		};
	}

	function verificarSiAlgunInputSeleccionado() {
		for (i = 0; i < sels.length; i++) {
			if (sels[i].checked) {
				return true;
			}
		}
		return false;
	}

	// Restaurar productos, compras y ventas
	var button_restaurar = document.getElementById("section_perfil_form:boton_restaurar");
	button_restaurar.onclick = function (e){
		var r = confirm("Si continua se eliminara los productos creados, las compras y ventas agregadas.\n"
				+"¿Esta seguro de que desea restaurar los productos, compras y ventas?");
		if (!r) {
			e.preventDefault();
		}
	};
};



