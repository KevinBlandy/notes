------------------------
Transport
------------------------
	# 传输协议枚举： enum Transport

	  /**
	   * <a href="http://www.rfc-editor.org/rfc/rfc6455.txt">rfc 6455</a>
	   */
	  WEBSOCKET,

	  /**
	   * <a href="http://dev.w3.org/html5/eventsource/">Event source</a>
	   */
	  EVENT_SOURCE,

	  /**
	   * <a href="http://cometdaily.com/2007/11/18/ie-activexhtmlfile-transport-part-ii/">HtmlFile</a>.
	   */
	  HTML_FILE,

	  /**
	   * Slow and old fashioned <a hred="https://developer.mozilla.org/en/DOM/window.postMessage">JSONP polling</a>.
	   * This transport will show "busy indicator" (aka: "spinning wheel") when sending data.
	   */
	  JSON_P,

	  /**
	   * Long-polling using <a hred="https://secure.wikimedia.org/wikipedia/en/wiki/XMLHttpRequest#Cross-domain_requests">cross domain XHR</a>
	   */
	  XHR
	 
