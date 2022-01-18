$(function () {

  $("#nomeComercio").focus();

  $("#cep").mask("00000-000");
  
  $("body").after("<div id='divLoading'></div>");
  
  $(document).ajaxSend(function (event, jqxhr, settings) {
    $("#divLoading").addClass("submitting");
  });
  $(document).ajaxComplete(function (event, jqxhr, settings) {
    $("#divLoading").removeClass("submitting");
  });
  
  $('a').click(function (e) {
    if (!$(this).is("#navbarDropdown")) {
      $("#divLoading").addClass("loading");
    }
  });
    
  $("button[type='submit']").click(function (e) {
    if (!$("form").get(0).checkValidity()) {
      $("#divLoading").removeClass("submitting");
    } else {
      $("#divLoading").addClass("submitting");
    }
  });
  

  $("#cep").focusout(function () {
    if ($("#cep").val()) {
      $.get("https://viacep.com.br/ws/" + $("#cep").val() + "/json/", function (data) {
        if (data) {//new StoreDrink.Mensagem().show("warning","Telefone inválido!");
          $("#bairro").val(data.bairro);
          $("#estado").val(data.uf);
          $("#cidade").val(data.localidade);
          $("#logradouro").val(data.logradouro);
        }
      });
    }
  });

  var maskBehavior = function (val) {
    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
  };
  var options = {
    onKeyPress: function (val, e, field, options) {
      field.mask(maskBehavior.apply({}, arguments), options);
    }
  };

  var cpfCnpjMaskBehavior = function (val) {
    return val.replace(/\D/g, '').length <= 11 ? '000.000.000-009' : '00.000.000/0000-00';
  },
  cpfCnpjpOptions = {
    onKeyPress: function (val, e, field, options) {
      field.mask(cpfCnpjMaskBehavior.apply({}, arguments), options);
    }
  };

  $("#cpfCnpj").mask(cpfCnpjMaskBehavior, cpfCnpjpOptions);

  $("#telefone").mask(maskBehavior, options);

  $("#pastaPrincipal").select2({
    theme: "bootstrap-5",
    allowClear: true,
    multiple: false,
    closeOnSelect: true,
    debug: true,
    placeholder: "Selecione a pasta principal",
    templateSelection: function (pasta) {
      if (pasta && pasta.id !== "") {
        return $("<span class='badge bg-dark' style='font-size:13px;'>" + pasta.text + "</span>");
      }
      return $("<span class=''>" + pasta.text + "</span>");
    }
  });

  $("#negocio").select2({
    theme: "bootstrap-5",
    allowClear: true,
    multiple: false,
    closeOnSelect: true,
    debug: true,
    placeholder: "Selecione o negócio",
    templateSelection: function (pasta) {
      if (pasta && pasta.id !== "") {
        return $("<span class='badge bg-dark' style='font-size:13px;'>" + pasta.text + "</span>");
      }
      return $("<span class=''>" + pasta.text + "</span>");
    }
  });


  $("#imagemPerfilCadastroUsuario").select2({
    theme: "bootstrap-5",
    allowClear: true,
    multiple: false,
    closeOnSelect: true,
    debug: true,
    placeholder: "Imagens perfil usuário",
    templateResult: formatSelect,
    templateSelection: formatSelect
  });

  $("#imagePerfilCatalogo").select2({
    theme: "bootstrap-5",
    allowClear: true,
    multiple: false,
    closeOnSelect: true,
    debug: true,
    placeholder: "Imagens perfil catálogo",
    templateResult: formatSelect,
    templateSelection: formatSelect
  });

  function formatSelect(component) {
    if (component && component.id !== undefined && component.id !== "") {
      const context = component.element.dataset.context;
      var html = $("<span><img width='22px' height='22px' style='border-radius: 50%;' src='" + context + "vendor/imagens/" + component.id + "'/>" + "</span>"
              + "<span style='margin-left:10px;' class='badge bg-secondary'>" + component.text + "</span>");
      return html;
    }
    return $("<span class=''>" + component.text + "</span>");
  };

});