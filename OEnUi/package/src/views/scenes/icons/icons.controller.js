;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.icons' )
    .controller( 'iconsCtrl', iconsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  iconsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function iconsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Icons';

    var vm = this;
  }

})();
