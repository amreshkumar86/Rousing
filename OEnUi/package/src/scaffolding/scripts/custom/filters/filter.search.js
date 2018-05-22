;(function () {

  'use strict';


  /**
   * ==================
   * Registering Filter
   * ==================
   *
   * Search filter in scenarioes where searching
   * in specific keys of array of objects is required.
   *
   */

  angular
    .module( 'ponut.core' )
    .filter( 'search', search );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  search.$inject = [ '$filter' ];


  /**
   * =====================
   * Filter Function Setup
   * =====================
   */

  function search( $filter ) {
    return filter;

    function filter( input, keys, query )  {
      var output = [];


      /**
       * Check for query definition.
       */

      if ( !query ) {
        return input;
      }


      /**
       * - Loop through all objects of input array.
       * - Execute `objectHandler` function on each object.
       */

      angular.forEach( input, objectHandler );

      function objectHandler( object ) {
        var pushed = false;


        /**
         * - Loop through all provided keys.
         * - Execute `keysHandler` function on each key's value.
         */

        angular.forEach( keys, keysHandler );


        /**
         * [1] : Get current object value of current related key.
         * [2] : Test (if/if not) the previous value contains user query.
         * [3] : Push current object to output array only if `pushed` = false.
         * [4] : Set `pushed` = true to prevent unnecessary code execution.
         */

        function keysHandler( key ) {
          if ( !pushed ) {
            var value,
                matched;

            value   = object[ key ];                                      // [1]
            matched = $filter( 'filter' )( [ value ], query ).length > 0; // [2]

            if ( value && matched ) {
              output.push( object );  // [3]
              pushed = true;          // [4]
            }
          }
        }
      }


      /**
       * Return output array to update the view.
       */

      return output;
    }
  }

})();