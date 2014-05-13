var confirmacionParaEliminar = false;

$(document).ready(function () {
    $("title").html("Perfil - Tu mejor compra");

    // Dialogo confirmacion
    $("#btn-confirmar-eliminar-producto-no").click(function () {
        $("#popupDialog").popup("close");
    });
    $("#btn-confirmar-eliminar-producto-si").click(function () {
        $("#popupDialog").popup("close");
        confirmacionParaEliminar = true;
        $("#pagina-tmc\\:form-mis-productos\\:btn-eliminar-producto").trigger("click");
    });

    // Limpiar el cache en firefox
    $(".producto").find("input[type='checkbox']").each(function () {
        if ($(this).prop("checked") === true) {
            $(this).trigger("click");
        }
    });

    efectoSeleccionCheckBox();
});

function efectoSeleccionCheckBox() {
    var checkBoxes = $(".producto").find("input[type='checkbox']");

    // Efecto de seleccion
    checkBoxes.change(function () {
        if ($(this).prop("checked") === true) {
            $(this).parents(".producto").css("background-color", "#87BBE6");
        } else {
            $(this).parents(".producto").css("background-color", "white");
        }
    });
}

function checkBoxAjax(event) {
    /*console.info("event.type = " + event.type);
     console.info("event.source = " + event.source);
     console.info("event.status = " + event.status);*/
    if (event.status === "success") {
        $("#pagina-tmc\\:form-mis-productos\\:btn-eliminar-producto").button("refresh");
    }
}

function buttonSubmitAjax(event) {
    if (event.status === "success") {
        $("#pagina-tmc\\:form-mis-productos\\:btn-eliminar-producto").button();
        $(".producto").find("input[type='checkbox']").checkboxradio();
        efectoSeleccionCheckBox();

        $("#pagina-tmc\\:form-mis-ventas\\:btn-actualizar-ventas").button();
        $(".venta").find("select").selectmenu();
    }

    if (event.status === "begin") {
        $.mobile.loading( "show", { text: "procesando...", textVisible: true } );
    }
    if (event.status === "success") {
        $.mobile.loading( "hide" );
    }
}

function confirmarEliminar() {
    if (!confirmacionParaEliminar) {
        $("#popupDialog").popup("open");
    } else {
        confirmacionParaEliminar = false;
        return true;
    }
    return false;
}

function botonActualizarVentasAjax(event) {
    if (event.status === "success") {
        $("#pagina-tmc\\:form-mis-ventas\\:btn-actualizar-ventas").button();
        $(".venta").find("select").selectmenu();
    }

    if (event.status === "begin") {
        $.mobile.loading( "show", { text: "procesando...", textVisible: true } );
    }
    if (event.status === "success") {
        $.mobile.loading( "hide" );
    }
}

function botonActualizarComprassAjax(event) {
    if (event.status === "success") {
        $("#pagina-tmc\\:form-mis-compras\\:btn-actualizar-compras").button();
        $(".compra").find("select").selectmenu();
    }

    if (event.status === "begin") {
        $.mobile.loading( "show", { text: "procesando...", textVisible: true } );
    }
    if (event.status === "success") {
        $.mobile.loading( "hide" );
    }
}