;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'sparkline', sparkline );


  /**
   * ====================
   * Dependency Injection
   * ====================
   */

   sparkline.$inject = [ '$http', '$timeout' ];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function sparkline( $http, $timeout ) {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      var resizeChart, DrawSparkline, options,
          request, userOpts;


      userOpts = scope.$eval( attrs.sparkline) || {};

      request = {
        method : 'get',
        url    : attrs.sparklineDataUrl
      };

      $http( request ).then( promiseFullfill, promiseError );

      function promiseFullfill( response ) {
        DrawSparkline = function () {
          sparkline  = response.data;

          if ( !Array.isArray( sparkline ) ) {
            options = attrs.sparklineResponsive ? Object.assign({ width: $( element ).width() }, sparkline.options, userOpts )  : Object.assign(sparkline.options, userOpts);
            $( element ).sparkline( sparkline.data, options )
          }

          else {
            sparkline.forEach( iterator );

            function iterator( item ) {
              console.log( item )
              options = attrs.sparklineResponsive ? Object.assign({ width: $( element ).width() }, item.options, userOpts )  : Object.assign(item.options, userOpts);
              $( element ).sparkline( item.data, options )
            }
          }
        };

        DrawSparkline();

        $(window).on( 'resize', resizeHandler );

        function resizeHandler() {
          $timeout.cancel( resizeChart );
          resizeChart = $timeout(function() {
            DrawSparkline();
          }, 300);
        };
      };

      function promiseError( reason ) {
        // Error handling
      };
    }
  }

})();
