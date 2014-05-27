$(document).ready(function () {

    var inputFile = document.getElementById("form_crear_producto:file");
    inputFile.addEventListener("change", function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                document.getElementById("form_crear_producto:imagen").setAttribute("src", e.target.result);
            };

            reader.readAsDataURL(this.files[0]);
        } else {
            document.getElementById("form_crear_producto:imagen").setAttribute("src",
                "/javax.faces.resource/sel-imagen.png.xhtml?ln=images");
        }
    });

});

function archivoAjaxEvent(event) {
//	console.log("event.status = " + event.status);

    if (event.status === 'begin') {
        $('#archivo-ajax-gif').show();
    } else if (event.status === 'success') {
        $('#archivo-ajax-gif').hide();
    }
}

function nombreAjaxEvent(event){
//	console.log("event.status = " + event.status);

    if (event.status === 'begin') {
        $('#form_crear_producto\\:nombre-ajax-gif').show();
    } else if (event.status === 'success') {
        $('#form_crear_producto\\:nombre-ajax-gif').hide();
    }
}

function precioAjaxEvent(event){
//	console.log("event.status = " + event.status);

    if (event.status === 'begin') {
        $('#form_crear_producto\\:precio-ajax-gif').show();
    } else if (event.status === 'success') {
        $('#form_crear_producto\\:precio-ajax-gif').hide();
    }
}

function crearAjaxEvent(event){
//	console.log("event.status = " + event.status);

    if (event.status === 'begin') {
        $('#form_crear_producto\\:crear-ajax-gif').show();
    } else if (event.status === 'success') {
        $('#form_crear_producto\\:crear-ajax-gif').hide();
    }
}
