var win_handles = {};
console.log("Manager.js June 27 16:26")
function sendXHR(parms) {
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
			console.log("data sent");

		}
	}
	request.open('POST', url, true);
	request.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=UTF-8");
	request.send(parms);
}

function create_window() {
	for (var i = 5 - 1; i >= 0; i--) {
		win_handles[i] = window.open(location.origin+ "/pages/channelAction.jsp");
	}
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
	}, 2500 );
}