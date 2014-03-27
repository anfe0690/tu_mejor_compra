
window.addEventListener("load", function() {
	
	if (document.getElementById("form_cv:v_nombre_contacto") !== null) {
		var v_nombreContacto = document.getElementById("form_cv:v_nombre_contacto").value;
		var v_correo = document.getElementById("form_cv:v_correo").value;
		var v_telefonos = document.getElementById("form_cv:v_telefonos").value;
		var v_banco = document.getElementById("form_cv:v_banco").value;
		var v_numeroCuenta = document.getElementById("form_cv:v_numero_cuenta").value;

		var c_nombreContacto = document.getElementById("form_cv:c_nombre_contacto").value;
		var c_correo = document.getElementById("form_cv:c_correo").value;
		var c_telefonos = document.getElementById("form_cv:c_telefonos").value;

		var botonComprar = document.getElementById("form_cv:boton_comprar");
		if (botonComprar !== null) {
			botonComprar.onclick = function() {
				var texto = "Los siguientes datos del vendedor seran enviados al correo del comprador:\n" +
						"Contacto: " + v_nombreContacto + "\n" +
						"Correo: " + v_correo + "\n" +
						"Telefonos: " + v_telefonos + "\n" +
						"Banco: " + v_banco + "\n" +
						"Numero de cuenta: " + v_numeroCuenta + "\n\n" +
						"Como tambien los siguientes datos del comprador al vendedor:\n" +
						"Contancto: " + c_nombreContacto + "\n" +
						"Correo: " + c_correo + "\n" +
						"Telefonos: " + c_telefonos + "\n\n" +
						"El estado de la compra se vera en el perfil del vendedor y comprador";
				alert(texto);
			};
		}
	}

});