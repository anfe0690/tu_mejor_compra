
$(document).ready(function() {

    if( $('#form-iniciar-sesion\\:cont-nombre-usuario').hasClass('has-error')
        || $('#form-iniciar-sesion\\:cont-contrasena-usuario').hasClass('has-error')){
        $('#myModal').modal('show');
    }

    $("#form-iniciar-sesion-submit-helper").click(function(){
        $("#form-iniciar-sesion\\:btn-iniciar-sesion").trigger("click");
    });

});