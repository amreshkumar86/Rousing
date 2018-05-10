;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.todo' )
    .controller( 'todoCtrl', todoCtrl );

  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  todoCtrl.$inject = [ '$rootScope', '$scope', 'TodoService' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function todoCtrl( $rootScope, $scope, TodoService ) {
    // [1]
    $rootScope.title = 'Todo';
    $scope.todoService = new TodoService($scope);
  }

})();
