package com.sb.common.util;

import java.util.HashMap;

import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityStatus;

public class GoogleCapabilityChecker
	{
		private CapabilityStatus status = null;
		private CapabilitiesService service = null;
		HashMap<String,String> statusMap = new HashMap <String , String>();
		public HashMap<String,String> getStatus()
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
				
				/*request.setAttribute( "appengine-status-map" , statusMap );
				filter.doFilter( request , response );*/
				return statusMap;
			}
	}
