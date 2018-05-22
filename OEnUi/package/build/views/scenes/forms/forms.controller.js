;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.forms' )
    .controller( 'formsCtrl', formsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  formsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function formsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Forms';

    var vm = this;
  }

})();
