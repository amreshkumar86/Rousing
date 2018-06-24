(function() {
    'use strict';

    /**
   * ==================
   * Registering Module
   * ==================
   */

    var dependancies = [
        'ui.router',
        'ngAnimate',
        'cgNotify',
        'ponut.core',
        'ponut.blank',
        'ponut.buttons',
        'ponut.calendar',
        'ponut.chat',
        'ponut.email',
        'ponut.charts',
        'ponut.forms',
        'ponut.grid',
        'ponut.helpers',
        'ponut.icons',
        'ponut.dashboard',
        'ponut.connections',
        'ponut.redirectAppUrl',
        'ponut.invoice',
        'ponut.maps',
        'ponut.portlets',
        'ponut.profile',
        'ponut.sliders',
        'ponut.tables',
        'ponut.widgets',
        'ponut.todo',
        'ngMap',
        'tc.chartjs'
    ];

    var app = angular.module('ponut', dependancies);
    // // app.config(function($sceDelegateProvider) {
    // //     $sceDelegateProvider.resourceUrlWhitelist([
    // //         'https://tryit.w3schools.com/**'
    // //     ]);
    // // });
    // app.run(function($rootScope) {
    //     $rootScope.$on("$locationChangeStart", function(event, next, current) { 
    //         // handle route changes
    //         // console.log("Current:"+current+"\nNext:"+next);
    //         $rootScope.title = '';
    //         $rootScope.subtitle = '';
    //     });
    // });
    app.directive('autoFocus', function($timeout) {
        return {
            restrict: 'AC',
            link: function(_scope, _element) {
                $timeout(function(){
                    _element[0].focus();
                }, 0);
            }
        };
    });
}());
