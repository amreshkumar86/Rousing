;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'tab', tab );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function tab() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      var elem = $( element )
      elem.on('click', function ( event ) {
        event.preventDefault();

        elem.hasClass('is-active');

        if (!elem.hasClass('is-active')) {
          var
            target          = $( attrs.href ),
            tabEnv          = attrs.tabEnv,
            tabs            = $( '[data-tab-env=' + tabEnv + ']' ),
            activeTab       = tabs.filter('.is-active'),
            activeTabTarget = $( activeTab.attr('href') );

          tabs.removeClass('is-active');
          activeTabTarget.addClass('u-hide');
          elem.addClass('is-active');
          target.removeClass('u-hide');
        }

      });
    }
  }


})();
