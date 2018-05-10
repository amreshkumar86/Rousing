;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

 var app =  angular
    .module( 'ponut.dashboard' )
    .controller( 'dashboardCtrl', dashboardCtrl )
    .factory("dashboardServices", ['$http','$location', function($http,$location) {   	
       var obj = {};
    	obj.getEnergyGraph = function(json){
	       return $http({
	               method: 'POST',
	               url: website_url +'energy/get/energy/graph',
	               data: json,
	               headers: {	               
	            	   'X-Auth-Token':localStorage.getItem('token'),
	                   'Content-Type': 'application/json',                 
	               }
	           });       
	       }
    	obj.getCompleteLightData = function(customerId){
 	       return $http({
 	               method: 'POST',
 	               url: website_url +'device/item/list_with_complete_data/'+customerId,
 	               headers: {	               
 	            	   'X-Auth-Token':localStorage.getItem('token'),
 	                   'Content-Type': 'application/json',                 
 	               }
 	           });       
 	       }
    	obj.getEnergyConsumed = function(json){
  	       return $http({
  	               method: 'POST',
  	               url: website_url +'energy/get/dashboard',
  	               data: json,
  	               headers: {	               
  	            	   'X-Auth-Token':localStorage.getItem('token'),
  	                   'Content-Type': 'application/json',                 
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

  dashboardCtrl.$inject = [ '$rootScope' ,'$timeout','dashboardServices','$scope'];
  
 


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function dashboardCtrl( $rootScope, $timeout,dashboardServices,$scope ) {
    // [1]
    $rootScope.title = 'Dashboard';
    var init;

    
    $scope.customer = {};
    $scope.customerId = localStorage.getItem('customerId');
    
    $scope.customer.customer_id = $scope.customerId;
  
    
    $scope.getEnergyGraph = function()
    {
    	 
    	$scope.json = angular.toJson($scope.customer);  	       
        dashboardServices.getEnergyGraph($scope.json).then(function mySucces(data) {   	
       	$scope.response = data.data;    	
       	
       	var plot;       
           $scope.dataset = [];
           $scope.xData = [];
           for(var i = 0; i< $scope.response.length ; i++ )
           	{        	
           	 $scope.dataset.push([$scope.response[i].timeValue, $scope.response[i].wattage]);         
           	}    
           plot =  $.plot("#line-chart",[$scope.dataset],{
        		   xaxes: [
        	        	      { position: 'bottom', axisLabel: 'Time' }
        	        	    ],
        	        	    yaxes: [
        	        	      { position: 'left', axisLabel: 'Wattage in KWH',
        	        	       axisLabelPadding: 5},
        	        	       
        	        	    ],
           		yaxis:{ show:true,},xaxis:{ tickLength: 0,show:true,tickColor: "colors.palette.grey.muted",
           			 },grid:{
           				borderWidth: {
           			        top: 1,
           			        right: 1,
           			        bottom: 1,
           			        left: 1
           			      },          			 
           				 borderColor: "colors.palette.grey.muted",         			 
                         margin: 0,
                         minBorderMargin: 0,
                         labelMargin: 14,
                         hoverable: true,
                         clickable: true,
                         mouseActiveRadius: 6,
                   },series:{color:'#ff4444',shadowSize: null,splines: {
           	        show: true,
           	        tension: 0.35,
           	        lineWidth: 2,
           	        fill:  0
           	      } ,tooltip: true,
                         points: {
                             show: true,
                             radius: 1
                           },
                           
                         
           	      legend: {
           	          backgroundOpacity: 0,
           	          position: "ne",
           	          noColumns: 2
           	        },
           	        
                   
                     }});
      });   	
    }
    
    $scope.getCompleteLightData = function()
    {
    	$scope.group = [];
    	$scope.bulb = [];
    	dashboardServices.getCompleteLightData($scope.customerId).then(function mySucces(data) {   	
           	$scope.responseData = data.data; 
           	
           	for(var i=0 ; i < $scope.responseData.length; i++)
           		{
           		  if($scope.responseData[i].type == "group") 
           		   {
    		       $scope.group.push($scope.responseData[i]);  		
           		    }
           		  else if($scope.responseData[i].type == "device")
           			  {
           			$scope.bulb.push($scope.responseData[i]);
           			  }
    	
           		}
           	
           	$scope.groupCount = $scope.group.length;
            $scope.bulbCount =  $scope.bulb.length;
    	});
    	
    }
    
    $scope.getEnergyConsumed = function()
    {
    	$scope.cust = {};
    	$scope.cust.cust_id =  $scope.customerId;
    	$scope.data =  angular.toJson($scope.cust);  
    	dashboardServices.getEnergyConsumed($scope.data).then(function mySucces(data) {   	
           	$scope.countData = data.data; 
          
           	$scope.totalLightCount = 	$scope.countData[0].activeCount + $scope.countData[1].activeCount;
           	$scope.totalEnergy = $scope.countData[0].totalKWH + $scope.countData[1].totalKWH;
           	
           	
    	});
    	
    	
    }
   
    
    $scope.isObjectEmpty = function(card){
    	   return Object.keys(card).length === 0;
    	}
    $scope.getColor = function(colorValue,id,mode)
    {
   	var	r = Math.floor(colorValue / (256 * 256));
        var g = Math.floor((colorValue - r * 256 * 256) / 256);
        var	b = colorValue - r * 256 * 256 - g * 256;
        
        var rgb = b | (g << 8) | (r << 16);
        	$scope.hex =   '#' + (0x1000000 + rgb).toString(16).slice(1);
        	if(mode == 1)
        		{
        		$('#'+id).css({"background": "#fff"});
            	$('#'+id).css({"border": "1px solid #fff"});
        		}
        	else if(colorValue == undefined && mode == undefined)
        		{
        		$('#'+id).css({"background": "#fff"});
            	$('#'+id).css({"border": "1px solid #fff"});
        		
        		}else
        		{
        		$('#'+id).css({"background": $scope.hex});
            	$('#'+id).css({"border": "1px solid"+ $scope.hex});
        		
        		}
        	
    }
    
    
    
   
   init = function()
	{
	   $scope.getEnergyGraph();
	   $scope.getCompleteLightData();
	   $scope.getEnergyConsumed();
		$('#line-chart').show();
	  
	};
	init();
    
    
  }

})();
