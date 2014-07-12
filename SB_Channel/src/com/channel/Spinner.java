package com.channel;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Spinner extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		refreshMemcache();
		resp.setContentType("text/plain");
		resp.getWriter().println(req.getMethod() + ":Spinning Instance");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		refreshMemcache();
		resp.setContentType("text/plain");
		resp.getWriter().println(req.getMethod() + ":Spinning Instance");
	}

	private void refreshMemcache() {
		new ClientRecordStoreManager().putClientsInCache();
		System.out.println("spinner is spinning instance to keep it alive "+ new Date());
	}
}
