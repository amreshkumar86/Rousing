;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.dashboard' )
    .controller( 'dashboardCtrl', dashboardCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  dashboardCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function dashboardCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Dashboard';

    var vm = this;
    
    
    
    $rootScope.data = {
    		  "options": {
    			    "colors": ["colors.pink[500]", "colors.green[500]"],
    			    "series": { "shadowSize": null },

    			    "legend": {
    			      "backgroundOpacity": 0,
    			      "position": "ne",
    			      "noColumns": 2
    			    },

    			    "xaxis": {
    			      "font": {
    			        "color": "colors.palette.grey.minor"
    			      },

    			      "tickColor": "colors.palette.grey.muted",
    			      "position": "bottom",
    			      "ticks": [
    			        [0, "Jan"],
    			        [1, "Feb"],
    			        [2, "Mar"],
    			        [3, "Apr"],
    			        [4, "May"],
    			        [5, "Jun"],
    			        [6, "Jul"],
    			        [7, "Aug"],
    			        [8, "Sep"],
    			        [9, "Oct"],
    			        [10, "Nov"],
    			        [11, "Dec"]
    			      ]
    			    },

    			    "yaxis": {
    			      "font": {
    			        "color": "colors.palette.grey.minor"
    			      },

    			      "tickColor": "colors.palette.grey.muted"
    			    },

    			    "grid": {
    			      "borderWidth": {
    			        "top": 1,
    			        "right": 1,
    			        "bottom": 1,
    			        "left": 1
    			      },

    			      "borderColor": "colors.palette.grey.muted",
    			      "margin": 0,
    			      "minBorderMargin": 0,
    			      "labelMargin": 14,
    			      "hoverable": true,
    			      "clickable": true,
    			      "mouseActiveRadius": 6
    			    },

    			    "tooltip": true,
    			    "tooltipOpts": {
    			      "content": "%x.1 is %y.4",
    			      "defaultTheme": false,
    			      "shifts": {
    			        "x": 0,
    			        "y": 20
    			      }
    			    }
    			  },
    			  "data": [
    			    {
    			      "data": [
    			        [0, 10],
    			        [1, 8],
    			        [2, 16],
    			        [3, 8],
    			        [4, 10],
    			        [5, 6],
    			        [6, 12],
    			        [7, 4],
    			        [8, 8],
    			        [9, 10]
    			      ],

    			      "label": "Angular",

    			      "points": {
    			        "show": true,
    			        "radius": 1
    			      },

    			      "splines": {
    			        "show": true,
    			        "tension": 0.45,
    			        "lineWidth": 1,
    			        "fill": 0
    			      }
    			    },
    			    {
    			      "data": [
    			        [0, 12],
    			        [1, 16],
    			        [2, 12],
    			        [3, 4],
    			        [4, 10],
    			        [5, 2],
    			        [6, 8],
    			        [7, 6],
    			        [8, 4],
    			        [9, 10]
    			      ],

    			      "label": "React",

    			      "points": {
    			        "show": true,
    			        "radius": 1
    			      },

    			      "splines": {
    			        "show": true,
    			        "tension": 0.45,
    			        "lineWidth": 1,
    			        "fill": 0
    			      }
    			    }
    			  ]
    			}
    
    alert(Json.stringify($rootScope.data));

  }

})();
