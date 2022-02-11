
/* global Swal, moment */
var submmit = false;

$(function () {

  var cpfCnpj = localStorage.getItem("cpfCnpj");
  var inputCpfCnpj = $("#cpfCnpj");
  var contextApp = $("#contextApp");
  var senhaPrincipal = $("#senha");
  var senhaSecundaria = $("#confirmaSenha");
  mascaraTelefone();
  
  $("input[name=image]").change(function () {
    if (this.files && this.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        $('#image-viewer').attr('src', e.target.result);
      };
      reader.readAsDataURL(this.files[0]);
    }
  });
  
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
        $("#divLoading").addClass("submitting");
      },
      complete: function () {
        $("#divLoading").removeClass("submitting");
      }
    });
  } else {
    location.href = $("#contextApp").val() + "validar";
  }

  $("#btnCriarConta").click(function () {
    const somaAnoNascimento = moment().diff($("#dataNascimento").val(), 'years');
    if(somaAnoNascimento < 16) {
      Swal.fire('Atenção!', 'Data nascimento inválida!', 'warning');
      $("form").submit(function () {
        event.preventDefault();
      });
      return;
    }
    if (submmit === false || senhaPrincipal.val() !== senhaSecundaria.val()) {
      Swal.fire('Atenção!', 'As senhas não conferem!', 'warning');
      $("form").submit(function () {
        event.preventDefault();
      });
      return;
    }
    
    if (!$("form").get(0).checkValidity()) {
      $("#divLoading").removeClass("submitting");
      return;
    } else {
      $("#divLoading").addClass("submitting");
    }
    
    $("form").submit();
    
  });

  $("#confirmaSenha").keyup(function () {
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

  $('#senha').pwstrength(options);

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
    $("#divLoading").removeClass("submitting");
  });
}

function mascaraTelefone() {
  var maskBehavior = function (val) {
    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
  };
  var options = {
    onKeyPress: function (val, e, field, options) {
      field.mask(maskBehavior.apply({}, arguments), options);
    }
  };
  $("#telefone").mask(maskBehavior, options);
}
