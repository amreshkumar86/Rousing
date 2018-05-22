;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.helpers' )
    .controller( 'helpersCtrl', helpersCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  helpersCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function helpersCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Helpers';

    var vm = this;
  }

})();
