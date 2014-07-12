/*
 * Channel Action.js
 * CDN URL
 * Live : gsutil -h "Cache-Control:no-cache" cp -z js -a public-read file:///Users/kamesh/git/SB_Channel_Polling/SB_Channel/war/js/channelAction.js gs://images.sb.a-cti.com/sb-channel/live/js/
 * Staging : gsutil -h "Cache-Control:no-cache" cp -z js -a public-read file:///Users/kamesh/git/SB_Channel_Polling/SB_Channel/war/js/channelAction.js gs://images.sb.a-cti.com/sb-channel/staging/js/
 */
var channelKey, pageSocket, counts = 0 , user ;
console.log("July 3 04:03 IST");
function intialize_token(_tmp_token) {
	// Local variables exist alive context for function.
	var channel,token;
	
	if ( _tmp_token )
		token = _tmp_token.toString().trim(); // Extra space was creating
												// issue while creating a
												// channel connection.

	if ( token ) {
		channel = new goog.appengine.Channel(token);
		logData("Channel Successful: " + JSON.stringify(channel) + " at: "+ (new Date()));

		pageSocket = channel.open();

		logData("Socket opened  at:" + new Date());
		
		setTimeout( 'is_talk_gadget_embedded();' , 10000 );
		
		pageSocket.onopen = onOpened;
		pageSocket.onmessage = onMessage;
		pageSocket.onerror = onError;
		pageSocket.onclose = onClose;
		
	} else{
		logData("Invalid data token :: "+token );
	}
		
}

sendMessage = function(path, opt_param) {
	console.log("updateStatus in sendMessage:" + path + "::" + opt_param);
	var xhr = new XMLHttpRequest();
	channelKey = 'answerphrase';
	xhr.open('POST', location.origin + "/Channel/updateStatus.do?key="
			+ channelKey, true);
};

onOpened = function() {
	console.log("open channel event reached successfully");
	connected = true;
	sendMessage('opened');
};

onMessage = function(msg) {
	console.log(" Incoming message from channel ::", msg);
	try {
		var parsed_data = JSON.parse(msg.data);
		console.log("parse success ::: ", parsed_data);
		processData(parsed_data);

	} catch (exception) {
		console.error("parse failed ::: ", msg.data);
		console.error("parse failed ::: ", exception);
	}
	console.log("Post Message sent to parent");
};

onError = function(error) {
	logData("error in Channel " + error + " Client Status:"
			+ window.navigator.onLine ? "online" : "Offline");
	logData("Retry attempt setting up channel");
	/*
	 * Automatically retry will be tried from 10seconds and will increase by
	 * multiplier 10.
	 */
}

onClose = function() {
	logData(":( Channel Closed !!!!! ");
}


/**
 * We witnessed iframe for talk-gadget is not loading in some case we are
 * re-creating the token back to make it smooth.
 */
function is_talk_gadget_embedded(){
	// In development server.
	// Talk gadget frame is not created.
	if( location.port )
		return;
	
	if( document.querySelector('#wcs-iframe') ){
		logData("talk_gadget_embedded frame is present !! ");
		return true
	}
	else{
		logData("talk_gadget_embedded:error Frame not found so, Re-Creating Items!!!! ");
		setTimeout( function(){
			if( !document.querySelector('#wcs-iframe') ){
				logData("talk_gadget being tried to embed again !!! ");
				getChannelToken();
			}
		} , 5000 );
		return false;
	}
}

/**
 * communication between server and channel.
 */
function processData( dataObj ) {
	console.log("processing Data", dataObj.opt);
	var action = null;
	if ( dataObj && dataObj.opt ) {
		var operation = dataObj.opt;
		console.log("object is not null");
		switch ( operation ) {
			case "status":{
				logData(dataObj[operation][operation]);
				action = dataObj[operation][operation];
				if( action && action.toString().toLowerCase() ){
					
					switch ( action.toString().toLowerCase() ) {
						case "restart":{
							getChannelToken();
							break;
						}
						case "reload":{
							kill_switch();
							window.location.reload();
							break;
						}
						case "close":{
							kill_switch();
							break;
						}
						case "generatefeedback":{
							
							break;
						}
						case "clearconsole":{
							document.querySelector('#loggerBox').innerHTML = '';
							console.clear();
							break;
						}
						default:{
							console.error("Unknown sequence in process data :: STATUS CASE ",dataObj );
							break;
						}
					}
				}
				break;
			}
			case "msg":{
				logData("MSG :: ############" + dataObj[operation][operation]+ "############");
				break;
			}	
			case "answerphrase":{
				logData("ANSWER-PHRASE :: ############"+dataObj[operation]["accountnumber"]+" --> "+ dataObj[operation]["answerphrase"]+ "############");
				window.parent.postMessage(dataObj, '*');
				break;
			}	
			default:{
				console.error("Unknown sequence in process data ");
				break;
			}
		}
		// post recieved to parent. Only to check counts please comment this routine in production.
		if( user && user.isDevMode ){
			var _cl = new Clients('count');
			_cl[_cl.opt].clientid = user.email;
			window.parent.postMessage(_cl,'*');	
		}
	}
}


