(function () {

  'use strict';


  /**
   * ======================
   * Requiring Dependancies
   * ======================
   */

  var autoprefixer = require( 'autoprefixer' ),
      flexbugs     = require( 'postcss-flexbugs-fixes' ),
      fontMagician = require( 'postcss-font-magician' ),
      mqpacker     = require( 'css-mqpacker' ),
      rucksack     = require( 'rucksack-css' ),
      systemFont   = require( 'postcss-font-family-system-ui' ),
      poststylus   = require( 'poststylus' );


  /**
   * ================================
   * Defining PostCSS Plugins Options
   * ================================
   */

  var options = {};


  /**
   * =============================
   * Defining PostCSS Dependancies
   * =============================
   */

  var postcssPlugins = [
        autoprefixer,
        flexbugs,
        fontMagician,
        mqpacker,
        rucksack,
        systemFont
      ];


  /**
   * ==================================
   * Defining PostCSS plugin For Stylus
   * ==================================
   */

  var postcss = function () {
        return poststylus( postcssPlugins );
      };


  /**
   * ============================
   * Defining Task Configurations
   * ============================
   */

  var task = {
        target    : {
          options : {
            use      : [ postcss ],
            compress : false
          },

          files: [{
            expand : true,
            cwd    : 'src',
            src    : ['app.dark.styl', 'app.dark-black.styl', 'app.light.styl'],
            dest   : 'build',
            ext    : '.css',
            extDot : 'last'
          }]
        }
      };


  /**
   * ==============
   * Exporting Task
   * ==============
   */

  module.exports = task;

})();
