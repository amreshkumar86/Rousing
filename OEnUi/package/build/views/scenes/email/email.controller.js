;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.email' )
    .controller( 'emailCtrl', emailCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  emailCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function emailCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Email';

    var vm = this;
  }

})();
