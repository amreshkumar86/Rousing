;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.redirectAppUrl' )
    .config( config )


  /**
   * ==========================
   * Config Providers Injection
   * ==========================
   */

  config.$provide = [ '$stateProvider', '$urlRouterProvider'];


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
          name         : 'redirectAppUrl',
          url          : '/redirectAppUrl/:code/:scope/:state',
          templateUrl  : 'views/scenes/redirectAppUrl/redirectAppUrl.html',
          controller   : 'redirectAppUrlCtrl',
          controllerAs : 'vm'
        };

    // [2]
    $stateProvider.state( state );
  }

})();
