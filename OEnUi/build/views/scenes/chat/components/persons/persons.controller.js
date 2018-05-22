;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.chat' )
    .controller('personsCtrl', personsCtrl);


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  personsCtrl.$inject = [ '$rootScope', '$scope', 'Store' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   */

  function personsCtrl( $rootScope, $scope, Store ) {
    var vm = this;


    vm.switchConversation = switchConversation;
    vm.activeConv = Store.get( 'chat.activeConv' );
    $scope.$parent.activity = { item: vm.activeConv }

    vm.chat = '';

    $scope.$watch( watchChatExp, watchChatHandler, true );
    $scope.$watch( watchActiveExp, watchActiveHandler );

    function watchChatExp() {
      return Store.get( 'chat' );
    }

    function watchChatHandler( newValue, oldValue, scope ) {
      vm.chat = newValue.content;
    }

    function watchActiveExp() {
      return vm.activeConv;
    }

    function watchActiveHandler( newValue, oldValue, scope ) {
      Store.set( 'chat.activeConv', vm.activeConv )
    }

    function switchConversation( conv ) {
      Store.set( 'chat.activeConv', conv );
    };
  }

})();
