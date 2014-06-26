package com.sb.common.util;

import java.io.File;
import java.io.Serializable;
import java.util.Hashtable;



public class TemplateTest
	{
		public static void main( String[] args )
			{
				try{
					String fieldTitle = "Caller ID";
					String fieldValue = "(800)80800-898";
					System.out.println(fieldTitle.indexOf( "Caller ID" ));
					if(fieldTitle.indexOf( "Caller ID" ) != -1){
						System.out.println("inside ..");
						fieldValue = fieldValue.replaceAll( "\\)" , "" ).replaceAll( "\\(" , "" ).replaceAll( "-" , "" ).trim();
					}
						
					System.out.println("-->"+fieldValue );
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
	}
