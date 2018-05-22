;(function () {

  'use strict';

  window.loading_screen = window.pleaseWait({
    template:
      "<div class='pg-loading-inner'>" +
        "<div class='pg-loading-center-outer'>" +
          "<div class='pg-loading-center-middle'>" +
            "<div class='pg-loading-html'>" +
            "</div>" +
          "</div>" +
        "</div>" +
      "</div>",
    loadingHtml: '<progress class="c-progress c-progress--flipper" max="100" value="100"></progress>'
  });

})();
