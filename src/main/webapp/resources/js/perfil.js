
window.addEventListener("load", function() {

	// Eliminar productos
	var button_eliminar = document.getElementById("form-mis-productos:boton_eliminar");
	button_eliminar.onclick = function(e) {
		var r = confirm("¿Esta seguro de que desea eliminar el/los producto(s) seleccionado(s)?");
		if (!r) {
			e.preventDefault();
		}
	};

	// Habilitar o desabilitar el boton de eliminar productos
	var numFilas = document.getElementById("form-mis-productos:datatable-mis-productos").getElementsByTagName("tbody")[0].getElementsByTagName("tr").length;
	var sels = new Array();
	for (i = 0; i < numFilas; i++) {
		sels[i] = document.getElementById("form-mis-productos:datatable-mis-productos:" + i + ":seleccion");
		if (sels[i] !== null) {
			sels[i].onclick = function() {
				if (verificarSiAlgunInputSeleccionado()) {
					button_eliminar.removeAttribute("disabled");
				} else {
					button_eliminar.setAttribute("disabled", "disabled");
				}
			};
		}
	}

	function verificarSiAlgunInputSeleccionado() {
		for (i = 0; i < sels.length; i++) {
			if (sels[i].checked) {
				return true;
			}
		}
		return false;
	}

});



