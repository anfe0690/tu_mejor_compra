
$(document).ready(function() {

	// Eliminar productos
//	var button_eliminar = $("#form-mis-productos\\:boton_eliminar")[0];
//	button_eliminar.addEventListener("click", function(e) {
//		var r = confirm("¿Esta seguro de que desea eliminar el/los producto(s) seleccionado(s)?");
//		if (!r) {
//			e.preventDefault();
//		}
//	});

	// Habilitar o desabilitar el boton de eliminar productos
//	var numFilas = document.getElementById("form-mis-productos:datatable-mis-productos").getElementsByTagName("tbody")[0].getElementsByTagName("tr").length;
//	var sels = new Array();
//	for (i = 0; i < numFilas; i++) {
//		sels[i] = document.getElementById("form-mis-productos:datatable-mis-productos:" + i + ":seleccion");
//		if (sels[i] !== null) {
//			sels[i].onclick = function() {
//				if (verificarSiAlgunInputSeleccionado()) {
//					button_eliminar.setAttribute("class", button_eliminar.getAttribute("class").replace(" ui-state-disabled", ""));
//					button_eliminar.removeAttribute("disabled");
//				} else {
//					button_eliminar.setAttribute("class", button_eliminar.getAttribute("class") + " ui-state-disabled");
//					button_eliminar.setAttribute("disabled", "disabled");
//				}
//			};
//		}
//	}

//	function verificarSiAlgunInputSeleccionado() {
//		for (i = 0; i < sels.length; i++) {
//			if (sels[i].checked) {
//				return true;
//			}
//		}
//		return false;
//	}

});



