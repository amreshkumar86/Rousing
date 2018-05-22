;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.email' )
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
          name         : 'email.compose',
          url          : '/compose',
          views        : {
            'compose@email' : {
              templateUrl  : 'views/scenes/email/components/compose/compose.html',
              controller   : 'composeCtrl',
              controllerAs : 'vm'
            }
          }
        };

    // [2]
    $stateProvider.state( state );
  }

})();
