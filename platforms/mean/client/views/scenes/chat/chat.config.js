;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.chat' )
    .config( config )
    .run( run )


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
          name         : 'chat',
          url          : '/chat',
          views        : {
            '': {
              templateUrl  : 'views/scenes/chat/chat.html',
              controller   : 'chatCtrl',
              controllerAs : 'vm'
            },
            'conversation@chat' : {
              templateUrl  : 'views/scenes/chat/components/conversation/conversation.html',
              controller   : 'conversationCtrl',
              controllerAs : 'vm'
            },
            'persons@chat'      : {
              templateUrl  : 'views/scenes/chat/components/persons/persons.html',
              controller   : 'personsCtrl',
              controllerAs : 'vm'
            },
          }
        };

    // [2]
    $stateProvider.state( state );
  }


  /**
   * ========================
   * Run Dependency Injection
   * ========================
   */

  run.$inject = [ '$interval', '$rootScope' ];

  /**
   * =========
   * Run setup
   * =========
   */

  function run( $interval, $rootScope ) {

    $(window).load( function () {
      if (window.matchMedia( '(max-width: 1024px)' ).matches) {
        $('#chatSidebarToggle').on('click', function () {
          $('#chatSidebar').toggleClass('is-open')
        });
        $('#chatSidebarClear').on('click', function () {
          $('#chatSidebar').toggleClass('is-open')
        });
      };
    } );

  }

})();
