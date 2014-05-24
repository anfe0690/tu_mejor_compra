$(document).ready(function () {

    var botonComprar = $('#form_cv\\:boton_comprar');
    if (botonComprar.length > 0) {
        botonComprar.click(function (eventObject, continuar) {
            if (continuar === undefined) {
                eventObject.preventDefault();
                $('#comprar-dialog').modal('show');
            }
        });
    }

    $('#comprar-dialog').on('hidden.bs.modal', function (e) {
        botonComprar.trigger('click', {continuar: 'continuar'});
    });

});