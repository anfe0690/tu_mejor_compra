
window.onload = function() {

	var check_box_sel01 = document.getElementById("section_mis_productos_form:section_mis_productos_sel_01");
	check_box_sel01.onclick = function (){
		console.log("onclick");
		var button_eliminar = document.getElementById("section_mis_productos_form:boton_eliminar");
		if (button_eliminar.hasAttribute("disabled")){
			button_eliminar.removeAttribute("disabled");
		}else{
			button_eliminar.setAttribute("disabled","disabled");
		}
	};
	
};

