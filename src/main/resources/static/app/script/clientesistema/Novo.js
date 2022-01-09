$(function () {
  
  $("#nomeComercio").focus();
  
  $("#cep").mask("00000-000");
  
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

});