;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'vectorMap', vectorMap );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function vectorMap() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    /**
     * [1] : Variables declarations.
     * [2] : Default variable containing common options between
     *       all `vectorMap` plugin instances.
     * [3] : User defined options in the view.
     * [4] : Final combined options contaning defaults and user options.
     * [5] : Initating `vectorMap` plugin.
     */

    function link( scope, element, attrs ) {
      // [1]
      var defaults,
          userOpts,
          options;

      // [2]
      defaults = {
          backgroundColor   : null,
          borderColor       : null,
          enableZoom        : true,
          showTooltip       : true,
          hoverOpacity      : 0.7,
          color             : colors.white,
          selectedColor     : colors.palette.variant.primary,
          scaleColors       : [colors.blueGrey['50'], colors.blueGrey['700']],
          normalizeFunction : 'polynomial',
          values            : sample_data,
      };

      // [3]
      userOpts = scope.$eval( attrs.vectorMap) || {};

      // [4]
      options = Object.assign( {}, defaults, userOpts );

      // [5]
      $( element ).vectorMap( options );
    }
  }


})();
