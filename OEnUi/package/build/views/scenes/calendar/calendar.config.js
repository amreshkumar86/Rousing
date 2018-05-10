;(function () {

  'use strict';


  /**
   * ===========
   * Config Init
   * ===========
   */

  angular
    .module( 'ponut.calendar' )
    .config( config )


  /**
   * ==========================
   * Config Providers Injection
   * ==========================
   */

  config.$provide = [ '$stateProvider', '$urlRouterProvider' ];


  /**
   * ============
   * Config setup
   * ============
   *
   * [1] : State setup options.
   * [2] : State init.
   */

  function config( $stateProvider, $urlRouterProvider ) {
    // [1]
    var state = {
          name         : 'calendar',
          url          : '/calendar',
          templateUrl  : 'views/scenes/calendar/calendar.html',
          controller   : 'calendarCtrl',
          controllerAs : 'vm'
        };

    // [2]
    $stateProvider.state( state );
  }

})();
