;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.chat' )
    .controller('conversationCtrl', conversationCtrl);


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  conversationCtrl.$inject = [ '$rootScope', '$scope', 'Store', 'moment' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   */

  function conversationCtrl( $rootScope, $scope, Store, moment ) {
    var vm;

    vm = this;
    vm.conversation = '';
    vm.chat = '';
    vm.send = send

    vm.currentConv = Store.get( 'chat.activeConv' );
    console.log( 'init active ' + vm.currentConv );
    $scope.$parent.direction = 'right';

    $scope.$watch( watchChatExp, watchChatHandler, true );
    $scope.$watch( watchConvExp, watchConvHandler, true );
    $scope.$watch( watchActiveExp, watchActiveHandler );

    function watchChatExp() {
      return Store.get( 'chat' );
    }

    function watchChatHandler( newValue, oldValue, scope ) {
      vm.chat = newValue;
      vm.conversation = newValue.content[ vm.currentConv ].conversation;
    }

    function watchConvExp() {
      return vm.conversation;
    }

    function watchConvHandler( newValue, oldValue, scope ) {
      Store.set( ['chat', 'content', vm.currentConv, 'conversation'],  newValue)
    }

    function watchActiveExp() {
      return Store.get( 'chat.activeConv' );
    }

    function watchActiveHandler( newValue, oldValue, scope ) {
      vm.conversation = (Store.get( 'chat' )).content[ newValue ].conversation;
      vm.currentConv = newValue;
    }

    function send( msg ) {
      if ( !msg ) {
        return
      }
      console.log( 'send active ' + vm.currentConv )
      var lastConv = vm.conversation[vm.conversation.length - 1]
      var date = new Date();
      var target;
      var newMsgWrapper = {
            "me": true,
            "status": null,
            "messages": []
          }

      msg && targetSetter();
      push( msg );

      function push( msg ) {
        var msgObject = {
          text: msg,
          date: date
        }

        target['messages'].push( msgObject );
        vm.userMessage = '';
      }

      function targetSetter() {
        lastConv.me ? target = lastConv : target = newMsgWrapper
      }
    }
  }

})();
