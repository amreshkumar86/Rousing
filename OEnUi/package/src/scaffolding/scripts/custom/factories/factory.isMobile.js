;(function () {

  'use strict';


  /**
   * ===================
   * Registering Factory
   * ===================
   *
   * This factory is used to check for mobile devices
   *
   */

  angular
    .module( 'ponut.core' )
    .factory( 'IsMobile', IsMobile );


  /**
   * =============
   * Factory Setup
   * =============
   */

  function IsMobile() {
    return {
      apple      : isMobile.apple,
      amazon     : isMobile.amazon,
      android    : isMobile.android,
      windows    : isMobile.windows,
      other      : isMobile.other,
      sevenInch  : isMobile.sevenInch,
      any        : isMobile.any,
      phone      : isMobile.phone,
      tablet     : isMobile.tablet
    }
  }


  /**
   * =============
   * Factory Logic
   * =============
   */

  var apple_phone         = /iPhone/i,
      apple_ipod          = /iPod/i,
      apple_tablet        = /iPad/i,
      android_phone       = /(?=.*\bAndroid\b)(?=.*\bMobile\b)/i, // Match 'Android' AND 'Mobile'
      android_tablet      = /Android/i,
      amazon_phone        = /(?=.*\bAndroid\b)(?=.*\bSD4930UR\b)/i,
      amazon_tablet       = /(?=.*\bAndroid\b)(?=.*\b(?:KFOT|KFTT|KFJWI|KFJWA|KFSOWI|KFTHWI|KFTHWA|KFAPWI|KFAPWA|KFARWI|KFASWI|KFSAWI|KFSAWA)\b)/i,
      windows_phone       = /Windows Phone/i,
      windows_tablet      = /(?=.*\bWindows\b)(?=.*\bARM\b)/i, // Match 'Windows' AND 'ARM'
      other_blackberry    = /BlackBerry/i,
      other_blackberry_10 = /BB10/i,
      other_opera         = /Opera Mini/i,
      other_chrome        = /(CriOS|Chrome)(?=.*\bMobile\b)/i,
      other_firefox       = /(?=.*\bFirefox\b)(?=.*\bMobile\b)/i, // Match 'Firefox' AND 'Mobile'
      sevenInch = new RegExp(
        '(?:' +          // Non-capturing group
        'Nexus 7' +      // Nexus 7
        '|' +            // OR
        'BNTV250' +      // B&N Nook Tablet 7 inch
        '|' +            // OR
        'Kindle Fire' +  // Kindle Fire
        '|' +            // OR
        'Silk' +         // Kindle Fire, Silk Accelerated
        '|' +            // OR
        'GT-P1000' +     // Galaxy Tab 7 inch
        ')',             // End non-capturing group
        'i');            // Case-insensitive matching



  var _ = {
    match: function(regex, userAgent) {
      return regex.test(userAgent);
    }
  }

  var IsMobileClass = function(userAgent) {
    var ua = userAgent || navigator.userAgent;

    // Facebook mobile app's integrated browser adds a bunch of strings that
    // match everything. Strip it out if it exists.
    var tmp = ua.split('[FBAN');
    if (typeof tmp[1] !== 'undefined') {
      ua = tmp[0];
    }

    // Twitter mobile app's integrated browser on iPad adds a "Twitter for
    // iPhone" string. Same probable happens on other tablet platforms.
    // This will confuse detection so strip it out if it exists.
    tmp = ua.split('Twitter');
    if (typeof tmp[1] !== 'undefined') {
      ua = tmp[0];
    }

    this.apple = {
      phone        : _.match(apple_phone, ua),
      ipod         : _.match(apple_ipod, ua),
      tablet       : !_.match(apple_phone, ua) && _.match(apple_tablet, ua),
      device       : _.match(apple_phone, ua) || _.match(apple_ipod, ua) || _.match(apple_tablet, ua)
    };

    this.amazon = {
      phone        : _.match(amazon_phone, ua),
      tablet       : !_.match(amazon_phone, ua) && _.match(amazon_tablet, ua),
      device       : _.match(amazon_phone, ua) || _.match(amazon_tablet, ua)
    };

    this.android = {
      phone        : _.match(amazon_phone, ua) || _.match(android_phone, ua),
      tablet       : !_.match(amazon_phone, ua) && !_.match(android_phone, ua) && (_.match(amazon_tablet, ua) || _.match(android_tablet, ua)),
      device       : _.match(amazon_phone, ua) || _.match(amazon_tablet, ua) || _.match(android_phone, ua) || _.match(android_tablet, ua)
    };

    this.windows = {
      phone        : _.match(windows_phone, ua),
      tablet       : _.match(windows_tablet, ua),
      device       : _.match(windows_phone, ua) || _.match(windows_tablet, ua)
    };

    this.other = {
      blackberry   : _.match(other_blackberry, ua),
      blackberry10 : _.match(other_blackberry_10, ua),
      opera        : _.match(other_opera, ua),
      firefox      : _.match(other_firefox, ua),
      chrome       : _.match(other_chrome, ua),
      device       : _.match(other_blackberry, ua) || _.match(other_blackberry_10, ua) || _.match(other_opera, ua) || _.match(other_firefox, ua) || _.match(other_chrome, ua)
    };

    this.sevenInch = _.match(sevenInch, ua);
    this.any = this.apple.device || this.android.device || this.windows.device || this.other.device || this.sevenInch;

    // excludes 'other' devices and ipods, targeting touchscreen phones
    this.phone = this.apple.phone || this.android.phone || this.windows.phone;

    // excludes 7 inch devices, classifying as phone or tablet is left to the user
    this.tablet = this.apple.tablet || this.android.tablet || this.windows.tablet;


  };

  var instantiate = function() {
    var IM = new IsMobileClass();
    IM.Class = IsMobileClass;
    return IM;
  };

  var isMobile = instantiate();

})();
