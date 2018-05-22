(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var task = {
        build: {
          // updateAndDelete: true,
          files: [{
            expand: true,
            cwd: './src',
            src: [ '**/*.*', '!**/*.pug', '!**/*.styl' ],
            dest: './build'
          }]
        },

        dist: {
          // updateAndDelete: true,
          files: [{
            expand: true,
            cwd: './build',
            src: ['**/*', '!**/views/**/*.js', '!**/scaffolding/**'],
            dest: './dist'
          }]
        },
      };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
