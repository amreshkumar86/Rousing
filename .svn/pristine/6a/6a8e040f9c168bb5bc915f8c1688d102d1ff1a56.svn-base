(function () {

  'use strict';



  /**
   * Defining task dependencies.
   */

  var dependancies = [
        'clean:build',
        'concurrent:build',
        'concurrent:pretty',
        'browserSync:build',
        'watch'
      ];



  /**
   * Registering task name.
   */

  var task = function ( grunt ) {
        grunt.registerTask('build', dependancies);
      };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
