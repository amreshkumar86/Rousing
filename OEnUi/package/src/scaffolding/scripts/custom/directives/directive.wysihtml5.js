;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'wysihtml5Editor', wysihtml5Editor );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function wysihtml5Editor() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      var opt = {
        toolbar     : attrs.wysihtml5Toolbar,
        stylesheets : "app.css",
        parserRules : wysihtml5ParserRules
      }

      new wysihtml5.Editor( element[0], opt );
    }
  }

})();
