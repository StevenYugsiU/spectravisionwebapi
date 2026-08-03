(function($) {
  'use strict';

  if ($(".js-example-basic-single").length) {
    $(".js-example-basic-single").select2({
      theme: 'bootstrap',
      language: {
        noResults: function() { return "Sin resultados"; }
      }
    });
  }
  if ($(".js-example-basic-multiple").length) {
    $(".js-example-basic-multiple").select2({ theme: 'bootstrap' });
  }
})(jQuery);