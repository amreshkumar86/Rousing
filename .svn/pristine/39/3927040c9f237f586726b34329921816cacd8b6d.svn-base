(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var
    task = {
      build  : ['stylus', 'pug', 'sync:build'],
      pretty : ['csscomb', 'prettify'],
      dist   : ['csso', ['concat', 'uglify'], 'imagemin']
    };


  /**
   * Exporting task.
   */

  module.exports = task;

})();
