;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'topbarCtrl', topbarCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  topbarCtrl.$inject = [];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function topbarCtrl() {
    var vm = this;
  }

})();
