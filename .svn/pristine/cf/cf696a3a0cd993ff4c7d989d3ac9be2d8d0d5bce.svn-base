;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.sliders' )
    .config( config )


  /**
   * ==========================
   * Config Providers Injection
   * ==========================
   */

  config.$provide = [ '$stateProvider', '$urlRouterProvider' ];


  /**
   * ============
   * Config setup
   * ============
   *
   * [1] : State setup options.
   * [2] : State init.
   */

  function config( $stateProvider, $urlRouterProvider ) {
    // [1]
    var state = {
          name         : 'sliders',
          url          : '/sliders',
          templateUrl  : 'views/scenes/sliders/sliders.html',
          controller   : 'slidersCtrl',
          controllerAs : 'vm'
        };

    // [2]
    $stateProvider.state( state );
  }

})();
