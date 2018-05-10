;(function () {

  'use strict';


  /**
   * ======================
   * Registering controller
   * ======================
   */

  angular
    .module( 'ponut.email' )
    .controller( 'mailViewCtrl', mailViewCtrl );


  /**
   * ===================
   * Dependacy injection
   * ===================
   */

  mailViewCtrl.$inject = [ '$rootScope', '$scope', '$sce', 'Store' ];


  /**
   * =========================
   * Controller function setup
   * =========================
   *
   * [1] : .
   * [2] : .
   * [3] : .
   * [4] : .
   * [5] : .
   */

  function mailViewCtrl( $rootScope, $scope, $sce, Store ) {
    // [1]
    var vm = this;
    vm.mailView = Store.get('mailView');

    // [2]
    $scope.$watch( watchMailViewExp, watchMailViewHandler, true );

    // [3]
    function watchMailViewExp() {
      return vm.mailView;
    }

    // [4]
    function watchMailViewHandler( newValue, oldValue ) {
      vm.message = $sce.trustAsHtml( newValue.content.message );
    }
  }

})();
