/* global Swal */

$(function () {
  mascaraCpfCnpj();
  $(".btnValidarClienteSistema").click(function () {
    if ($("#cpfCnpj").val()) {
      if ($("#cpfCnpj").val().length === 14 || $("#cpfCnpj").val().length === 18) {
        $.ajax({
          url: $("#contextApp").val() + "validar/cliente",
          type: "get",
          data: {
            cpfCnpj: $("#cpfCnpj").val().replace(/[^\d]+/g, '')
          },
          headers: {
            "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
            "Content-Type": "application/json"
          },
          success: function (response) {
            window.localStorage.setItem("cpfCnpj", btoa($("#cpfCnpj").val()));
            window.location.href = $("#contextApp").val() + response;
          },
          error: function (xhr) {
            if (xhr.responseJSON) {
              $("#cpfCnpj").focus();
              Swal.fire('Atenção!',`${xhr.responseJSON.message}`,'warning');
            } else if(xhr.responseText) {
              $("#cpfCnpj").focus();
              Swal.fire('Atenção!',`${xhr.responseText}`,'warning');
            }
            window.console.log(xhr);
          },
          beforeSend: function () {
            $("#divLoading").addClass("loading");
          },
          complete: function () {
            $("#divLoading").removeClass("loading");
          }
        });
      } else {
        $("#cpfCnpj").focus();
        Swal.fire('Atenção!','CPF/CNPJ Inválido!', 'warning');
      }
    } else {
      $("#cpfCnpj").focus();
      Swal.fire( 'Atenção!','CPF/CNPJ é obrigatória!','warning');
    }
  });
});

function mascaraCpfCnpj() {
  var CpfCnpjMaskBehavior = function (val) {
    return val.replace(/\D/g, '').length <= 11 ? '000.000.000-009' : '00.000.000/0000-00';
  },
  cpfCnpjpOptions = {
    onKeyPress: function (val, e, field, options) {
      field.mask(CpfCnpjMaskBehavior.apply({}, arguments), options);
    }
  };
  $("#cpfCnpj").mask(CpfCnpjMaskBehavior, cpfCnpjpOptions);
}