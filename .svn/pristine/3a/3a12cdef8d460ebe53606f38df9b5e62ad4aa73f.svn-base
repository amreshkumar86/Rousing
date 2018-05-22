/**
* Toggles & Switches
* ------------------
* A declarative pattern for applying CSS states
* and animations based on user interaction
*
* @author Digital Surgeons
*/

(function() {

	"use strict";

	// switch constructor
	function Switch(opts) {

		this.type = opts.type;
		this.element = opts.element;
		this.target = opts.target;
		this.event = opts.event || 'click';
		this.onEvent = opts.onEvent || false;
		this.offEvent = opts.offEvent || false;
		this.className = opts.class || 'active';
		this.add = opts.add || 'active';
		this.remove = opts.remove || 'inactive';
		this.self = opts.self || false;
		this.stopPropagation = opts.stopPropagation || false;
		this.events = {};

		// if target is empty default to element itself
		this.target = this.target ? document.querySelectorAll(this.target) : [this.element];

		// mark this element as initialised
		this.element.setAttribute('data-toggle-switch', 'switch');

		// set up switch custom events
		_createCustomEvents.apply(this);

		// set up switch event listeners
		this.bindEventListeners();
	}

	// toggle constructor
	function Toggle(opts) {

		this.type = opts.type;
		this.element = opts.element;
		this.target = opts.target;
		this.className = opts.class || 'active';
		this.add = opts.add || 'active';
		this.remove = opts.remove || 'inactive';
		this.event = opts.event || 'click';
		this.self = opts.self || false;
		this.stopPropagation = opts.stopPropagation || false;
		this.events = {};

		// if target is empty default to element itself
		this.target = this.target ? document.querySelectorAll(this.target) : [this.element];

		// mark this element as initialised
		this.element.setAttribute('data-toggle-switch', 'toggle');

		// set up toggle custom events
		_createCustomEvents.apply(this);

		// set up toggle event listeners
		this.bindEventListeners();
	}

	// initialize custom events
	// spotty support for CustomEvent :/
	function _createCustomEvents() {
		// create 4 types of event
		this.events = {
			'toggled' : document.createEvent('Event'),
			'added' : document.createEvent('Event'),
			'removed' : document.createEvent('Event'),
			'replaced' : document.createEvent('Event')
		};

		this.events.toggled.initEvent('ToggleSwitch.toggled', true, true);
		this.events.added.initEvent('ToggleSwitch.added', true, true);
		this.events.removed.initEvent('ToggleSwitch.removed', true, true);
		this.events.replaced.initEvent('ToggleSwitch.replaced', true, true);
	}

	// cross browser event trigger
	function _triggerEvent(event) {
		// no event name supplied or invalid
		// instance has no element
		if(!event || typeof event !== 'string' || !this.element) {
			return false;
		}

		// check event exists
		var evt = this.events[event];
		if(!evt) {
			return false;
		}

		// trigger event
		this.element.dispatchEvent(evt);
	}

	// bind a single event listener
	function _bindEventListener(event) {
		this.element.addEventListener(event, function(e) {
			e.preventDefault();

			// optional propagation halt
			if(this.stopPropagation) {
				e.stopPropagation();
			}

			this.fire();
		}.bind(this));
	}

	// add class of className to target
	function _addClass() {
		// could be single or multiple targets
		[].forEach.call(this.target, function(el) {
			if(el.classList.contains(this.className)) {
				return false;
			}

			el.classList.add(this.className);
			_triggerEvent.apply(this, ['added']);
		}.bind(this));

		// optionally add class to element itself
		if(this.self) {
			this.element.classList.add(this.className);
		}
	}

	// remove class of className from target
	function _removeClass() {
		// could be single or multiple targets
		[].forEach.call(this.target, function(el) {
			if(!el.classList.contains(this.className)) {
				return false;
			}

			el.classList.remove(this.className);
			_triggerEvent.apply(this, ['removed']);
		}.bind(this));

		// optionally add class to element itself
		if(this.self) {
			this.element.classList.remove(this.className);
		}
	}

	// toggle class of className on target
	function _toggleClass() {
		// could be single or multiple targets
		[].forEach.call(this.target, function(el) {
			el.classList.toggle(this.className);
			_triggerEvent.apply(this, ['toggled']);
		}.bind(this));

		// optionally add class to element itself
		if(this.self) {
			this.element.classList.toggle(this.className);
		}
	}

	Switch.prototype.bindEventListeners = function() {

		var events;

		// custom on switch events
		if(this.type === 'on' && this.onEvent) {

			events = this.onEvent.split(',');

		// custom off switch events
		} else if(this.type === 'off' && this.offEvent) {

			events = this.offEvent.split(',');

		// shared on/off events
		} else {
			events = this.event.split(',');
		}

		// will be array of length 1 if single event
		events.forEach(function(event) {
			_bindEventListener.apply(this, [event]);
		}.bind(this));
	};

	// switch specific replace class logic
	Switch.prototype.replaceClass = function() {
		[].forEach.call(this.target, function(el) {
			el.classList.remove(this.remove);
			el.classList.add(this.add);
			_triggerEvent.apply(this, ['replaced']);
		}.bind(this));
	};

	Toggle.prototype.bindEventListeners = function() {
		var events = this.event.split(',');

		// will be array of length 1 if single event
		events.forEach(function(event) {
			_bindEventListener.apply(this, [event]);
		}.bind(this));
	};

	// toggle specific replace class logic
	Toggle.prototype.replaceClass = function() {
		[].forEach.call(this.target, function(el) {
			// element contains neither class
			// or element contains class that should be removed
			if((!el.classList.contains(this.remove) && !el.classList.contains(this.add)) ||
				el.classList.contains(this.remove)) {

				el.classList.remove(this.remove);
				el.classList.add(this.add);

			// element contains that was added so reverse logic
			} else if(el.classList.contains(this.add)) {
				el.classList.add(this.remove);
				el.classList.remove(this.add);
			}

			_triggerEvent.apply(this, ['replaced']);
		}.bind(this));
	};

	// fire switch
	Switch.prototype.fire = function() {
		// this is a replace switch so replace
		if(this.type === 'replace') {

			this.replaceClass();

		// class not applied this is an on switch so add
		} else if(this.type === 'on') {

			_addClass.apply(this);

		// class applied this is an off switch so remove
		} else if(this.type === 'off') {

			_removeClass.apply(this);
		}
	};

	// fire toggle
	Toggle.prototype.fire = function() {
		if(this.type === 'replace') {
			this.replaceClass();
		} else {
			_toggleClass.apply(this);
		}
	};

	// data attr API initializers
	var initializers = {
		toggles: function(t) {
			// required params
			var opts = {
				element: t,
				target: t.getAttribute('data-toggle')
			};

			// optional params
			if(t.hasAttribute('data-toggle-class')) {
				opts.class = t.getAttribute('data-toggle-class');
			}

			if(t.hasAttribute('data-toggle-event')) {
				opts.event = t.getAttribute('data-toggle-event');
			}

			if(t.hasAttribute('data-toggle-self')) {
				opts.self = true;
			}

			if(t.hasAttribute('data-toggle-stop-propagation')) {
				opts.stopPropagation = true;
			}

			new Toggle(opts);
		},

		togglesReplace: function(t) {
			// required params
			var opts = {
				type: 'replace',
				element: t,
				target: t.getAttribute('data-toggle-replace'),
				add: t.getAttribute('data-toggle-add'),
				remove: t.getAttribute('data-toggle-remove')
			};

			// optional params
			if(t.hasAttribute('data-toggle-stop-propagation')) {
				opts.stopPropagation = true;
			}

			if(t.hasAttribute('data-toggle-event')) {
				opts.event = t.getAttribute('data-toggle-event');
			}

			new Toggle(opts);
		},

		switchesOn: function(s) {
			// required params
			var opts = {
				type: 'on',
				element: s,
				target: s.getAttribute('data-switch-on')
			};

			// optional params
			if(s.hasAttribute('data-switch-class')) {
				opts.class = s.getAttribute('data-switch-class');
			}

			if(s.hasAttribute('data-switch-event')) {
				opts.event = s.getAttribute('data-switch-event');
			}

			if(s.hasAttribute('data-switch-on-event')) {
				opts.onEvent = s.getAttribute('data-switch-on-event');
			}

			if(s.hasAttribute('data-switch-self')) {
				opts.self = true;
			}

			if(s.hasAttribute('data-switch-stop-propagation')) {
				opts.stopPropagation = true;
			}

			new Switch(opts);
		},

		switchesOff: function(s) {
			// required params
			var opts = {
				type: 'off',
				element: s,
				target: s.getAttribute('data-switch-off')
			};

			// optional params
			if(s.hasAttribute('data-switch-class')) {
				opts.class = s.getAttribute('data-switch-class');
			}

			if(s.hasAttribute('data-switch-event')) {
				opts.event = s.getAttribute('data-switch-event');
			}

			if(s.hasAttribute('data-switch-off-event')) {
				opts.offEvent = s.getAttribute('data-switch-off-event');
			}

			if(s.hasAttribute('data-switch-self')) {
				opts.self = true;
			}

			if(s.hasAttribute('data-switch-stop-propagation')) {
				opts.stopPropagation = true;
			}

			new Switch(opts);
		},

		switchesReplace: function(s) {
			// required params
			var opts = {
				type: 'replace',
				element: s,
				target: s.getAttribute('data-switch-replace'),
				add: s.getAttribute('data-switch-add'),
				remove: s.getAttribute('data-switch-remove')
			};

			// optional params
			if(s.hasAttribute('data-switch-stop-propagation')) {
				opts.stopPropagation = true;
			}

			if(s.hasAttribute('data-switch-event')) {
				opts.event = s.getAttribute('data-switch-event');
			}

			new Switch(opts);
		}
	};

	// select all toggles & switches in provided node and initialize
	function initialize(containerNode) {
		var // use not selector to ensure initialized toggles & switches aren't touched
			notInitialized = ':not([data-toggle-switch])',
			toggles = containerNode.querySelectorAll('[data-toggle]'+notInitialized),
			togglesReplace = containerNode.querySelectorAll('[data-toggle-replace]'+notInitialized),
			switchesOn = containerNode.querySelectorAll('[data-switch-on]'+notInitialized),
			switchesOff = containerNode.querySelectorAll('[data-switch-off]'+notInitialized),
			switchesReplace = containerNode.querySelectorAll('[data-switch-replace]'+notInitialized);

		// set up toggles & switches
		[].forEach.call(toggles, initializers.toggles);
		[].forEach.call(togglesReplace, initializers.togglesReplace);
		[].forEach.call(switchesOn, initializers.switchesOn);
		[].forEach.call(switchesOff, initializers.switchesOff);
		[].forEach.call(switchesReplace, initializers.switchesReplace);
	}

	// create mutation observers for watchers
	(function() {
		// check for mutation observers before using, IE11 only
		if(window.MutationObserver == undefined) {
			return;
		}

		[].forEach.call(document.querySelectorAll('[data-toggle-switch-watch]'), function(w) {
			var observer = new MutationObserver(function(mutations) {
				// target will match between all mutations so just use first
				initialize(mutations[0].target);
			});

			observer.observe(w, {
				childList: true
			});
		});
	})();

	// initialize toggles & switches in entire document
	initialize(document);

})();

