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
    .filter( 'highlight', highlight );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  highlight.$inject = [ '$sce' ];


  /**
   * =====================
   * Filter Function Setup
   * =====================
   *
   * [1] : Regular expression pattern to match `input` against.
   * [2] : What matched pattern will be replaced with.
   * [3] : Updating input values with the highlight injected styles.
   * [4] : Return rendered HTML to be injected in the view.
   *
   */

  function highlight( $sce ) {
    return filter;

    function filter( input, query )  {
      var pattern,
          replacement;

      if ( query ) {
        pattern     = new RegExp( '(' + query + ')', 'gi' );                       // [1]
        replacement = '<span style="background: yellow; color: black;">$1</span>'; // [2]

        input = input.replace( pattern, replacement ); // [3]
      }

      return $sce.trustAsHtml( input ); // [4]
    }
  }

})();