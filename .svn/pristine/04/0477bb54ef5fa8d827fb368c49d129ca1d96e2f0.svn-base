(function () {

  'use strict';



  /**
   * Defining task dependencies.
   */

  var dependancies = [
        'clean:dist',
        'sync:dist',
        'concurrent:dist',
        'processhtml',
        'htmlmin',
        'browserSync:dist'
      ];



  /**
   * Registering task name.
   */

  var task = function ( grunt ) {
        grunt.registerTask('dist', dependancies);
      };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
