;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.profile' )
    .controller( 'profileCtrl', profileCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  profileCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function profileCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Profile';

    var vm = this;
  }

})();
