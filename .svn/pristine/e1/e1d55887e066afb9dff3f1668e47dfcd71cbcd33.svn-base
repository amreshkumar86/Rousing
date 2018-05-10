(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var
    task = {
      compress: {
        options: {
          report      : 'gzip',
          restructure : false
        },

        files: [{
          expand : true,
          cwd    : './dist',
          src: [
            '**/*.css',
            '!**/*.min.css'
          ],
          dest : './dist',
          ext  : '.css',
          extDot  : 'last'
        }]
      }
    };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
