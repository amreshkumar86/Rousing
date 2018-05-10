;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.blank' )
    .factory('BlankService', BlankService)


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  BlankService.$inject = [ 'localStorageService', '$rootScope', '$filter','$http' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

   function BlankService( localStorageService, $rootScope, $filter,$http ) {
	   
	   function Blank ($scope) {
	       this.$scope       = $scope;
	   

	       Blank.login = function(json){
	       	
	       	
	           return $http({
	               method: 'POST',
	               url: website_url +'auth/login',
	               data: json,
	               headers: {
	               
	                   'Content-Type': 'application/json',
	                   'Accept': 'text/plain, application/json'
	               }
	           });
	       
	       }
	     return Blank;
	   }
   }

})();
