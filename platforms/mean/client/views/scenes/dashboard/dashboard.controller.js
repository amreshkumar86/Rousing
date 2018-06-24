;
(function() {

    'use strict';


    /**
     * ======================
     * Registering Controller
     * ======================
     */
    var updateDuration = 1000*60*5; //5 minutes
    var app = angular
        .module('ponut.dashboard')
        .controller('dashboardCtrl', dashboardCtrl)
        .factory("dashboardServices", ['$http', '$location', function($http, $location) {
            var obj = {};
            obj.getAllLights = function() {
                return $http({
                    method: 'GET',
                    url: 'api/devices/',
                    headers: {
                        'X-Auth-Token': localStorage.getItem('token'),
                        'Content-Type': 'application/json',
                    }
                });
            }
            obj.getCompleteLightData = function(customerId) {
                return $http({
                    method: 'POST',
                    url: website_url + 'device/item/list_with_complete_data/' + customerId,
                    headers: {
                        'X-Auth-Token': localStorage.getItem('token'),
                        'Content-Type': 'application/json',
                    }
                });
            }
            obj.getEnergyConsumed = function(json) {
                return $http({
                    method: 'POST',
                    url: website_url + 'energy/get/dashboard',
                    data: json,
                    headers: {
                        'X-Auth-Token': localStorage.getItem('token'),
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

    dashboardCtrl.$inject = ['$rootScope', '$timeout', 'dashboardServices', '$scope', '$location', '$activityIndicator','NgMap'];



    class Location {
        constructor(data, updateCallback) {
            this.groups = data.groups;
            this.id = data.id;
            this.name = data.name;
            this.avgPowerConsumption = '';
            this.address = data.address || '';
            this.location = data.location;
            this.updateCallback = updateCallback;
            for (var i = this.groups.length - 1; i >= 0; i--) {
                var eachGroup = this.groups[i];
                eachGroup.location = this;
                for (var j = eachGroup.lights.length - 1; j >= 0; j--) {
                    eachGroup.lights[j].group = eachGroup;
                }
            }
            var me = this;
            setTimeout(()=>{
                me.updateAvgPowerConsumption();
            },100);
            setInterval(()=>{
                me.updateAvgPowerConsumption();
            },updateDuration);
        }

        updateAvgPowerConsumption() {
            var currentPowerConsumption = 0;
            for (var i = this.groups.length - 1; i >= 0; i--) {
                currentPowerConsumption += this.groups[i].getCurrentPowerConsumption();
            }
            this.avgPowerConsumption = currentPowerConsumption.toFixed(2);
            if(this.updateCallback) {
              this.updateCallback();
            }
        }

        getEnergyGraphColor() {
          //Max is 9 watt per light
          //Divide in 4 quarters
          //0-25: Green
          //26-50: Yellow
          //51-75: Orange
          //76-100: Red
          var energyConsumptionPercent = (this.avgPowerConsumption/(this.getTotalLights()*9)*100);
          var color = 'black';
          if(energyConsumptionPercent <= 25) {
            color = 'green';
          }
          else if(energyConsumptionPercent <= 50) {
            color = 'yellow';
          }
          else if(energyConsumptionPercent <= 75) {
            color = 'orange';
          }
          else if(energyConsumptionPercent <= 100) {
            color = 'red';
          }

          return color;
        }

        getLightsStats() {
          return this.getConnectedLights() + '/' + this.getTotalLights();
        }

        getTotalLights() {
          var totalLights = 0;
          for (var i = this.groups.length - 1; i >= 0; i--) {
            totalLights += this.groups[i].getTotalLights();
          }
          return totalLights;
        }

        getConnectedLights() {
          var totalLights = 0;
          for (var i = this.groups.length - 1; i >= 0; i--) {
            totalLights += this.groups[i].getConnectedLights();
          }
          return totalLights; 
        }

        getGatewayStats() {
          return this.getTotalLights() > 0 ? 'Connected' : 'Disconnected';
        }
    }

    class Group {
        constructor(data) {
            this.lights = data.lights ? data.lights : [];
            this.name = data.name;
            this.id = data.id;
            this.connectedLights = '';
            var me = this;
            setTimeout(()=>{
                this.connectedLights = me.getConnectedLights();
            },100);
            this.avgPowerConsumption = '';
            var me = this;
            setTimeout(()=>{
                me.avgPowerConsumption = me.getCurrentPowerConsumption().toFixed(2) + ' Watts/hour';
            },100);
            setInterval(()=>{
                me.updateState();
            },updateDuration);
        }

        getTotalLights() {
            return this.lights.length;
        }
        getConnectedLights() {
            var totalLightCount = this.lights.length;
            var connectedLights = this.lights.filter((light)=>{return light.data.connected}).length;
            return connectedLights;
        }

        getCurrentPowerConsumption() {
            var currentPowerConsumption = 0;
            for (var i = this.lights.length - 1; i >= 0; i--) {
                currentPowerConsumption += this.lights[i].getCurrentPowerConsumption();
            }
            return currentPowerConsumption;
        }

        updateState() {
            var me = this;
            $.ajax({
            url: 'api/devices/group/'+this.id,
                headers: {
                    'X-Auth-Token': localStorage.getItem('token'),
                    'Content-Type': 'application/json',
                    'LocationId' : this.location.id
                },
                method: 'GET',
                dataType: 'json',
                success: function(data){
                  if(data.success && data.success == true) {
                    for (var i = data.lights.length - 1; i >= 0; i--) {
                        var eachLight = data.lights[i];
                        var indexOfLight = me.lights.findIndex(x => x.id==eachLight.id);
                        if(indexOfLight >= 0) {
                            me.lights[indexOfLight].data = eachLight;
                        }
                    }
                    me.avgPowerConsumption = me.getCurrentPowerConsumption().toFixed(2) + ' Watts/hour';
                  }
                }
            });
        }
    }

    class Light {
        constructor(data) {
            this.data = data;
            this.id = data.id;
            var me = this;
            // setInterval(()=>{
            //     me.updateState();
            // },updateDuration);
        }

        getCurrentPowerConsumption() {
            //Brightness  1 -> 9 Watt
            //Brightness 0 -> 0.1 Watt
            if(!this.data.connected) return 0;
            var avgPowerConsumption = 0;
            if(this.data.brightness == 0 || this.data.power == 'off') {
                avgPowerConsumption = 0.1;
            }
            else {
                avgPowerConsumption = (9 / 100)*(this.data.brightness*100);
            }
            return avgPowerConsumption;
        }

        updateState() {
            var me = this;
            $.ajax({
            url: 'api/devices/light/'+this.data.id,
                headers: {
                    'X-Auth-Token': localStorage.getItem('token'),
                    'Content-Type': 'application/json',
                    'LocationId' : this.group.location.id
                },
                method: 'GET',
                dataType: 'json',
                success: function(data){
                  if(data.success && data.success == true) {
                    me.data = data.light;
                  }
                }
            });
        }
    }

    /**
     * =========================
     * Controller Function Setup
     * =========================
     *
     * [1] : Defining page title.
     *
     */

    function dashboardCtrl($rootScope, $timeout, dashboardServices, $scope, $location, $activityIndicator, NgMap) {
        $rootScope.title = 'Dashboard';
        var init;
        if (localStorage.getItem('token') == null) {
            $location.path('/login');

        } else {

            $rootScope.$on("CallParentMethod", function() {
                $scope.getAllLights();
            });

            $scope.getAllLights = function() {
                $activityIndicator.startAnimating();
                dashboardServices
                .getAllLights()
                .then(res=>{
                    $activityIndicator.stopAnimating();
                    var allLocations = [];
                    for(var i = 0; i < res.data.length; i++){
                        var eachLocation = res.data[i];
                        var groups = [];
                        for (var j = 0; j < eachLocation.lights.length; j++) {
                            var eachLight = eachLocation.lights[j];
                            var lightGroup = eachLight.group;
                            var indexOfGroup = groups.findIndex(x => x.id==lightGroup.id);
                            if(indexOfGroup < 0) {
                                lightGroup = new Group(lightGroup);
                                groups.push(lightGroup);
                            }
                            else {
                                lightGroup = groups[indexOfGroup];
                            }
                            lightGroup.lights.push(new Light(eachLight));
                        }
                        groups.sort(function(a,b) {return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);} ); 
                        allLocations.push(new Location({
                            name: eachLocation.name,
                            id: eachLocation.id,
                            address: eachLocation.address,
                            location : eachLocation.location,
                            groups: groups
                        },$scope.refreshGraph));
                    }
                    $scope.allLocations = allLocations;
                    $scope.updateMap();
                    $scope.refreshGraph($scope.allLocations);
                });
            }

            init = function() {
                $scope.getAllLights();
            };
            init();

            $scope.updateMap = function() {
              NgMap.getMap().then(function(map) {
                var bounds = new google.maps.LatLngBounds();
                for (var i = $scope.allLocations.length - 1; i >= 0; i--) {
                  var loc = $scope.allLocations[i].location;
                  var marker = new google.maps.Marker({
                    position: loc,
                    map: map,
                    title: $scope.allLocations[i].name
                  });
                  bounds.extend(marker.getPosition());
                }
                setTimeout(()=>{
                  map.fitBounds(bounds);
                },100);
              });
            }

            $scope.refreshGraph = function() {
              $scope.graphOptions = {
                animation: false,
                legend: {
                    display: false,
                },
                title: {
                    display: true,
                    text: 'Current Power Consumption in Watts/Hr',
                    fontColor: 'white',
                },
                scales: {
                  yAxes: [{
                      ticks: {
                          fontColor: 'white',
                      },
                      gridLines : {
                        // color: 'white'
                      }
                  }],
                  xAxes: [{
                      ticks: {
                          fontColor: "white",
                          fontSize: 14,
                          stepSize: 1,
                          beginAtZero: true
                      }
                  }],
              }
              }
              $scope.graphData = {};
              $scope.graphData.labels = $scope.allLocations.map(eachLocation=>{return eachLocation.name});
              $scope.graphData.datasets = [{
                // label : ''
              }];
              var plotData = $scope.graphData.datasets[0];
              plotData.data = $scope.allLocations.map(eachLocation=>{return eachLocation.avgPowerConsumption;});
              plotData.backgroundColor = $scope.allLocations.map(eachLocation=>{return eachLocation.getEnergyGraphColor()});
            }
        }
    }

})();