/*! smooth-scroll v11.1.0 | (c) 2017 Chris Ferdinandi | GPL-3.0 License | http://github.com/cferdinandi/smooth-scroll */
!(function(e,t){"function"==typeof define&&define.amd?define([],t(e)):"object"==typeof exports?module.exports=t(e):e.smoothScroll=t(e)})("undefined"!=typeof global?global:this.window||this.global,(function(e){"use strict";var t,n,o,r,a,i,c,l={},s="querySelector"in document&&"addEventListener"in e,u={selector:"[data-scroll]",ignore:"[data-scroll-ignore]",selectorHeader:null,speed:500,offset:0,easing:"easeInOutCubic",easingPatterns:{},before:function(){},after:function(){}},f=function(){var e={},t=!1,n=0,o=arguments.length;"[object Boolean]"===Object.prototype.toString.call(arguments[0])&&(t=arguments[0],n++);for(;n<o;n++){var r=arguments[n];!(function(n){for(var o in n)Object.prototype.hasOwnProperty.call(n,o)&&(t&&"[object Object]"===Object.prototype.toString.call(n[o])?e[o]=f(!0,e[o],n[o]):e[o]=n[o])})(r)}return e},d=function(e){return Math.max(e.scrollHeight,e.offsetHeight,e.clientHeight)},h=function(e,t){for(Element.prototype.matches||(Element.prototype.matches=Element.prototype.matchesSelector||Element.prototype.mozMatchesSelector||Element.prototype.msMatchesSelector||Element.prototype.oMatchesSelector||Element.prototype.webkitMatchesSelector||function(e){for(var t=(this.document||this.ownerDocument).querySelectorAll(e),n=t.length;--n>=0&&t.item(n)!==this;);return n>-1});e&&e!==document;e=e.parentNode)if(e.matches(t))return e;return null},m=function(e){"#"===e.charAt(0)&&(e=e.substr(1));for(var t,n=String(e),o=n.length,r=-1,a="",i=n.charCodeAt(0);++r<o;){if(0===(t=n.charCodeAt(r)))throw new InvalidCharacterError("Invalid character: the input contains U+0000.");t>=1&&t<=31||127==t||0===r&&t>=48&&t<=57||1===r&&t>=48&&t<=57&&45===i?a+="\\"+t.toString(16)+" ":a+=t>=128||45===t||95===t||t>=48&&t<=57||t>=65&&t<=90||t>=97&&t<=122?n.charAt(r):"\\"+n.charAt(r)}return"#"+a},g=function(e,t){var n;return"easeInQuad"===e.easing&&(n=t*t),"easeOutQuad"===e.easing&&(n=t*(2-t)),"easeInOutQuad"===e.easing&&(n=t<.5?2*t*t:(4-2*t)*t-1),"easeInCubic"===e.easing&&(n=t*t*t),"easeOutCubic"===e.easing&&(n=--t*t*t+1),"easeInOutCubic"===e.easing&&(n=t<.5?4*t*t*t:(t-1)*(2*t-2)*(2*t-2)+1),"easeInQuart"===e.easing&&(n=t*t*t*t),"easeOutQuart"===e.easing&&(n=1- --t*t*t*t),"easeInOutQuart"===e.easing&&(n=t<.5?8*t*t*t*t:1-8*--t*t*t*t),"easeInQuint"===e.easing&&(n=t*t*t*t*t),"easeOutQuint"===e.easing&&(n=1+--t*t*t*t*t),"easeInOutQuint"===e.easing&&(n=t<.5?16*t*t*t*t*t:1+16*--t*t*t*t*t),e.easingPatterns[e.easing]&&(n=e.easingPatterns[e.easing](t)),n||t},p=function(e,t,n){var o=0;if(e.offsetParent)do{o+=e.offsetTop,e=e.offsetParent}while(e);return o=Math.max(o-t-n,0),Math.min(o,y()-b())},b=function(){return Math.max(document.documentElement.clientHeight,e.innerHeight||0)},y=function(){return Math.max(document.body.scrollHeight,document.documentElement.scrollHeight,document.body.offsetHeight,document.documentElement.offsetHeight,document.body.clientHeight,document.documentElement.clientHeight)},v=function(e){return e&&"object"==typeof JSON&&"function"==typeof JSON.parse?JSON.parse(e):{}},O=function(e){return e?d(e)+e.offsetTop:0},S=function(t,n,o){o||(t.focus(),document.activeElement.id!==t.id&&(t.setAttribute("tabindex","-1"),t.focus(),t.style.outline="none"),e.scrollTo(0,n))};l.animateScroll=function(n,o,i){var l=v(o?o.getAttribute("data-options"):null),s=f(t||u,i||{},l),d="[object Number]"===Object.prototype.toString.call(n),h=d||!n.tagName?null:n;if(d||h){var m=e.pageYOffset;s.selectorHeader&&!r&&(r=document.querySelector(s.selectorHeader)),a||(a=O(r));var b,E,I=d?n:p(h,a,parseInt("function"==typeof s.offset?s.offset():s.offset,10)),H=I-m,A=y(),j=0,C=function(t,r,a){var i=e.pageYOffset;(t==r||i==r||e.innerHeight+i>=A)&&(clearInterval(a),S(n,r,d),s.after(n,o))},M=function(){j+=16,b=j/parseInt(s.speed,10),b=b>1?1:b,E=m+H*g(s,b),e.scrollTo(0,Math.floor(E)),C(E,I,c)};0===e.pageYOffset&&e.scrollTo(0,0),s.before(n,o),(function(){clearInterval(c),c=setInterval(M,16)})()}};var E=function(t){try{m(decodeURIComponent(e.location.hash))}catch(t){m(e.location.hash)}n&&(n.id=n.getAttribute("data-scroll-id"),l.animateScroll(n,o),n=null,o=null)},I=function(r){if(0===r.button&&!r.metaKey&&!r.ctrlKey&&(o=h(r.target,t.selector))&&"a"===o.tagName.toLowerCase()&&!h(r.target,t.ignore)&&o.hostname===e.location.hostname&&o.pathname===e.location.pathname&&/#/.test(o.href)){var a;try{a=m(decodeURIComponent(o.hash))}catch(e){a=m(o.hash)}if("#"===a){r.preventDefault(),n=document.body;var i=n.id?n.id:"smooth-scroll-top";return n.setAttribute("data-scroll-id",i),n.id="",void(e.location.hash.substring(1)===i?E():e.location.hash=i)}n=document.querySelector(a),n&&(n.setAttribute("data-scroll-id",n.id),n.id="",o.hash===e.location.hash&&(r.preventDefault(),E()))}},H=function(e){i||(i=setTimeout((function(){i=null,a=O(r)}),66))};return l.destroy=function(){t&&(document.removeEventListener("click",I,!1),e.removeEventListener("resize",H,!1),t=null,n=null,o=null,r=null,a=null,i=null,c=null)},l.init=function(n){s&&(l.destroy(),t=f(u,n||{}),r=t.selectorHeader?document.querySelector(t.selectorHeader):null,a=O(r),document.addEventListener("click",I,!1),e.addEventListener("hashchange",E,!1),r&&e.addEventListener("resize",H,!1))},l}));
;(function () {

  'use strict';
  smoothScroll.init();

})();
