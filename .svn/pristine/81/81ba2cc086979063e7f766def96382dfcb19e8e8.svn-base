;(function () {
  'use strict';


  /**
   * Config init
   */

  angular
    .module( 'ponut.email' )
    .config( config );


  /**
   * Config providers injection
   */

  config.$provide = [ '$stateProvider', '$urlRouterProvider' ];


  /**
   * Config setup
   */

  function config( $stateProvider, $urlRouterProvider ) {
    var state = {
          name         : 'email.inbox',
          url          : '/inbox',
          views        : {
            'emailsList@email' : {
              templateUrl  : 'views/scenes/email/components/inbox/inbox.html',
              controller   : 'inboxCtrl',
              controllerAs : 'vm'
            }
          }
        };

    /**
     * State init
     */

    $stateProvider.state( state );
  }
})();
