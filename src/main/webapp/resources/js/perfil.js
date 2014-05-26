var confirmacionParaEliminar = false;

$(document).ready(function () {

    // Seccion mis productos
    $('#btn-confirmacion-eliminar-producto').click(function () {
        confirmacionParaEliminar = true;
        $('#form-mis-productos\\:boton_eliminar').trigger('click');
    });
    inicializarTablaMisProductos();

});

function confirmarEliminarProducto() {
    if (!confirmacionParaEliminar) {
        $('#dialogo-eliminar-producto-confirmacion').modal('show');
    } else {
        confirmacionParaEliminar = false;
        return true;
    }
    return false;
}

function inicializarTablaMisProductos() {
    var filaTabla = $('#form-mis-productos').find('.table').find('tbody').find('tr');
    var dispararClick = true;

    filaTabla.click(function () {
        if (dispararClick) {
            $(this).find('input[type="checkbox"]').trigger('click');
        } else {
            dispararClick = true;
        }
    });

    filaTabla.find('input[type="checkbox"]').click(function () {
        dispararClick = false;
        $(this).parents('tr').toggleClass('info');
    });

    filaTabla.find('input[type="checkbox"]').each(function () {
        if ($(this).prop('checked')) {
            $(this).parents('tr').addClass('info');
        } else {
            $(this).parents('tr').removeClass('info');
        }
    });
}

function misProductosBotonEliminarAjaxEvent(event) {
    /*console.info('event.type = ' + event.type);
     console.info('event.status = ' + event.status);
     console.info('event.source = ' + event.source);*/

    if (event.status === 'begin') {
        $('#form-mis-productos\\:ajax-gif').show();
        $('#dialogo-ajax-procesando').modal('show')
    } else if (event.status === 'success') {
        inicializarTablaMisProductos();
        $('#form-mis-productos\\:ajax-gif').hide();
        $('#dialogo-ajax-procesando').modal('hide')
    }
}

function misProductosSelectAjaxEvent(event) {
    /*console.info('event.type = ' + event.type);
     console.info('event.status = ' + event.status);
     console.info('event.source = ' + event.source);*/

    if (event.status === 'begin') {
        $('#form-mis-productos\\:ajax-gif').show();
    } else if (event.status === 'success') {
        $('#form-mis-productos\\:ajax-gif').hide();
    }
}

function misVentasAjaxEvent(event) {
    /*console.info('event.type = ' + event.type);
     console.info('event.status = ' + event.status);
     console.info('event.source = ' + event.source);*/

    if (event.status === 'begin') {
        $('#form_ventas\\:actualizar-ajax-gif').show();
    } else if (event.status === 'success') {
        $('#form_ventas\\:actualizar-ajax-gif').hide();
    }
}

function misComprasAjaxEvent(event) {
    /*console.info('event.type = ' + event.type);
     console.info('event.status = ' + event.status);
     console.info('event.source = ' + event.source);*/

    if (event.status === 'begin') {
        $('#form_compras\\:actualizar-compras-ajax-gif').show();
    } else if (event.status === 'success') {
        $('#form_compras\\:actualizar-compras-ajax-gif').hide();
    }
}