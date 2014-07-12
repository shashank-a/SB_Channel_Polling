package com.sb.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityStatus;


public class URLFilter implements Filter
	{
		
		private CapabilityStatus status = null;
		private CapabilitiesService service = null;
		private static final Logger log = Logger.getLogger( URLFilter.class.getName() );
		HashMap<String,String> statusMap = new HashMap <String , String>();
		@Override
		public void doFilter( ServletRequest request , ServletResponse response , FilterChain filter ) throws IOException , ServletException
			{
				
				service = CapabilitiesServiceFactory.getCapabilitiesService();
				status = service.getStatus( Capability.DATASTORE ).getStatus();
				statusMap.put( "DataStore" , status.toString() );
				
				status = service.getStatus( Capability.DATASTORE_WRITE ).getStatus();
				statusMap.put( "DataStore_Write" , status.toString() );
				
				status = service.getStatus( Capability.TASKQUEUE ).getStatus();
				statusMap.put( "TaskQueue" , status.toString() );
				
				status = service.getStatus( Capability.MAIL ).getStatus();
				statusMap.put( "Mail" , status.toString() );
				
				System.out.println( "status in URLFilter :::" + statusMap.toString() );
				request.setAttribute( "appengine-status-map" , statusMap );
				filter.doFilter( request , response );
			}

		@Override
		public void destroy()
			{
			}

		@Override
		public void init( FilterConfig arg0 ) throws ServletException
			{
			}
	}
