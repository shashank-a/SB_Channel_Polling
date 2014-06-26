/* channel function */


	var channelKey;
	var pageSocket;
	console.log("June 18 18:05 ist");
	function testToken(_token) {
		console.log("inside testToken...");
		channel = new goog.appengine.Channel(_token);
		socket = channel.open();
		window.pageSocket=socket;
		console.log("channel::" + JSON.stringify(channel));
		logData("Channel Successful: "+JSON.stringify(channel) +" at:"+ (new Date()));
		socket.onopen = onOpened;
		socket.onmessage = onMessage;
		socket.onerror = onError;
		socket.onclose = onClose;
	}
	
	function messageListener( event ){
		console.info("messageListener got a message from parent ::: ",event.data);
		var operationName = event.data.opt;
		console.log(operationName);
		if( operationName ){
			switch( operationName ){
				case "trackevent":{
					console.log("track event");

					console.info(event.data[operationName]);
					var gaObj=event.data[operationName];
					console.info(gaObj.cat,gaObj.act,gaObj.label,gaObj.cval1,gaObj.cval2,gaObj.cval3,gaObj.cval4);
					sendData(gaObj.cat,gaObj.act,gaObj.label,gaObj.cval1,gaObj.cval2,gaObj.cval3,gaObj.cval4);

					/* shanshank code */
					break;
				}
				case "answerphrase":{
						console.log("answerphrase");
					console.info(event.data.m);
					//getChannelToken();
					/* shanshank code */
					break;
				}
				default : {
					console.warn("This should never get activated .... ",event.data);
					break;
				}
			}
		}
	}
	
		window.addEventListener('message',messageListener,false);
	
	
	sendMessage = function(path, opt_param) {
		console.log("updateStatus in sendMessage:"+path+"::"+opt_param);
		var xhr = new XMLHttpRequest();
		//xhr.open('POST', "http://staging-gae.appspot.com/Channel/updateStatus.do?key="+channelKey, true);
		channelKey='answerphrase';
		//xhr.open('POST', "http://shashank.sb.a-cti.com:8888/Channel/updateStatus.do?key="+channelKey, true);
		//xhr.open('POST', location.protocol+'://'+location.host+"/Channel/updateStatus.do?key="+channelKey, true);
		xhr.open('POST', location.origin+"/Channel/updateStatus.do?key="+channelKey, true);
		
		//alert("sending update to:::"+"http://195.staging-gae.appspot.com/Channel/updateStatus.do?key="+channelKey);
		//xhr.send();
	};
	
	onOpened = function() {
		console.log("inside on opened");
		connected = true;
		sendMessage('opened');
	};
	
	onMessage = function(msg) {
		//alert("ON messaage::"+JSON.stringify(msg));
		//window.parent.updateAPCache(msg);
		console.log(" Incoming message from channel ::",msg);
		//logData(JSON.stringify(msg));
		try{
			var parsed_data = JSON.parse(msg.data);
			console.log("parse success ::: ",parsed_data);
			processData(parsed_data);
			
	
		}
		catch( exception ){
			console.info("parse failed ::: ",msg.data);
			console.info("parse failed ::: ",exception);
		}
		
		console.log("Post Message sent to parent");
		
	};
	
	onError = function(error) {
		console.log("inside error:" + error);
		logData("error in Channel "+error+" Client Status:"+window.navigator.onLine?"online":"Offline");
		logData("Retry attempt setting up channel");
		setTimeout('retry()',10000);
	}
	
	onClose = function(cl) {
		console.log("inside onclose:" + cl);
		console.log("closing CHannel");
		logData("Channel Closed" + cl);
	}
/*
**
		Register client with channel server.
*/	
	function getChannelToken(clientId)
	{
		//var url='http://staging-gae.appspot.com/Channel/getToken.do';
		
		//var url=location.protocol+'://'+location.host+'/Channel/getToken.do';
		//var url=location.origin+'/Channel/getToken.do'+"?clientId="+clientId;
		var url=location.origin+'/listen'+"?action="+"getToken";
		console.log(url);
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
				console.log("Successful Request");
				var responseText = request.responseText;
				
				testToken(responseText);
			setTimeout('getChannelToken()',900000);
			}
		}
		request.open('POST', url, true);
		request.setRequestHeader( "Access-Control-Allow-Origin" , "*" );
		request.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
	
		request.send("");
	
		
		}

		/** Data logger for jsp*/
	
		function logData(data)
		{
	
			var logData=document.getElementById("loggerBox").innerHTML+"<br>"+data+"<br>";
			document.getElementById("loggerBox").innerHTML=logData;
			console.log((new Date()).getTime(),"Incoming message",data);
		}



		/**  process action received from server*/


		function processData(dataObj)
		{   console.log("processing Data"+dataObj.opt);
		var action=null;
			if(dataObj!=null)
			{	console.log("object is not null");
				
			var operation=dataObj.opt;
			if(operation=="status")
			{
				logData(dataObj[operation][operation]);
				action=dataObj[operation][operation];
				if(action=="Restart")
					retry();
				if(action=="Reload")
					window.location.reload();
				if(action=="Close")
					pageSocket.close();

			}
			if(operation=="msg")
				logData("############"+dataObj[operation][operation]+"############");
			if(operation=="answerphrase")
				{
					logData("############AP Update############");
					logData("############"+dataObj[operation][operation]+"############");
					window.parent.postMessage(dataObj,'*');
					//window.parent.notifyMessage('AP Update',"Updated for: "+dataObj[operation][accountnumber],notificationIcon.alert);
					//console.log("after notification");

				}
				
			}
	
		}
	
		function retry()
		{
			if(window.navigator.onLine==true)
			{  logData("########## Client Online ############");
				logData("########## Connecting.... ############");	

				getChannelToken('answerphrase');
			}	
			else
			{
				logData("########## Client Offline ############");
				setTimeout('retry()',30000);
			}
	
		}
function sendData(cat,act,label,c1,c2,c3,c4)
  {
	  //recordGAEvent('load','shashank.ashokkumar@a-cti.com','8939401354','shashank','iFrame','iframetest');
	  //console.info(acctInfo, uAction, initial);
			_gaq.push([ '_setCustomVar', 1, 'connId',c1!=''?c1:null ]);
		
			_gaq.push([ '_setCustomVar', 2, 'type',c2!=''?c2:null ]);

			_gaq.push([ '_setCustomVar', 3, 'timeStamp',(new Date()).toString() ]);

			_gaq.push([ '_setCustomVar', 4, 'info',c4!=''?c4:null ]);
		
		_gaq.push([ '_trackEvent', cat,act,label]);
  }
	
	(function(){
    var headID = document.getElementsByTagName("script")[0];         
    var newScript = document.createElement('script');
    newScript.type = 'text/javascript';
    newScript.async=true;
    newScript.src='http://www.google-analytics.com/ga.js';
    headID.parentNode.insertBefore(newScript, headID);
   })();
