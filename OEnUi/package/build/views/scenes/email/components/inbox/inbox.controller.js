;(function () {

  'use strict';


  /**
   * Registering controller
   */

  angular
    .module( 'ponut.email' )
    .controller( 'inboxCtrl', inboxCtrl );


  /**
   * Dependacy injection
   */

  inboxCtrl.$inject = [ '$rootScope', '$http' ];


  /**
   * Controller function setup
   * [1] : Declaring variables names.
   * [2] : Assgining values to variables.
   * [3] : Promise success function.
   * [4] : JSON file request initation.
   */

  function inboxCtrl( $rootScope, $http ) {
    $rootScope.title = 'Inbox';
    // [1]
    var vm,
        request;

    // [2]
    vm      = this;

    console.log( $rootScope.$state )

    vm.checkAll = function () {
      if ( vm.selectAll )
        vm.selectAll = true;
      else
        vm.selectAll = false;

      angular.forEach(vm.emails, function ( email ) {
        email.selected = vm.selectAll
      });
    };

    request = {
      method : 'get',
      url    : 'views/scenes/email/data/emails.json'
    };

    // [3]
    function promiseAction( response ) {
      vm.emails = response.data;
    };

    // [4]
    $http( request ).then( promiseAction );
  };

})();
