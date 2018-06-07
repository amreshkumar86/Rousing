;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.blank' )
    .factory("blankServices", ['$http','$location', function($http,$location) {
    	
    	var obj = {};
    	
    
    	
    	obj.login = function(json){
	       
    		
	       	
	           return $http({
	               method: 'POST',
	               url: website_url +'/auth/local',
	               data: json,
	               headers: {
	               
	                   'Content-Type': 'application/json',
	                   'Accept': 'text/plain, application/json'
	               }
	           });
	       
	       }
    	obj.getUserDetais = function(){
  	      
	           return $http({
	               method: 'GET',
	               url: website_url +'/api/users/me/',
	               headers: {
	            	    'X-Auth-Token':localStorage.getItem('token'),
	                   'Content-Type': 'application/json',
	                   'Accept': 'text/plain, application/json'
	               }
	           });
	       
	       }
    	return obj;
    	
    }])
    .controller( 'blankCtrl', blankCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  blankCtrl.$inject = [ '$rootScope','$scope','blankServices','$location','toaster','$activityIndicator'];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function blankCtrl( $rootScope,$scope,blankServices,$location,toaster,$activityIndicator) {
    // [1]
    $rootScope.title = 'login';
    $scope.user = {username : 'test@example.com',password:'test'};
    if(localStorage.getItem('token') != undefined)
	{
	$location.path('/dashboard');
	
	}
else
	
	{
    
    $scope.submitForm = function()
    {   	
    	$scope.json = angular.toJson($scope.user); 
    	$activityIndicator.startAnimating();
    	blankServices.login($scope.json).then(function mySucces(data) {
    	    	$scope.response = data.data;
    	    	$activityIndicator.stopAnimating();
    	    		$scope.token = $scope.response.token;
        	    	$scope.userId = $scope.response.user_id;
        	    	$scope.customerId = $scope.response.customer_id;
        	    	localStorage.setItem('token',$scope.token);
        	 		localStorage.setItem('customerId',$scope.customerId);
        	 		
    	    		$location.path('/dashboard');
    	    		$scope.getDetails();
    	    		},
    	    		 function myError(data) {
            	    	$scope.response = data.data.code;
            	    	
            	    	if($scope.response =='500')
            	    		{
            	    		
            	    		   toaster.pop('error', "Error", "Bad Credentials");
            	    		}
            	    	else
            	    		{
            	    		toaster.pop('warning', "Warning", "Something went wrong");
            	    		}
    	    	
    	    		});
    	
    }
    
    $scope.getDetails = function()
    { 
    	blankServices.getUserDetais().then( function mySucces(data){	   
 		   $scope.dataValue = data.data;	  
 		  $rootScope.name  = $scope.dataValue.name;	  
 			localStorage.setItem('name',$scope.name);
 	   });
    }
    
	}

    var vm = this;
    
  }

})();
