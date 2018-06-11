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
                    url: website_url + '/api/thirdPartyConnection/',
                    headers: {
                        'X-Auth-Token': localStorage.getItem('token'),
                        'Content-Type': 'application/json',
                    }
                });
            }
            obj.saveNewConnection = function(data) {
              return $http({
                method: 'POST',
                url: website_url + '/api/thirdPartyConnection/',
                data: data,
                headers: {
                  'X-Auth-Token': localStorage.getItem('token'),
                  'Content-Type': 'application/json'
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

    connectionsCtrl.$inject = ['$rootScope', '$state', '$timeout', 'connectionsServices', '$scope', '$location', '$activityIndicator'];

    /**
     * =========================
     * Controller Function Setup
     * =========================
     *
     * [1] : Defining page title.
     *
     */

    function connectionsCtrl($rootScope, $state, $timeout, connectionsServices, $scope, $location, $activityIndicator) {
        $rootScope.title = 'Locations';
        $rootScope.subtitle = 'Manage Locations';
        $scope.allConnections = [];
        console.log($state);
        var init;
        if (localStorage.getItem('token') == null) {
            $location.path('/login');

        } else {

            $scope.listAllConnections = function() {
                $activityIndicator.startAnimating();
                connectionsServices
                    .listAllConnections()
                    .then(res => {
                        $scope.allConnections = res.data;
                        $activityIndicator.stopAnimating();
                        if($state.current.name === 'addConnectionState') {
                          $scope.allConnections.push({
                            authToken: $state.params.code,
                            editable: true,
                            isNew: true
                          });
                        }
                    });
            }

            $scope.addNewConnection = function() {
                let newWindow = open('https://cloud.lifx.com/oauth/authorize?client_id=28fc5a151c53c85053a3992b3eb42643aa4f26d821af7d28eed1b2e6e941c877&scope=remote_control:all&state=akjsdh&response_type=code', 'example', 'width=300,height=300')
                newWindow.focus();
            }

            $scope.saveConnection = function(connection) {
              if(connection.isNew) {
                connectionsServices
                .saveNewConnection(connection)
                .then(res=>{
                  if(res.data.success) {
                    alert('save success');
                    delete connection.isNew;
                    $state.go('manageConnectionState');
                  }
                  //TODO: Handle failure
                })
              }
            }

            $scope.cancel = function(connection) {
              connection.isNew ? $scope.allConnections.pop() &&  $state.go('manageConnectionState') : connection.editable = false;
            }

            $rootScope.$on("CallParentMethod", function() {
               $scope.listAllConnections();
            });



            init = function() {
                $scope.listAllConnections();
            };
            init();
        }
    }

})();