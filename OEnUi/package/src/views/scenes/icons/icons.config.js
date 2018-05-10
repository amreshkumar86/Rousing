;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.icons' )
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
          name         : 'icons',
          url          : '/icons',
          templateUrl  : 'views/scenes/icons/icons.html',
          controller   : 'iconsCtrl',
          controllerAs : 'vm'
        };

    // [2]
    $stateProvider.state( state );
  }

})();
