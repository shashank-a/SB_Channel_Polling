<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html><head>
<!-- <script type="text/javascript" src="/js/channel.js"></script> -->
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript" src="http://images.sb.a-cti.com/testing/shashank/js/channelListner.js"></script>
<!-- <script type="text/javascript" src="/js/channelListner.js"></script> -->
<script>
//for http://sb-channel.appspot.com/
// var _gaq = _gaq || [];
// _gaq.push(['_setAccount', 'UA-52099783-1']);
// _gaq.push(['_setDomainName', 'sb-channel.appspot.com']);
// _gaq.push(['_setAllowLinker', true]);
// _gaq.push(['_trackPageview']);


//for  http://staging-sb-channel.appspot.com
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-52099783-2']);
_gaq.push(['_setDomainName', 'staging-sb-channel.appspot.com']);
_gaq.push(['_setAllowLinker', true]);
_gaq.push(['_trackPageview']);




</script>
</head>
<body onload="getChannelToken('answerphrase')">
<div id="loggerBox">
</div>

</body>
</html>