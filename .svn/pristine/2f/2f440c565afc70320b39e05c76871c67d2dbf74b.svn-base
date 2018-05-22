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
          name         : 'email.inbox.message',
          url          : '/{id}',
          views        : {
            'messageBox@email' :{
              templateUrl  : 'views/scenes/email/components/message/message.html',
              controller   : 'mailViewCtrl',
              controllerAs : 'vm'
            }
          }
        };

    // [2]
    $stateProvider.state( state );
  }


  /**
   * ==========================
   * Config Providers Injection
   * ==========================
   */

  run.$inject = [ '$rootScope', '$http', '$transitions', '$state', 'Store' ];


  /**
   * =========
   * Run Setup
   * =========
   */

  function run( $rootScope, $http, $transitions, $state, Store ) {

    /**
     * Declaring variables
     */

    var states = {
          success: {
            entering: 'email.inbox.message'
          },

          exit: {
            exiting : 'email.inbox.message',
            to      : exitToStateHook
          }
        };

    resetMailView();


    /**
     * States switching logic
     */

    $transitions.onSuccess( states.success, successHandler );
    $transitions.onExit( states.exit, exitHandler );


    /**
     * - Getting all states but `email.inbox.message`.
     * - Necessary for putting default email placeholder
     *   when exiting from opened emails.
     */

    function exitToStateHook( state ) {
      return state.name != 'email.inbox.message'
    }


    /**
     * Retreive the right email according to the current state ID.
     */

    function successHandler( $transitions ) {
      var id,
          request;

      id = $transitions.params().id || false;

      msgWrapperHandler();

      request = {
        method : 'get',
        url    : 'views/scenes/email/data/emails/' + id + '.json'
      };


      id ? $http( request ).then( successAction, errorAction ) : resetMailView();
    }


    /**
     * Update `$rootScope._email` with the returned
     * data from the email JSON file.
     */

    function successAction( response ) {
      var model = {
            'mailView.defaultStatus': false,
            'mailView.content': response.data
          }

      Store.set( model );
    };


    function errorAction( reason ) {
      console.log('in erro')
      resetMailView();
    };


    /**
     * Necessary for putting default email placeholder
     * when exiting from opened emails.
     */

    function exitHandler( $transitions ) {
      resetMailView();
      msgWrapperHandler();
    }

    function resetMailView() {
      var model = {
            'mailView.defaultStatus': true,
            'mailView.content': {
              'message': '<h1 class="u-opacity-30p">Choose Email To Display</h1>'
            }
          }

      Store.set( model );
    }

    function msgWrapperHandler() {
      $(window).load(function () {
        if ( window.matchMedia( '(min-width: 1367px)' ).matches ) {
          $('.c-emailMsgWrapper').removeClass('ng-hide');
        }
      })
    }
  }

})();
