(function() {
    'use strict';


    /**
   * ===========
   * Config Init
   * ===========
   */

    angular
        .module('ponut')
        .config(config)
        .run(run);


    /**
   * ==========================
   * Config Providers Injection
   * ==========================
   */

    config.$provide = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];


    /**
   * ============
   * Config setup
   * ============
   *
   * [1] : Default route.
   */

    function config($stateProvider, $urlRouterProvider, $locationProvider) {
    // [1]
        $urlRouterProvider.otherwise('/');
    }


    /**
   * ========================
   * Run Dependency Injection
   * ========================
   */

    run.$provide = ['$rootScope', '$state'];


    /**
   * ============
   * Run setup
   * ============
   *
   * [1] : Default route.
   * [2] : Remove page loader after app is loaded.
   */

    function run($rootScope, $state) {
    // [1]
        $rootScope.$state = $state;

        // [2]
        $(window).load(function() {
            loading_screen.finish();
        });
    }

    var website_url = 'https://localhost';
}());
