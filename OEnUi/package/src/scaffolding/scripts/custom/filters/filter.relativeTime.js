;(function () {

  'use strict';


  /**
   * ==================
   * Registering Filter
   * ==================
   *
   * Filter used to highlight mateched string
   * in the provided collection with the provided styles.
   *
   */

  angular
    .module( 'ponut.core' )
    .filter( 'relativeTime', relativeTime );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  relativeTime.$inject = [ 'moment' ];


  /**
   * =====================
   * Filter Function Setup
   * =====================
   *
   * [1] : Regular expression pattern to match `input` against.
   * [2] : What matched pattern will be replaced with.
   * [3] : Updating input values with the relativeTime injected styles.
   * [4] : Return rendered HTML to be injected in the view.
   *
   */

  function relativeTime( moment ) {
    return filter;

    function filter( input )  {
      input = moment( input ).fromNow() // [3]

      return input; // [4]
    }
  }

})();
