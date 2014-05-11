$(document).ready(function () {
    $("title").html("Producto - Tu mejor compra");

    $("#pagina-tmc\\:form-comprar\\:btn-comprar").click(function (eventObject, continuar) {
        if (continuar === undefined) {
            eventObject.preventDefault();
            $("#popupDialog").popup("open");
        }
    });

    $("#btn-confirmar-compra-no").click(function () {
        $("#popupDialog").popup("close");
    });

    $("#btn-confirmar-compra-si").click(function () {
        $("#popupDialog").popup("close");
        $("#pagina-tmc\\:form-comprar\\:btn-comprar").trigger("click", { continuar: "continuar" });
    });

});