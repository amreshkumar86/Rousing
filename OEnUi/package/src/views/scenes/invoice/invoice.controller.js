;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.invoice' )
    .controller( 'invoiceCtrl', invoiceCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  invoiceCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function invoiceCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Invoice';

    var vm = this;
  }

})();
