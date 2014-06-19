package com.channel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import common.util.AppCacheManager;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().println(decider(req));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().println(decider(req));
	}

	/**
	 * Request enter's into both methods. 
	 * based on action param response is generated.
	 * @param req
	 * @return
	 */
	public String decider(HttpServletRequest req) {
		try {

			String controller = req.getParameter("action");
			String response = null;

			Enumeration<?> e = req.getParameterNames();
			System.out.println(req.getMethod() + ": Identifier : June 16 11:42");
			while (e.hasMoreElements()) {
				String _param_name = (String) e.nextElement();
				System.out.println(_param_name + " : "+ req.getParameter(_param_name));
			}

			/**
			 * Controller sequence
			 */
			if (StringUtils.isNotEmpty(controller))
				switch (controller) {
				case "getToken":
					response = getToken(req);
					break;
				case "refreshToken":
					response = refreshToken(req);
					break;
				case "push":
					response = push(req);
					break;
				default:
					response = "Access Denied";
					break;
				}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * Get token is initiated and token is passed back from server.
	 * 
	 * @param req
	 * @return
	 */
	private String getToken(HttpServletRequest req) {
		String token="";
		try {
			ChannelService cs = ChannelServiceFactory.getChannelService();
			token = cs.createChannel("answerphrase");
			AppCacheManager.set("answerphraseBroadCast", token);
			System.out.println("token:" + token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public void sendUpdateToUser(String userChannelKey, String data) {
		synchronized (this) {
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			userChannelKey = (String) AppCacheManager
					.get("answerphraseBroadCast");
			System.out.println("userChannelKey:" + userChannelKey);
			System.out.println("Synchronized thread,  printing something :::"
					+ data);
			channelService
					.sendMessage(new ChannelMessage(userChannelKey, data));
			System.out.println("message sent to client");
		}
	}
	
	public String createChannelObject(TreeMap<String, Object> hm,String operation) {
		String jsonString = null;
		
		try {
			System.out.println("operation::"+operation);
			JSONObject channelListener = new JSONObject();
			JSONObject dataObj = new JSONObject();
				channelListener.put("name", "channellistener");
				channelListener.put("opt", operation);
			System.out.println("map:"+hm);

			if(hm != null)
			{
				if ( operation.equals("answerphrase")) {
					hm.put("name", "answerphrase");
					hm.put("timestamp", System.currentTimeMillis());
					for (String s : hm.keySet()) {
						dataObj.put(s, hm.get(s));
					}
					channelListener.put("answerphrase", dataObj);
				}
				else {
							hm.put("name", operation);
							for (String s : hm.keySet()) {
								dataObj.put(s, hm.get(s));
							}
							channelListener.put(operation, dataObj);
				}
			}
			jsonString = channelListener.toString();
			System.out.println("ChannelObject created");
			System.out.println("jsonString::" + jsonString);

			jsonString = channelListener.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;
	}

	
	

	private String refreshToken(HttpServletRequest req) {
		return "refreshToken request is recieved";
	}

private String push(HttpServletRequest req) {
		
		String data=req.getParameter("data");
		String operation=req.getParameter("op");
		
		if(operation!=null && !operation.equals(""))
		{ TreeMap<String,Object> hm=null;
		String userChannelKey=(String) AppCacheManager.get("answerphraseBroadCast");
		
			switch (operation)
			{
			
			case "update":
				if (data != null)
				{	hm = new TreeMap();
				hm.put("accountnumber", data.split("\\|\\|")[0]);
				hm.put("answerphrase", data.split("\\|\\|")[1]);
				
				sendUpdateToUser(userChannelKey, createChannelObject(hm,"answerphrase"));
				System.out.println("inside updateStatus" + userChannelKey);
				}
				break;

			case "status":
				System.out.println("#############Status Update########");
				System.out.println("Info:" + data);
				if (data != null)
				{	hm = new TreeMap();
				hm.put("status", data);
				
				sendUpdateToUser(userChannelKey, createChannelObject(hm,"status"));
				System.out.println("inside updateStatus" + userChannelKey);
				}
				break;
			case "answerphrase": break;

			default:
				System.out.println("######No Match Found#####");
				break;

			}
		
		}
		
			return "push request is recieved";
	}
}
