;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut.todo' )
    .directive('todo', todo)


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  todo.$inject = [ 'TodoService' ];

  /**
   * ===============
   * Directive Setup
   * ===============
   */

   function todo(TodoService) {
     return {
       restrict: 'EA',
       templateUrl: 'tpl/partials/todo-widget.html',
       replace: true,
       link: link
     };

     function link($scope, $element) {
       $scope.todoService = new TodoService($scope);
     }
   }

})();
