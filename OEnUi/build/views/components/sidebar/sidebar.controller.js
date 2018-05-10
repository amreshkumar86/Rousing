;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'sidebarCtrl', sidebarCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  sidebarCtrl.$inject = [];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function sidebarCtrl() {
    var vm = this;
  }

})();
