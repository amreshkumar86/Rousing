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
        'ponut.invoice',
        'ponut.maps',
        'ponut.portlets',
        'ponut.profile',
        'ponut.sliders',
        'ponut.tables',
        'ponut.widgets',
        'ponut.todo',
    ];

    var app = angular.module('ponut', dependancies);
    // app.config(function($sceDelegateProvider) {
    //     $sceDelegateProvider.resourceUrlWhitelist([
    //         'https://tryit.w3schools.com/**'
    //     ]);
    // });
}());
