
$(document).ready(function() {

	var inputFile = document.getElementById("form_crear_producto:file");
	inputFile.addEventListener("change", function() {
		if (this.files && this.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				document.getElementById("form_crear_producto:imagen").setAttribute("src", e.target.result);
			};

			reader.readAsDataURL(this.files[0]);
		} else {
			document.getElementById("form_crear_producto:imagen").setAttribute("src",
					"/javax.faces.resource/sel-imagen.png.xhtml?ln=images");
		}
	});

});

function miAjaxEvent(event) {
	console.log("event.source.id = " + event.source.id);
	console.log("event.status = " + event.status);

	if (event.source.id === "form_crear_producto:file") {
		if (event.status === "begin") {
			$("#form_crear_producto\\:loading-file-gif").css("display", "inline");
		}
		else if (event.status === "complete") {
			$("#form_crear_producto\\:loading-file-gif").css("display", "none");
		}
	}
	else if (event.source.id === "form_crear_producto:nombre-producto") {
		if (event.status === "begin") {
			$("#form_crear_producto\\:loading-nombre-gif").css("display", "inline");
		}
		else if (event.status === "complete") {
			$("#form_crear_producto\\:loading-nombre-gif").css("display", "none");
		}
	}
	else if (event.source.id === "form_crear_producto:precio-producto") {
		if (event.status === "begin") {
			$("#form_crear_producto\\:loading-precio-gif").css("display", "inline");
		}
		else if (event.status === "complete") {
			$("#form_crear_producto\\:loading-precio-gif").css("display", "none");
		}
	}

	else if (event.source.id === "form_crear_producto:boton-crear-producto") {
		if (event.status === "begin") {
			$("#form_crear_producto\\:loading-submit-gif").css("display", "inline");
		}
		else if (event.status === "complete") {
			$("#form_crear_producto\\:loading-submit-gif").css("display", "none");
		}
	}

}
;

