;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'notificationsCtrl', notificationsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  notificationsCtrl.$inject = [ 'notify', '$interval' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function notificationsCtrl( notify, $interval ) {
    var vm;

    vm = this;

    var notifyOpt = {
      duration : 3000,
      message  : 'Hi, I\'m Notification Messsage',
      position : 'right'
    };

    $interval(function () {
      notify( notifyOpt );
    }, 10000);
  }

})();
