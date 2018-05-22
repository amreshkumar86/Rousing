(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var
    task = {
      compile: {
        options: {
          data: {
            debug: false,
          },

          pretty: true
        },

        files: [{
          expand : true,
          cwd    : 'src',
          src    : ['**/*.pug', '!views/scaffolding/**/*.pug'],
          // src    : '**/*.pug',
          dest   : 'build',
          ext    : '.html'
        }]
      }
    };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
