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

    dashboardCtrl.$inject = ['$rootScope', '$timeout', 'dashboardServices', '$scope', '$location', '$activityIndicator'];



    class Location {
        constructor(data) {
            this.groups = data.groups;
            this.id = data.id;
            this.name = data.name;
            this.avgPowerConsumption = '';
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
            this.avgPowerConsumption = currentPowerConsumption.toFixed(2) + ' Watts/hour';
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

    function dashboardCtrl($rootScope, $timeout, dashboardServices, $scope, $location, $activityIndicator) {
        // [1]
        $rootScope.title = 'Dashboard';
        var init;
        if (localStorage.getItem('token') == null) {
            $location.path('/login');

        } else

        {

            $scope.customer = {};
            $scope.customerId = localStorage.getItem('customerId');

            $scope.customer.customer_id = $scope.customerId;


            $scope.getEnergyGraph = function() {
                $activityIndicator.startAnimating();
                $scope.json = angular.toJson($scope.customer);
                dashboardServices.getEnergyGraph($scope.json).then(function mySucces(data) {
                    $scope.response = data.data;
                    $activityIndicator.stopAnimating();
                    var plot;
                    $scope.dataset = [];
                    $scope.xData = [];
                    for (var i = 0; i < $scope.response.length; i++) {
                        $scope.dataset.push([$scope.response[i].timeValue, $scope.response[i].wattage]);
                    }
                    plot = $.plot("#line-chart", [$scope.dataset], {
                        xaxes: [{
                            position: 'bottom',
                            axisLabel: 'Time'
                        }],
                        yaxes: [{
                                position: 'left',
                                axisLabel: 'Wattage in KWH',
                                axisLabelPadding: 5
                            },

                        ],
                        grid: {
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
                        },
                        tooltip: true,
                        tooltipOpts: {
                            content: "%x.1 is %y.4",
                            defaultTheme: false,
                            shifts: {
                                x: 0,
                                y: 20
                            }
                        },
                        series: {
                            color: '#ff4444',
                            shadowSize: null,
                            splines: {
                                show: true,
                                tension: 0.35,
                                lineWidth: 2,
                                fill: 0
                            },
                            tooltip: true,
                            legend: {
                                backgroundOpacity: 0,
                                position: "ne",
                                noColumns: 2
                            },


                        }
                    });
                });
            }



            $scope.getCompleteLightData = function() {
                $scope.group = [];
                $scope.bulb = [];
                dashboardServices.getCompleteLightData($scope.customerId).then(function mySucces(data) {
                    $scope.responseData = data.data;

                    for (var i = 0; i < $scope.responseData.length; i++) {
                        if ($scope.responseData[i].type == "group") {
                            $scope.group.push($scope.responseData[i]);
                        } else if ($scope.responseData[i].type == "device") {
                            $scope.bulb.push($scope.responseData[i]);
                        }

                    }

                    $scope.groupCount = $scope.group.length;
                    $scope.bulbCount = $scope.bulb.length;
                });

            }

            $scope.getEnergyConsumed = function() {
                $scope.cust = {};
                $scope.cust.cust_id = $scope.customerId;
                $scope.data = angular.toJson($scope.cust);
                dashboardServices.getEnergyConsumed($scope.data).then(function mySucces(data) {
                    $scope.countData = data.data;

                    $scope.totalLightCount = $scope.countData[0].activeCount;
                    $scope.totalLightEnergy = $scope.countData[0].totalKWH;
                    $scope.totalGatewayEnergy = $scope.countData[1].totalKWH;
                    $scope.totalEnergy = $scope.countData[0].totalKWH + $scope.countData[1].totalKWH;


                });


            }


            $scope.isObjectEmpty = function(card) {
                return Object.keys(card).length === 0;
            }
            $scope.getColor = function(colorValue, id, mode) {
                var r = Math.floor(colorValue / (256 * 256));
                var g = Math.floor((colorValue - r * 256 * 256) / 256);
                var b = colorValue - r * 256 * 256 - g * 256;

                var rgb = b | (g << 8) | (r << 16);
                $scope.hex = '#' + (0x1000000 + rgb).toString(16).slice(1);
                if (mode == 1) {
                    $('#' + id).css({
                        "background": "#fff"
                    });
                    $('#' + id).css({
                        "border": "1px solid #fff"
                    });
                } else if (colorValue == undefined && mode == undefined) {
                    $('#' + id).css({
                        "background": "#fff"
                    });
                    $('#' + id).css({
                        "border": "1px solid #fff"
                    });

                } else {
                    $('#' + id).css({
                        "background": $scope.hex
                    });
                    $('#' + id).css({
                        "border": "1px solid" + $scope.hex
                    });

                }

            }

            $scope.getColorForGroup = function(colorValue, id, mode) {

                var rc = Math.floor(colorValue / (256 * 256));
                var gc = Math.floor((colorValue - rc * 256 * 256) / 256);
                var bc = colorValue - rc * 256 * 256 - gc * 256;

                var rgbc = bc | (gc << 8) | (rc << 16);
                $scope.hexa = '#' + (0x1000000 + rgbc).toString(16).slice(1);
                if (mode == 1) {
                    $('#' + id).css({
                        "background": "#fff"
                    });
                    $('#' + id).css({
                        "border": "1px solid #fff"
                    });
                } else if (colorValue == undefined && mode == undefined) {
                    $('#' + id).css({
                        "background": "#fff"
                    });
                    $('#' + id).css({
                        "border": "1px solid #fff"
                    });

                } else {
                    $('#' + id).css({
                        "background": $scope.hexa
                    });
                    $('#' + id).css({
                        "border": "1px solid" + $scope.hexa
                    });

                }

            }

            $rootScope.$on("CallParentMethod", function() {
                // $scope.getEnergyGraph();
                // $scope.getCompleteLightData();
                // $scope.getEnergyConsumed();
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
                            groups: groups
                        }));
                    }
                    $scope.allLocations = allLocations;
                });
            }

            init = function() {
                $scope.getAllLights();
            };
            init();

        }
    }

})();