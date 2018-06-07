;
(function() {

    'use strict';


    /**
     * ======================
     * Registering Controller
     * ======================
     */

    var app = angular
        .module('ponut.connections')
        .controller('connectionsCtrl', connectionsCtrl)
        .factory("connectionsServices", ['$http', '$location', function($http, $location) {
            var obj = {};
            obj.listAllConnections = function() {
              return $http({
                     method: 'GET',
                     url: website_url +'/api/thirdPartyConnection/',
                     headers: {                
                       'X-Auth-Token':localStorage.getItem('token'),
                       'Content-Type': 'application/json',                 
                     }
                 });       
            }
            return obj;
        }])

    /**
     * ===================
     * Dependacy Injection
     * ===================
     */

    connectionsCtrl.$inject = ['$rootScope', '$timeout', 'connectionsServices', '$scope', '$location', '$activityIndicator', '$sce'];

    /**
     * =========================
     * Controller Function Setup
     * =========================
     *
     * [1] : Defining page title.
     *
     */

    function connectionsCtrl($rootScope, $timeout, connectionsServices, $scope, $location, $activityIndicator, $sce) {
        // [1]
        // $scope.trustSrc = function(src) {
        //   return $sce.trustAsResourceUrl(src);
        // }
        // $scope.src = "https://cloud.lifx.com/oauth/authorize?client_id=28fc5a151c53c85053a3992b3eb42643aa4f26d821af7d28eed1b2e6e941c877&scope=remote_control:all&state=akjsdh&response_type=code";
        $rootScope.title = 'Connections';
        $scope.allConnections = [];
        var init;
        if (localStorage.getItem('token') == null) {
            $location.path('/login');

        } else {

          $scope.listAllConnections = function() {
            $activityIndicator.startAnimating();
            connectionsServices
            .listAllConnections()
            .then(res=>{
              // $scope.allConnections = res.data;
              $activityIndicator.stopAnimating();
            });
          }

          $scope.addNewConnection = function() {
            // var ifrm = document.createElement("IFRAME"); 
            // ifrm.setAttribute("name", "authIframe");
            // ifrm.setAttribute("target","_parent");
            // ifrm.setAttribute("src", "https://cloud.lifx.com/oauth/authorize?client_id=28fc5a151c53c85053a3992b3eb42643aa4f26d821af7d28eed1b2e6e941c877&scope=remote_control:all&state=akjsdh&response_type=code");
            // ifrm.style.width = 640+"px"; 
            // ifrm.style.height = 480+"px"; 
            // document.body.appendChild(ifrm);
          //   var newWindow = window.open("https://cloud.lifx.com/oauth/authorize?client_id=28fc5a151c53c85053a3992b3eb42643aa4f26d821af7d28eed1b2e6e941c877&scope=remote_control:all&state=akjsdh&response_type=code");
          //   newWindow.onload = function() {
          //   newWindow.close();
          //   alert(newWindow.closed); // true
          // };
          let newWindow = open('https://cloud.lifx.com/oauth/authorize?client_id=28fc5a151c53c85053a3992b3eb42643aa4f26d821af7d28eed1b2e6e941c877&scope=remote_control:all&state=akjsdh&response_type=code', 'example', 'width=300,height=300')
newWindow.focus();

newWindow.onload = function() {
  console.log('Hello',arguments);
};
          }

          $rootScope.$on("CallParentMethod", function() {
              // $scope.getEnergyGraph();
              // $scope.getCompleteLightData();
              // $scope.getEnergyConsumed();
          });



          init = function() {
              $scope.listAllConnections();
              // $scope.getCompleteLightData();
              // $scope.getEnergyConsumed();
          };

          init();
        }
    }

})();