(function () {

  'use strict';



  /**
   * Defining task configurations.
   */

  var
    task = {
      black: {
        options: {
          patterns: [
            {
              match: 'app.dark.css',
              replacement: "app.dark-black.css"
            },
            {
              match: 'app.colors-dark.js',
              replacement: "app.colors-dark-black.js"
            }
          ],
          usePrefix: false
        },
        files: [
          {expand: true, cwd: './.tmp/black/', src: ['**/*.html'], dest: './.tmp/black/'}
        ]
      },
      light: {
        options: {
          patterns: [
            {
              match: 'app.dark.css',
              replacement: "app.light.css"
            },
            {
              match: 'app.colors-dark.js',
              replacement: "app.colors-light.js"
            }
          ],
          usePrefix: false
        },
        files: [
          {expand: true, cwd: './.tmp/light/', src: ['**/*.html'], dest: './.tmp/light/'}
        ]
      }
    };



  /**
   * Exporting task.
   */

  module.exports = task;

})();
