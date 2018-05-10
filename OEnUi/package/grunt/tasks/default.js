(function () {

  'use strict';



  /**
   * Defining task dependencies.
   */

  var dependancies = [
        'concurrent:build',
        'browserSync:build',
        'watch'
      ];



  /**
   * Registering task name.
   */

  var task = function ( grunt ) {
        grunt.registerTask('default', dependancies);
      };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
