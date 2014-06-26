package com.channel;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Spinner extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
//		System.out.println(req.getMethod()+":Pinging Check = "+new Date());
		resp.setContentType("text/plain");
		resp.getWriter().println(req.getMethod()+":Spinning Instance");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
//		System.out.println(req.getMethod()+":Pinging Check = "+new Date());
		resp.setContentType("text/plain");
		resp.getWriter().println(req.getMethod()+":Spinning Instance");
	}
}