/**
 * Communication between Thinclient window and chat frame.
 * 
 * @param event
 */
function messageListener(event) {
	console.info("MessageListener recieved message from parent ::: ", event.data);
	var operationName = event.data.opt;
	if (operationName) {
		console.log( "opt Name = ", operationName );
		switch (operationName) {
			case "user":{
				console.log("Getting user object :: "+event.data[event.data.opt]);
				user = event.data[event.data.opt].data;
				getChannelToken(user.email);
			} 
			case "trackevent": {
				console.log("track event : operation name :: "+event.data[operationName]);
				var gaObj = event.data[operationName];
				console.info(gaObj.cat, gaObj.act, gaObj.label, gaObj.cval1,
						gaObj.cval2, gaObj.cval3, gaObj.cval4);
				sendData(gaObj.cat, gaObj.act, gaObj.label, gaObj.cval1,
						gaObj.cval2, gaObj.cval3, gaObj.cval4);
				break;
			}
			case "internet_down":{
				console.error(" Internet Down signal recieved from Desktop Client ");
				kill_switch();
				break;
			}
			case "internet_up":{
				console.debug("Internet Up and running");
				getChannelToken();
				break;
			}
			case "init": {
				console.log("Init has been recieved in client application end.");
				var _cl = new ChannelListner('getuserinfo');
				window.parent.postMessage(_cl,'*');
				break;
			}
			default: {
				console.warn("This should never get activated .... ", event.data);
				break;
			}
		}
	}
}

function ChannelListner(lOperation) {
    var operation = (lOperation) ? lOperation : false;
    this.name = 'channellistener';
    this.opt = operation;
    this.answerphrase = {
        name: 'answerphrase',
        accountnumber: false,
        answerphrase: false,
        timestamp: false
    }
    this.getuserinfo = {
    	name: 'getuserinfo'
    }
    this.user = {
        name: 'user',
        data : false
    }
    this.generatefeedback = {
    	name: 'generatefeedback'
    }
    this.reloadv2 = {
       name: 'reloadv2'
    }
    this.reloadchat = {
       name: 'reloadchat'
    }
    this.trackevent = {
        name: 'trackevent',
        cat: '', // accountnumber
        act: '', // any action events anme.
        label: '', // email id.
        cval1: '', // connid id.
        cval2: '', // type of event, ["switchboard/thinclient"]
        cval3: '', // timestamp
        cval4: '' // details, meta-data
    }
    this.script = {
        name: 'script' // get all the current scripts in document experimental.
    }
}

/*
 * Register client with channel server. clientId is unused variable.
 */
function getChannelToken( clientId ) {
	/**
	 * If client is not valid, we will check whether it is present in user
	 * object.
	 */
	if( !clientId ) {
		clientId = ( user && user.email ) ? user.email.trim() : false;
	}
		
	if( clientId ) {
		kill_switch();
		
		var url = location.origin + "/listen?action=getToken&clientId="+clientId;
		logData("getChannelToken posted to URL :: "+ url );
		var isSynchronous = false;
		if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			request = new ActiveXObject("Msxml2.XMLHTTP");
		} else {
			request = new ActiveXObject("Microsoft.XMLHTTP");
		}
		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				logData("Successful response :: "+request.responseText);
				intialize_token(request.responseText);
			}
		}
		
		request.onerror = function(){
			logData("Error response ");
		}
		
		request.open('POST', url, true);
		request.setRequestHeader("Access-Control-Allow-Origin", "*");
		request.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
		request.send("");
	}
	else{
		console.error(" User Info is missing asking parent to send the data to us.");
		var _get_user_info = new ChannelListner('getuserinfo');
		/**
		 * When parent message the data. application will start geting token and
		 * connection will be established.
		 */
		window.parent.postMessage(_get_user_info,'*');
		
	}
}

function kill_switch(){
	/*
	 * pageSocket variable is present in global context. hold's the socket
	 * opened by channel.
	 */
	if( pageSocket ){
		logData("************* Kill switch activated for channel ************  ");
		pageSocket.close();
		pageSocket = null;
	}
}

/** Data logger for jsp */

