(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var
    task = {
      target: {
        files: [{
          expand : true,
          cwd    : './dist/resources',
          src    : ['**/*.{png,jpg,gif}'],
          dest   : './dist/resources'
        }]
      }
    };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
