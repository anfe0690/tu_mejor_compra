$(document).ready(function () {
    $("title").html("Crear producto - Tu mejor compra");

    $("#pagina-tmc\\:form-crear-producto\\:archivo-imagen").change(function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                document.getElementById("pagina-tmc:imagen").setAttribute("src", e.target.result);
            };

            reader.readAsDataURL(this.files[0]);
        } else {
            document.getElementById("pagina-tmc:imagen").setAttribute("src",
                "/javax.faces.resource/images/sel-imagen.png.xhtml");
        }
    });

});

function formAjax(event) {
    /*console.info("event.type = " + event.type);
    console.info("event.source = " + event.source);
    console.info("event.status = " + event.status);*/
    if (event.status === "begin") {
        $.mobile.loading( "show", { text: "procesando...", textVisible: true } );
    }
    if (event.status === "success") {
        $.mobile.loading( "hide" );
    }
}