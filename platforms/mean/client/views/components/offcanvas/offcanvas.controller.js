;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'offcanvasCtrl', offcanvasCtrl )
    .factory("canvasServices", ['$http','$location', function($http,$location) {
  	
  	var obj = {};
  	
  	obj.getUserDetais = function(){
	       
  		
	       	
	           return $http({
	               method: 'GET',
	               url: website_url +'/api/users/me',
	               headers: {
	            	   'X-Auth-Token':localStorage.getItem('token'),
	                   'Content-Type': 'application/json',
	                   'Accept': 'text/plain, application/json'
	               }
	           });
	       
	       }
  	
  	return obj
  	
  }])

  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  offcanvasCtrl.$inject = ['$scope','canvasServices','$rootScope'];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function offcanvasCtrl($scope,canvasServices,$rootScope) {
   var init;
   
   
   $rootScope.name = localStorage.getItem("name");
	   
   
 
  
  }
})();
