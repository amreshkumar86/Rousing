;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'headerCtrl', headerCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  headerCtrl.$inject = [];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function headerCtrl() {
    var vm = this;
  }

})();
