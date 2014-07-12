package com.channel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.client.ClientJDO;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().print(decider(req));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().print(decider(req));
	}

	/**
	 * Request enter's into both methods. based on action param response is
	 * generated.
	 * 
	 * @param req
	 * @return
	 */
	public String decider(HttpServletRequest req) {
		try {

			String controller = req.getParameter("action");
			String response = null;

			Enumeration<?> e = req.getParameterNames();
			System.out
					.println(req.getMethod() + ": Identifier : June 16 11:42");
			while (e.hasMoreElements()) {
				String _param_name = (String) e.nextElement();
				System.out.println(_param_name + " : "
						+ req.getParameter(_param_name));
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
				case "clearMemcache":
					ClientMemCacheManager.clearCache();
					break;
				case "clearDatastore":
					new ClientRecord().clearDatastore();
					break;
				default:
					response = "Access Denied";
					break;
				}

			return ( response == null ) ? "default response" : response ;

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
		String token = "";
		String clientId =  req.getParameter("clientId");
		ClientJDO lClientJDO = null;
		ClientRecord lClientRecord = null;
		try {
			if( StringUtils.isNotEmpty(clientId) ){
				lClientRecord =  new ClientRecord();
				ChannelService cs = ChannelServiceFactory.getChannelService();
				// Check if clientid and token not expired and create new one else reuse.
				lClientJDO = lClientRecord.getClient(clientId); 
				if(  lClientJDO != null )
					token = lClientJDO.getToken();
				else{
					token = cs.createChannel(clientId, ( ClientRecord.time_diff / (1000 * 60) ) ); // 23 hours channel is will available for a client
					lClientRecord.setClient(clientId, token);
				}	
				System.out.println("ClientId : "+clientId+" , token is :" + token);
			}
			else
				System.out.println(" Issue with clientId ::: "+clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	

	public void sendUpdateToUser(String data) {
		List<ClientJDO> _list = null;
		synchronized (this) {
			ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			System.out.println("Synchronized thread,  printing something :::"
					+ data);
			_list = new ClientRecordStoreManager().getClients();
			if( _list != null) {
				for (ClientJDO client : _list) {
//					System.out.println("Sending message to :: "+client.getKey());
					channelService.sendMessage(new ChannelMessage(client.getKey(), data));
				}
				System.out.println("Message sent to client :: "+_list.size() );
			}
		}
	}

	public String createChannelObject(TreeMap<String, Object> hm,
			String operation) {
		String jsonString = null;

		try {
			System.out.println("operation::" + operation);
			JSONObject channelListener = new JSONObject();
			JSONObject dataObj = new JSONObject();
			channelListener.put("name", "channellistener");
			channelListener.put("opt", operation);
			System.out.println("map:" + hm);

			if (hm != null) {
				if (operation.equals("answerphrase")) {
					hm.put("name", "answerphrase");
					hm.put("timestamp", System.currentTimeMillis());
					for (String s : hm.keySet()) {
						dataObj.put(s, hm.get(s));
					}
					channelListener.put("answerphrase", dataObj);
				} else {
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
			e.printStackTrace();
		}

		return jsonString;
	}

	private String refreshToken(HttpServletRequest req) {
		return "refreshToken request is recieved";
	}

	private String push(HttpServletRequest req) {

		String data = req.getParameter("data");
		String operation = req.getParameter("op");
		/* Induvijual user will identified here */

		if ( StringUtils.isNotEmpty(operation) ) {
			TreeMap<String, Object> hm = null;
			new ClientRecordStoreManager().getClients();

			switch (operation) {

			case "update":
				if ( StringUtils.isNotEmpty(data) ) {
					data = data.trim();
					hm = new TreeMap<String, Object>();
					hm.put("accountnumber", data.split("[|]")[0]);
					hm.put("answerphrase", data.split("[|]")[1]);
					sendUpdateToUser(createChannelObject(hm, "answerphrase"));
				}
				break;

			case "status":
				System.out.println("#############Status Update########");
				System.out.println("Info:" + data);
				if (data != null) {
					hm = new TreeMap<String, Object>();
					hm.put("status", data);

					sendUpdateToUser(createChannelObject(hm, "status"));
				}
				break;
				
			case "answerphrase":
				break;

			default:
				System.out.println("######No Match Found#####");
				break;
			}
		}
		return "push request is recieved";
	}
}
