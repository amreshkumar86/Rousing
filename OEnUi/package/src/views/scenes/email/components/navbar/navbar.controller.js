;(function () {

  'use strict';


  /**
   * Registering Controller
   */

  angular
    .module( 'ponut.email' )
    .controller( 'emailNavCtrl', emailNavCtrl );


  /**
   * Dependacy Injection
   */

  emailNavCtrl.$inject = [ '$rootScope' ];


  /**
   * Controller function setup
   */

  function emailNavCtrl( $rootScope ) {}

})();
