<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Channel Manager</title>
<script type="text/javascript" src='http://code.jquery.com/jquery-2.1.0.min.js'></script>
<script>
 function sendXHR(parms){
	 url=location.origin+"/listen?action=push";
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
</script>
</head>
<body>
<form id="channelSubmit" name="channelSubmit'">
<label for="op">Operation</label>
<select id="op" name='op'>
	<option id="update" value="update">update</option>
	<option id="status" selected="selected" value="status">status</option>\
</select>
<label for="data">Data:</label>
<input type="text" id="data" name="data"/>
<!-- <label for="clientId">clientId:</label> -->
<!-- <input type="text" id="clientId" name="clientId"/> -->
<input type="button" onclick="sendXHR($('#channelSubmit').serialize())">
</form>
</body>
</html>