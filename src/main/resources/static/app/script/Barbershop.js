/* global Swal, numeral */

var Barbershop = Barbershop || {};

Barbershop.DialogoExcluir = (function () {

  function DialogoExcluir() {
    this.exclusaoBtn = $('.js-exclusao-btn');
  }

  DialogoExcluir.prototype.iniciar = function () {
    this.exclusaoBtn.on('click', onExcluirClicado.bind(this));

    if (window.location.search.indexOf('excluido') > -1) {
      Swal.fire('Pronto!', 'Excluído com sucesso!', 'success');
    }
  };

  function onExcluirClicado(evento) {
    event.preventDefault();
    var botaoClicado = $(evento.currentTarget);
    var url = botaoClicado.data('url');
    var objeto = botaoClicado.data('objeto');

    Swal.fire({
      title: 'Tem certeza?',
      text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
      showCancelButton: true,
      confirmButtonColor: '#DD6B55',
      confirmButtonText: 'Sim, exclua agora!'
    }).then((result) => {
      if (result.isConfirmed) {
        onExcluirConfirmado(url);
      }
      $("#divLoading").removeClass("loading");
    });
  }

  function onExcluirConfirmado(url) {
    $.ajax({
      url: url,
      method: 'DELETE',
      success: onExcluidoSucesso.bind(this),
      error: onErroExcluir.bind(this)
    });
  }

  function onExcluidoSucesso() {
    var urlAtual = window.location.href;
    var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
    var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
    window.location = novaUrl;
    $("#divLoading").removeClass("loading");
  }

  function onErroExcluir(e) {
    console.log('ahahahah', e.responseText);
    Swal.fire('Oops!', e.responseText, 'error');
    $("#divLoading").removeClass("loading");
  }

  return DialogoExcluir;

}());


Barbershop.Security = (function () {

  function Security() {
    this.token = $("meta[name='_csrf']").attr("content");
    this.header = $("meta[name='_csrf_header']").attr("content");
  }

  Security.prototype.enable = function () {
    $(document).ajaxSend(function (event, jqxhr, settings) {
      if(this.token) {
        jqxhr.setRequestHeader(this.header, this.token);
      }
    }.bind(this));
  };

  return Security;

}());

Barbershop.MascaraMoneteria = (function () {
  function MascaraMoneteria() {}
  MascaraMoneteria.prototype.enable = function () {
    $(".monetaria").maskMoney({prefix: 'R$ ', allowNegative: false, thousands: ',', decimal: '.', affixesStay: false});
    ;
  };
  return MascaraMoneteria;
}());

Barbershop.Cep = (function () {
  function Cep() {
    this.cep = $(".cep");
  }
  Cep.prototype.enable = function () {
    this.cep.mask("00000-000");
  };
  return Cep;
}());

Barbershop.MascaraCpfCnpj = (function () {

  function MascaraCpfCnpj() {
    this.mascaraCpfCnpj = $(".mascara-cpf-cnpj");
  }

  MascaraCpfCnpj.prototype.enable = function () {
    var CpfCnpjMaskBehavior = function (val) {
      return val.replace(/\D/g, '').length <= 11 ? '000.000.000-009' : '00.000.000/0000-00';
    },
    cpfCnpjpOptions = {
      onKeyPress: function (val, e, field, options) {
        field.mask(CpfCnpjMaskBehavior.apply({}, arguments), options);
      }
    };
    this.mascaraCpfCnpj.mask(CpfCnpjMaskBehavior, cpfCnpjpOptions);
  };

  return MascaraCpfCnpj;
}());

Barbershop.MaskPhoneNumber = (function () {

  function MaskPhoneNumber() {
    this.inputPhoneNumber = $('.mascara-telefone');
  }
  MaskPhoneNumber.prototype.enable = function () {
    var maskBehavior = function (val) {
      return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
    };
    var options = {
      onKeyPress: function (val, e, field, options) {
        field.mask(maskBehavior.apply({}, arguments), options);
      }
    };
    this.inputPhoneNumber.mask(maskBehavior, options);
  };
  return MaskPhoneNumber;

}());

Barbershop.LoadGif = (function () {

  function LoadGif() {}
  LoadGif.prototype.enable = function () {
    $(document).ajaxSend(function (event, jqxhr, settings) {
      $("#divLoading").addClass("loading");
    }.bind(this));
    $(document).ajaxComplete(function (event, jqxhr, settings) {
      $("#divLoading").removeClass("loading");
    }.bind(this));
  };
  return LoadGif;
}());

Barbershop.Mensagem = (function () {
  function Mensagem() {}

  Mensagem.prototype.show = function (icon, mensagem) {

    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 8000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      }
    });

    Toast.fire({
      icon: `${icon}`,
      text: `${mensagem}`
    });

  };

  return Mensagem;

}());


Barbershop.RemoveMask = (function () {

  function RemoveMask() {}

  RemoveMask.prototype.remover = function (value) {
    if (value.length >= 8) {
      value = value.replace(/[^0-9.-]+/g, "");
      if (!value.includes(".")) {
        var val = value.substring(0, value.length - 2) + "." + value.substring(value.length - 2, value.length);
        return Number(val);
      }
    }
    return value;
  };
  return RemoveMask;
}());

$(function () {
  
  $('[data-bs-toggle="popover"]').popover();
  $('[data-bs-toggle="tooltip"]').tooltip();

  var dialogo = new Barbershop.DialogoExcluir();
  dialogo.iniciar();

  var loadGif = new Barbershop.LoadGif();
  loadGif.enable();

  var inputM = new Barbershop.MascaraMoneteria();
  inputM.enable();

  var security = new Barbershop.Security();
  security.enable();

  var mascaraCpfCnpj = new Barbershop.MascaraCpfCnpj();
  mascaraCpfCnpj.enable();

  var mascaraCep = new Barbershop.Cep();
  mascaraCep.enable();

  var maskPhone = new Barbershop.MaskPhoneNumber();
  maskPhone.enable();


});
