;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.blank' )
    .config( config )
    .config(function ($qProvider) {
	    $qProvider.errorOnUnhandledRejections(false);
	});

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
          name         : 'login',
          url          : '/',
          templateUrl  : 'views/scenes/blank/login.html',
          controller   : 'blankCtrl',
          controllerAs : 'vm'
        };

    // [2]
    $stateProvider.state( state );
  }

})();
