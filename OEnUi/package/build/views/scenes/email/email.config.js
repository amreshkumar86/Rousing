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
    .run( run );


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
          name         : 'email',
          url          : '/email',
          views : {
            ''  : {
              templateUrl  : 'views/scenes/email/email.html',
              controller   : 'emailCtrl',
              controllerAs : 'vm'
            },

            'emailNavbar@email' : {
              templateUrl  : 'views/scenes/email/components/navbar/navbar.html',
              controller   : 'emailNavCtrl',
              controllerAs : 'vm'
            }
          }
        };

    // [2]
    $urlRouterProvider.when( '/email', '/email/inbox/' );
    $urlRouterProvider.when( '/email/', '/email/inbox/' );
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
      $interval(function () {
        $rootScope.breakpoint = window.matchMedia( '(max-width: 1367px)' ).matches;
      }, 10);
    } );

    // $(window).load( loadHandler );

    // function loadHandler() {
    //   handleMailView();
    //   $(window).on( 'resize', resizeHandler );

    //   function handleMailView() {
    //     if ( window.matchMedia('(max-width: 1367px)').matches ) {

    //       if ( !$('.c-emailMsgWrapper').hasClass('is-active') ) {
    //         $('.c-emailMsgWrapper').hide();
    //       }

    //       $('.c-mailbox__briefTitle').on('click', function (event) {
    //         event.preventDefault();

    //         $('.c-mailboxMessages').hide();
    //         $('.c-emailMsgWrapper').show();
    //         $('.c-emailMsgWrapper').addClass('is-active');
    //       });


    //       $('.fn-messagesRevealer').on('click', function (event) {
    //         event.preventDefault();

    //         $('.c-mailboxMessages').show();
    //         $('.c-emailMsgWrapper').hide();
    //         $('.c-emailMsgWrapper').removeClass('is-active');
    //       });
    //     } else {
    //       $('.c-mailboxMessages').show();
    //       $('.c-emailMsgWrapper').show();
    //     }
    //   };

    //   function resizeHandler() {
    //     handleMailView();
    //   }
    // }

  }

})();
