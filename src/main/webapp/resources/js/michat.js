// Keys
var K_TIPO = "K_TIPO";
// Values
var V_TIPO_UNION = "V_TIPO_UNION";
var V_TIPO_MENSAJE = "V_TIPO_MENSAJE";
var V_TIPO_LISTA_NOMBRES = "V_TIPO_LISTA_NOMBRES";

// Variables
var wsocket;

$(document).ready(function () {
        $("#form-nombre").submit(conectarWebSocket);
        $("#form-mensaje").submit(sendMessage);
    }
);

function conectarWebSocket(event) {
    console.log("conectarWebSocket()");

    var nombreInput = $("#nombre");
    if (nombreInput.val().trim()) {
        $('#error-nombre').hide().next().removeClass('has-error has-feedback').find('.glyphicon').hide();

        wsocket = new WebSocket("ws://" + $("#host").val() + ":" + $("#puerto-websockets").val() + "/michat");
        wsocket.onmessage = onMessage;
        wsocket.onopen = function () {
            var nombreInput = $("#nombre");
            var mensajeJson = {
                K_TIPO: V_TIPO_UNION,
                nombre: nombreInput.val().trim()
            };
            wsocket.send(JSON.stringify(mensajeJson));
            nombreInput.prop("disabled", true);
            $("#boton-unirse").prop("disabled", true);

            $("#mensaje").prop("disabled", false).focus();
            $("#boton-mensaje").prop("disabled", false);
        };
    } else {
        console.warn('¡Nombre vacio!');
        $('#error-nombre').show().next().addClass('has-error has-feedback').find('.glyphicon').show();
        nombreInput.val("");
        nombreInput.focus();
    }
    event.preventDefault();
}

function onMessage(evt) {
    console.log("onMessage()");
    console.info("evt.data = '" + evt.data + "'");
    var chat = $("#chat");
    var mensajeJson = JSON.parse(evt.data);
    if (mensajeJson.K_TIPO === V_TIPO_MENSAJE) {
        chat.val(chat.val() + mensajeJson.nombre + " - " + mensajeJson.mensaje + "\n");
        chat.scrollTop(chat.height());
    } else if (mensajeJson.K_TIPO === V_TIPO_LISTA_NOMBRES) {
        var listaNombres = $("#lista-usuarios");
        listaNombres.empty();
        for (var i = 0; i < mensajeJson.listaNombres.length; i++) {
            listaNombres.append("<li>" + mensajeJson.listaNombres[i] + "</li>");
        }
    }

}

function sendMessage(event) {
    console.log("sendMessage()");

    var inputMensaje = $("#mensaje");
    if (inputMensaje.val().trim()) {
        $('#error-mensaje').hide().next().removeClass('has-error has-feedback').find('.glyphicon').hide();

        var mensajeJson = {
            K_TIPO: V_TIPO_MENSAJE,
            nombre: $("#nombre").val(),
            mensaje: inputMensaje.val().trim()
        };
        var jsonString = JSON.stringify(mensajeJson);
        wsocket.send(jsonString);
        console.info("jsonString = '" + jsonString + "'");
        inputMensaje.val("");
        inputMensaje.focus();
    }
    else {
        console.warn('¡Mensaje vacio!');
        $('#error-mensaje').show().next().addClass('has-error has-feedback').find('.glyphicon').show();
        inputMensaje.val("");
        inputMensaje.focus();
    }

    event.preventDefault();
}

$(window).on("beforeunload", function () {
    window.console.log("beforeunload()");
    if (wsocket !== undefined) {
        wsocket.close();
    }
});
