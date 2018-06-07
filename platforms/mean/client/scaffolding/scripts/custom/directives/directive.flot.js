;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'flot', flot );


  /**
   * ====================
   * Dependency Injection
   * ====================
   */

   flot.$inject = [ '$http', '$timeout' ];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function flot( $http, $timeout ) {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      // [1]
      var options, flot,  data,
          request, chart, realtime;

      realtime = {
        data   : [],
        points : 300
      }

      request = {
        method : 'get',
        url    : attrs.flotDataUrl
      };

     
      function promiseFullfill( response ) {
        flot  = response.data;
        data  = attrs.flotRealtime ? [ getRandomData() ] : flot.data;

        function isColor( input ) {
          var pattern = /colors/;
          return pattern.test( input );
        }

        function evalProps( obj ) {
          Object.keys( obj ).forEach(function (key) {
              if ( Object.prototype.toString.call( obj[key] ) === '[object Object]' ) {
                return evalProps( obj[key] );
              }


              if ( isColor( obj[key] ) ) {
                if ( Array.isArray( obj[key] ) ) {
                  obj[key].forEach(function (item, index) {
                    obj[key][index] = eval( item );
                  });

                  return
                }

                obj[key] = eval( obj[key] );
                console.log( obj[key] );
              }
          });
        }

        evalProps( flot );

        chart = $.plot( $( element ), data, flot.options );

        attrs.flotResize && $(window).on( 'resize', resizeHandler );

        function getRandomData() {
          if ( realtime.data.length > 0 ) {
            realtime.data = realtime.data.slice(1);
          }

          while ( realtime.data.length < realtime.points ) {
            var prev = realtime.data.length > 0 ? realtime.data[realtime.data.length - 1] : 50,
                y = prev + Math.random() * 10 - 5;

            if (y < 0) {
              y = 0;
            } else if ( y > 100 ) {
              y = 100;
            }

            realtime.data.push(y);
          }

          var res = [];
          for (var i = 0; i < realtime.data.length; ++i) {
            res.push([i, realtime.data[i]])
          }

          return res;
        }

        function update() {
          chart.setData([getRandomData()]);
          chart.draw();
          $timeout(update, 30);
        }

        attrs.flotRealtime && update();

        function resizeHandler() {
          chart.resize();
          chart.setupGrid();
          chart.draw();
        };
      };

      function promiseError( reason ) {
        // Error handling
      };
    }
  }

})();
