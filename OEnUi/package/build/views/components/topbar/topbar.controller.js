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

  topbarCtrl.$inject = ['$scope','$location'];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function topbarCtrl($scope,$location) {
  
	  $scope.logout = function()
	  {
		
		  localStorage.removeItem('customerId');
		  localStorage.removeItem('userId');
		  localStorage.removeItem('token');
		  localStorage.removeItem('name');
		  $location.path('/login');
	  }

  }

})();
