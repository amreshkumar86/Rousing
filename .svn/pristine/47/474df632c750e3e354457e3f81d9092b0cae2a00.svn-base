(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var
    task = {
      options: {
        config: '.csscombrc'
      },

      target: {
        files: [{
          expand : true,
          cwd    : './build',
          src    : ['**/*.css', '!**/*.min.css'],
          dest   : './build'
        }]
      }
    };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
