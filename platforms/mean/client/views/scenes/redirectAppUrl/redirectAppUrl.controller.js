;
(function() {

    'use strict';


    /**
     * ======================
     * Registering Controller
     * ======================
     */

    var app = angular
        .module('ponut.redirectAppUrl')
        .controller('redirectAppUrlCtrl', redirectAppUrlCtrl)
        .factory("redirectAppUrlServices", ['$http', '$location', function($http, $location) {
            var obj = {};
            // obj.listAllConnections = function() {
            //   return $http({
            //          method: 'GET',
            //          url: website_url +'/api/thirdPartyConnection/',
            //          headers: {                
            //            'X-Auth-Token':localStorage.getItem('token'),
            //            'Content-Type': 'application/json',                 
            //          }
            //      });       
            // }
            return obj;
        }])

    /**
     * ===================
     * Dependacy Injection
     * ===================
     */

    redirectAppUrlCtrl.$inject = ['$rootScope', '$timeout', 'redirectAppUrlServices', '$scope', '$location', '$activityIndicator', '$stateParams'];

    /**
     * =========================
     * Controller Function Setup
     * =========================
     *
     * [1] : Defining page title.
     *
     */

    function redirectAppUrlCtrl($rootScope, $timeout, redirectAppUrlServices, $scope, $location, $activityIndicator, $stateParams) {
        console.log($stateParams);
        if(window.opener){
          window.opener.location.href = window.location.href.replace('redirectAppUrl','manageConnections/add');
          window.opener.focus();
          window.close();
        }
        debugger;
    }

})();