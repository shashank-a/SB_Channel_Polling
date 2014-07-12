/*
 * ChannelManager.js
 * CDN URL
 * Live : gsutil -h "Cache-Control:no-cache" cp -z js -a public-read file:///Users/kamesh/git/SB_Channel_Polling/SB_Channel/war/js/channelManager.js gs://images.sb.a-cti.com/sb-channel/live/js/
 * Staging : gsutil -h "Cache-Control:no-cache" cp -z js -a public-read file:///Users/kamesh/git/SB_Channel_Polling/SB_Channel/war/js/channelManager.js gs://images.sb.a-cti.com/sb-channel/staging/js/
 */

var win_handles = {},client_count = 10,_track_value = 0 ;
console.log("Manager.js June 27 16:26")
function sendXHR( url , params ) {
	if( !url )
		url = location.origin + "/listen?action=push";
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
			console.log('sucess for url :: ',url);
		}
	}
	
	request.onerror = function(){
		alert("Something went wrong while :: "+url);
	}
	
	request.open('POST', url, true);
	request.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=UTF-8");
	request.send(params);
}

function clear_memcache(){
	confirm("Are you sure you wanna clear entire memcache data for this channel app ");
	sendXHR(location.origin+"/listen?action=clearMemcache",{});
}

function clear_datastore(){
	confirm("Are you sure you wanna clear entire Datastore data for this channel app ");
	sendXHR(location.origin+"/listen?action=clearDatastore",{});
}

function client_count_change( evt ){
	var _val = evt.target.value;
	console.log("change in number of clients ", _val );
	if( _val && parseInt(_val) ) 
	client_count = parseInt(_val);
}



function create_window() {
	var _avail_rounds = Math.round(window.screen.availWidth / 200);
	var width, height, top, left;
	width = 200;
	height = 75;
	top = 300;
	left = 202;
	round = 0;

	for (var i = client_count - 1; i >= 0; i--) {
		if (round == _avail_rounds) {
			// default round and increase top;
			round = 0;
			top += height;
		}
		win_handles[i] = window.open(location.origin
				+ "/pages/channelAction.jsp?clientId=client" + i, "_child" + i,
				"width=" + width + ", height=" + height + ",top=" + top
						+ ",left=" + (round * left));
		win_handles[i].parent = window;
		round++
	};

	// for( var i=1; i <= 18 ; i++ ){
	//		
	// }

}

function kill_windows() {
	for (var i = Object.keys(win_handles).length - 1; i >= 0; i--) {
		if (win_handles[i]) {
			console.log("Executing close ::: " + i);
			win_handles[i].close();
			delete win_handles[i];
		}
	}
}

function delete_window() {

	for (var i = Object.keys(win_handles).length - 1; i >= 0; i--) {
		if (win_handles[i]) {
			console.log("Deleting window :: " + i);
			win_handles[i].kill_switch();
			console.log("Channel is getting closed !!! ");
		}
	}

	setTimeout(function() {
		kill_windows();
	}, 2500);
}

function setTrack(){
	_track_value++;
	$('#_track').html(_track_value);
}

function resetTrack(){
	_track_value = 0;
	$('#_track').html(_track_value);
}


function Clients( opt ){
	this.name = 'clients';
	this.opt = ( opt ) ? opt : false;
	this.count = {
		name : 'count',
		clientid : false
	}
}

function childListener( msg ){
	
	if( msg.data.name ){
		switch (msg.data.opt) {
		case "count":{
			setTrack();
//			console.log("From :: "+msg.data[msg.data.opt].clientid);
			break;
		}
		default:
			console.error("What block is this man... ", msg );
			break;
		}
	}
}

window.addEventListener('DOMContentLoaded',function(){
	document.querySelector('#client_count').onchange = client_count_change;
	window.addEventListener('message',childListener,false);
});
