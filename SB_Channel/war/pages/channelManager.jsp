<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Channel Manager</title>
<script type="text/javascript" src='http://code.jquery.com/jquery-2.1.0.min.js'></script>
<script type="text/javascript" src="http://images.sb.a-cti.com/sb-channel/staging/js/channelManager.js"></script>
</head>
<body>
<form id="channelSubmit" name="channelSubmit'">
<label for="op">Operation</label>
<select id="op" name='op'>
	<option id="update" value="update">update</option>
	<option id="status" selected="selected" value="status">status</option>
</select>
<label for="data">Data:</label>
<input type="text" id="data" name="data"/>
<!-- <label for="clientId">clientId:</label> -->
<!-- <input type="text" id="clientId" name="clientId"/> -->
<input type="button" onclick="sendXHR($('#channelSubmit').serialize())" value="send">
</form>
<br></br><input type="button" onclick="create_window();" value="create_window">
<input type="button" onclick="delete_window();" value="delete_window">
</body>
</html>