;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'tuck', tuck );


  /**
   * ====================
   * Dependancy Injection
   * ====================
   */

   tuck.$inject = [ '$window', '$interval' ];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function tuck( $window, $interval ) {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      var el, range,
          opt, isInRange,
          start, end;

      range     = {
        from : '0px',
        to   : '5000px'
      };

      el        = $( element ),
      opt       = scope.$eval( attrs.tuck ),
      start     = opt.from || range.from,
      end       = opt.to || range.to,
      isInRange = false;


      function setHeight( el ) {
        var rect         = el.get(0).getBoundingClientRect(),
            height       = el.offsetHeight,
            windowHeight = window.innerHeight

        el.get(0).style.height = ((windowHeight - rect.top) + 'px' );
      }

      function resetHeight( el ) {
        el.get(0).style.height = null;
      }

      function checkRange() {
        isInRange = window.matchMedia('(min-width: ' + start + ') and (max-width: ' + end + ')').matches ? true : false;
      }

      function init() {
        checkRange();
        isInRange ? setHeight( el ) : resetHeight( el );
      }

      $interval( init, 100 );
    }
  }


})();
