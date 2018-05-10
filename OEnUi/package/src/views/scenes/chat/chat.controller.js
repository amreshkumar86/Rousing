;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.chat' )
    .controller('chatCtrl', chatCtrl);


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  chatCtrl.$inject = [ '$rootScope', '$http', 'Store', 'moment' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Update page title for chat state.
   * [2] : Loading chat data from JSON file.
   *
   */

  function chatCtrl( $rootScope, $http, Store, moment ) {
    $rootScope.title = 'Chat'; // [1]

    var request,
        vm;

    vm = this;

    request = {
      method : 'get',
      url    : 'views/scenes/chat/data/chat.json'
    };

    Store.set({
      'chat': {
        'activeConv': 0,
        'content': [{
          "avatar": null,
          "name": null,
          "job": null,
          "conversation": []
        }],
      }
    });

    $http( request ).then( successHandler, errorHandler ); // [2]

    function successHandler( response ) {
      Store.set( 'chat.content', response.data );
    }

    function errorHandler( reason ) {
      // Error handling
    }
  }

})();
