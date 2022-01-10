/* global bootstrap */

$(function () {
  const cpfCnpj = localStorage.getItem("cpfCnpj");
  if (cpfCnpj) {
    $("#cpfCnpj").val(atob(cpfCnpj));
  } else {
    location.href = $("#contextApp").val() + "validar";
  }
  $("#password").mask("00000S0S");
  $('[data-bs-toggle="popover"]').popover();



//$("#password").mask("#####S#S");
//    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
//    popoverTriggerList.map(function (popoverTriggerEl) {
//        return new bootstrap.Popover(popoverTriggerEl);
//    });

});