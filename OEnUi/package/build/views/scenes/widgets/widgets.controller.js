;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.widgets' )
    .controller( 'widgetsCtrl', widgetsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  widgetsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function widgetsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Widgets';

    var vm = this;
  }

})();
