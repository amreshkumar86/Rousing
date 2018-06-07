;(function () {

  'use strict';


  /**
   * ===================
   * Registering Factory
   * ===================
   *
   * This factory is used to store data for
   * sharing between components isolated scopes
   *
   */

  angular
    .module('ponut.core')
    .factory('Store', Store);


  /**
   * =============
   * Factory Setup
   * =============
   */

  function Store() {
    return {
      get: get,
      set: set
    }
  }


  /**
   * ===================
   * Data Storage Object
   * ===================
   */

  var data = {};


  /**
   * ====================
   * Data Getter Function
   * ====================
   *
   * @param ( pointer )
   *   Type  : String | Array of strings.
   *   Descp : Represnts path to required property.
   *   Exp   : `obj.prop.sub`.
   *
   * @return
   *   Value representing matched prop value.
   *
   * [1] : Type check of parameter (array | string) type.
   * [2] : Getting the depth of provided prop path.
   * [3] : Refrencing original data object from which data will be loaded.
   * [4] : Looping through all segments of provided path and getting
   *       the related property value.
   * [5] : Outputing matching prop value .
   */

  function get( pointer ) {
    var ref, i,
        len, path;

    path = typeof pointer === 'string' ? pointer.split('.') : pointer; // [1]
    len  = path.length;                                                // [2]
    ref  = data;                                                       // [3]

    // [4]
    for ( i = 0; i < len; i++ ) {
      ref = ref[ path[ i ] ];
    };

    // [5]
    return ref;
  }


  /**
   * ====================
   * Data Setter Function
   * ====================
   *
   * @param ( pointer )
   *   Type  : String | Array of strings.
   *   Descp : Represnts path to required property to set.
   *   Exp   : `obj.prop.sub`.
   *
   * @param ( value )
   *   Type  : any.
   *   Descp : Represnts value to required property to set.
   *   Exp   : `text` | 0 | {} | [].
   *
   * @param ( Object )
   *   Type  : object.
   *   Descp : Represnts object of key value pairs to set.
   *   Exp   : { 'prop.sub': 1 }.
   *
   * [1] : Check for object type argument and do related logic.
   * [2] : Loop through all provided object keys and do `argSetter` foor each one.
   * [3] : Set `pointer` to retrieved key from the provided object.
   * [4] : Set `value` to corresponding value of the retrieved key from the provided object.
   * [5] : Invoke `propSetter` function with the above two values.
   * [6] : Check if the provided parameters are just prop/value pair.
   * [7] : Check if arguments are non of the above and through an error.
   */

  function set( pointer, value ) {
    var args,    props,   keys,
        argsLen, pointer, value;

    args    = arguments;
    argsLen = args.length;
    props   = args[ 0 ];

    // [1]
    if ( argsLen === 1 && typeof props === 'object') {
      keys = Object.keys( props );

      keys.forEach( argsSetter ); // [2]

      function argsSetter( key ) {
        pointer = key;          // [3]
        value   = props[ key ]; // [4]

        propSetter( pointer, value ); // [5]
      }
    }

    // [6]
    else if ( argsLen === 2 ) {
      propSetter( pointer, value );
    }

    // [7]
    else {
      throw new Error('Store factory takes 1 argeument as Object or 2 arguments as  property/value pair.');
    };
  }


  /**
   * ========================
   * Property Setter Function
   * ========================
   *
   * @param ( pointer )
   *   Type  : String | Array of strings.
   *   Descp : Represnts path to required property to set.
   *   Exp   : `obj.prop.sub`.
   *
   * @param ( value )
   *   Type  : any.
   *   Descp : Represnts value to required property to set.
   *   Exp   : `text` | 0 | {} | [].
   *
   * [1] : Refrencing original data object from which data will be loaded.
   * [2] : Type check of parameter for array | string type.
   * [3] : Getting the depth of provided prop path.
   * [4] : Looping through all segments of provided path and setting
   *       the related property value.
   * [5] : Checking for prop existence, if exists set value, if not
   *       create it and then set value.
   * [6] : Updating refrence to next depth level for next iteration process.
   * [7] : Setting the last depth refrence to the provided value.
   * [8] : If the provided pointer is only one prop, add it directly to data object.
   */

  function propSetter( pointer, value ) {
    var ref, prop, i,
        len, path;

    ref  = data;                                                       // [1]
    path = typeof pointer === 'string' ? pointer.split('.') : pointer; // [2]
    len  = path.length;                                                // [3]

    if ( len > 1 ) {
      // [4]
      for ( i = 0; i < len - 1; i++ ) {
        prop = path[ i ];

        // [5]
        if ( !ref[ prop ] ) {
          ref[ prop ] = {};
        }

        ref = ref[ prop ]; // [6]
      };

      ref[ path[ len - 1 ] ] = value; // [7]
    } else {
      data[ pointer ] = value; // [8]
    }
  }

})();