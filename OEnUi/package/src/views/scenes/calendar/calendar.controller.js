;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.calendar' )
    .controller( 'calendarCtrl', calendarCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  calendarCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function calendarCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Calendar';

    var vm = this;
  }

})();
