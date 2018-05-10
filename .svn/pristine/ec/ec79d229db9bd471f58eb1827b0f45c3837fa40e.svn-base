module.exports = function ( grunt ) {

  'use strict';



  /**
   * Requiring dependancies.
   */

  var
    path     = require('path'),
    configs  = require('load-grunt-config');



  /**
   * Defining paths to both configuration
   * and building tasks files.
   */

  var
    paths = {
      config : 'grunt/config',
      tasks  : 'grunt/tasks'
    };



  /**
   * Reading `package.json` file for project info.
   */

  var
    pkg = grunt.file.readJSON('package.json');


  /**
   * Itializing Grunt tasks.
   */

  configs(grunt, {
    configPath : path.join( process.cwd(), paths.config ),
    jitGrunt   : {
      customTasksDir : paths.tasks,
      staticMappings: {
        includereplace: 'grunt-include-replace'
      }
    }
  });

};
