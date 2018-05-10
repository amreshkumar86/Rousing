;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'contentCtrl', contentCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  contentCtrl.$inject = [];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function contentCtrl() {
    var vm = this;
  }

})();
