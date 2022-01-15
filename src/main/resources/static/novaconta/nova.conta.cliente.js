
/* global Swal */
var submmit = false;

$(function () {

  var cpfCnpj = localStorage.getItem("cpfCnpj");
  var inputCpfCnpj = $("#cpfCnpj");
  var contextApp = $("#contextApp");
  var senhaPrincipal = $("#password");
  var senhaSecundaria = $("#confirmPassword");
  
  if (cpfCnpj) {
    inputCpfCnpj.val(atob(cpfCnpj));
    $.ajax({
      url: contextApp.val() + "validar/cliente",
      type: "get",
      data: {
        cpfCnpj: inputCpfCnpj.val().replace(/[^\d]+/g, '')
      },
      headers: {
        "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
        "Content-Type": "application/json"
      },
      success: function (response) {
        //Swal.fire('Atenção!', `${response}`, 'warning');
        //window.location.href = $("#contextApp").val() + response;
      },
      error: function (xhr) {
        localStorage.removeItem('cpfCnpj');
        if (xhr.responseJSON) {
          alert();
        } else if (xhr.responseText) {
          alert();
        }
      },
      beforeSend: function () {
        $("#divLoading").addClass("loading");
      },
      complete: function () {
        $("#divLoading").removeClass("loading");
      }
    });
  } else {
    location.href = $("#contextApp").val() + "validar";
  }

  $("#btnCriarConta").click(function () {
    if (submmit === false || senhaPrincipal.val() !== senhaSecundaria.val()) {
      Swal.fire('Atenção!', 'As senhas não conferem!', 'warning');
      $("form").submit(function () {
        event.preventDefault();
      });
      return;
    }
    $("form").submit();
  });
  
  $("#confirmPassword").keyup(function () {
    if(senhaPrincipal.val() === senhaSecundaria.val()) {
      $("#btnCriarConta").attr("disabled", false);
      $("#btnCriarConta").removeClass();
      $("#btnCriarConta").addClass("btn btn-sm btn-outline-success");
    }
  });
  
  
  options = {
    common: {
      minChar: 11,
      maxChar: 11,
      debug: false,
      onKeyUp: function (evt, data) {
        if (data.verdictLevel > 4) {
          submmit = true;
        } else {
          $("#btnCriarConta").attr("disabled", true);
          $("#btnCriarConta").removeClass();
          $("#btnCriarConta").addClass("btn btn-sm btn-outline-danger");
          submmit = false;
        }
      }
    },

    rules: {
      activated: {
        wordTwoCharacterClasses: true,
        wordMaxLength: true,
        wordInvalidChar: true
      },
      specialCharClass: "[!,@,#,$,%,&,*,?]"
    },

    ui: {
      container: "#pwd-container",
      viewports: {
        progress: ".pwstrength_viewport_progress"
      },
      showErrors: true,
      showVerdictsInsideProgressBar: true,
      showPopover: true
    }
  };

  $('#password').pwstrength(options);

  $('[data-bs-toggle="popover"]').popover();
});

function alert() {
  Swal.fire({
    title: 'Atenção!',
    text: 'CPF CNPJ Inválido!',
    showCancelButton: false,
    confirmButtonColor: '#DD6B55',
    confirmButtonText: 'OK'
  }).then((result) => {
    if (result.isConfirmed) {
      window.location.href = $("#contextApp").val() + 'validar';
    } else {
      window.location.href = $("#contextApp").val() + 'validar';
    }
    $("#divLoading").removeClass("loading");
  });
}