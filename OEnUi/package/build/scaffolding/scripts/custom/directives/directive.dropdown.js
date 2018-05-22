;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'dropdown', dropdown );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function dropdown() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      var elem      = $( element ),
          trigger   = elem.find('.fn-dropdownTrigger'),
          dropdown  = elem.find('.c-menu--dropdown'),
          dropdowns = $('.c-menu--dropdown');

      trigger.bind( 'click', clickHandler );

      function clickHandler( event ) {
        event.preventDefault();
        event.stopPropagation();

        if ( !dropdown.hasClass( 'is-visible' ) ) {
          dropdowns.removeClass( 'is-visible' );
          dropdown.addClass( 'is-visible' );
        } else {
          dropdown.removeClass('is-visible');
        }

        document.addEventListener('click', dropdownDismiss);

        function dropdownDismiss( event ) {
          if ( !dropdown.get(0).contains( event.target ) ){
             document.removeEventListener( 'click', dropdownDismiss );
             dropdown.removeClass('is-visible');
          }
        }
      }
    }
  }

})();