function logData(data) {
	if( !is_nw_app ){
		/*
		 * On browser alone we are writing into document because DOM insertion's
		 * are costly.
		 */
		var _tmp = new Date();
		_tmp =  _tmp.getHours() + ':' + _tmp.getMinutes() + ':' + _tmp.getSeconds() + ':' + _tmp.getMilliseconds();
		var logData = "Total Count :: " + ( counts++ ) +"<br></br>";
			logData += _tmp+" :: "+ data + "<br></br>"+document.getElementById("loggerBox").innerHTML ;
		document.getElementById("loggerBox").innerHTML = logData;
	}
	console.log( data );
}

function retry() {
	var retry_time = 0;
	
	if( retry.clear_timer_count && arguments.callee.caller)
		return;
	
	if( !retry.isCached ){
		/**
		 * Block will run only one time. Initializing these function's
		 */
		retry.isCached = true;
		retry.counter = 1; // Multiplier.
		retry.multiplier = 10;
		retry.clear_timer_count = false,
		retry.getSeconds = function(){
			logData("Previous retry second's count :: "+retry.counter);
			return ( retry.multiplier * 1000 * retry.counter++ ); // milliseconds
																	// will be
																	// returned.
		}
		retry.default = function(){
			logData("Retry mechanism restored default state!!!");
			retry.counter = 1;
		}
	}
	if ( window.navigator.onLine == true ) {
		logData("########## Client Online ############ ");
		logData("########## Connecting.... ############ ");
		retry.clear_timer_count = false;
		retry.default();
		getChannelToken();
	} else {
		/**
		 * Seconds will increase by 10,20,30 seconds of retry.
		 */
		retry_time = +retry.getSeconds();
		logData("########## Client Offline ############ Will retry in :: "+retry_time);
		retry.clear_timer_count = setTimeout('retry()', retry_time );
	}
}


function sendData(cat, act, label, c1, c2, c3, c4) {
	// recordGAEvent('load','shashank.ashokkumar@a-cti.com','8939401354','shashank','iFrame','iframetest');
	
	_gaq.push([ '_setCustomVar', 1, 'connId', c1 != '' ? c1 : null ]);

	_gaq.push([ '_setCustomVar', 2, 'type', c2 != '' ? c2 : null ]);

	_gaq.push([ '_setCustomVar', 3, 'timeStamp', (new Date()).toString() ]);

	_gaq.push([ '_setCustomVar', 4, 'info', c4 != '' ? c4 : null ]);

	_gaq.push([ '_trackEvent', cat, act, label ]);
	console.info("Sending data in google analytics : ",arguments);
}

(function() {
	var headID = document.getElementsByTagName("script")[0];
	var newScript = document.createElement('script');
	newScript.type = 'text/javascript';
	newScript.async = true;
	newScript.src = 'http://www.google-analytics.com/ga.js';
	headID.parentNode.insertBefore(newScript, headID);
	
	window.is_nw_app = ( navigator.userAgent.indexOf('Tc-webkit') === -1 ) ? false : true;
	if( !is_nw_app ){
		/**
		 * Node-webkit has internal network connect/disconnect mechanism running
		 * so we are making use of it listener will recieve a message from app
		 * regarding connect or disconnect.
		 * 
		 * So, when opened in browser we are activating this basic n/w detection
		 * for testing purpose.
		 */
		console.log("Basic N/W detection is added ");
		window.ononline = function(){
			console.log("Online Event Recieved for n/w ");
			getChannelToken();
		}
		window.onoffline = function(){
			console.log("********* OFFLINE Event Recieved for n/w ");
			kill_switch();
		}
	}
})();

function Clients( opt ){
	this.name = 'clients';
	this.opt = ( opt ) ? opt : false;
	this.count = {
		name : 'count',
		clientid : false
	}
}

function getRequestParameters(url) {
    var keyValue = {};
    if (url.indexOf('?') !== -1) {
        var lUrl = url.split('?')
        lUrl = lUrl[1].split('&');
        for (var i = lUrl.length - 1; i >= 0; i--) {
            var key, value;
            key = lUrl[i].split('=')[0]
            value = lUrl[i].split('=')[1]
            keyValue[key] = value;
        };
    }
    return (keyValue);
}

window.addEventListener('DOMContentLoaded',function(){
	// Dom content has been loaded.
	if( navigator.userAgent.indexOf('Tc-webkit') === -1 ){
		logData("DOMContentLoaded Load event has been fired up ");
		/* Block only used of testing multiple window routine from channelmanager.jsp */
		var params;
		if( typeof getRequestParameters != 'undefined');
			params = getRequestParameters(location.href);
		if( params && params['clientId']){
			getChannelToken( params['clientId'] );
			document.title = params['clientId'];
			user={}; user.email = params['clientId'];
			user.isDevMode = true;
		}
	}
},false);

window.addEventListener('message', messageListener, false);

var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-52099783-2']);
_gaq.push(['_setDomainName', 'staging-sb-channel.appspot.com']);
_gaq.push(['_setAllowLinker', true]);
_gaq.push(['_trackPageview']);