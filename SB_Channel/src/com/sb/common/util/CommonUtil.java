package com.sb.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CommonUtil
	{
		static SimpleDateFormat outputFormat = new SimpleDateFormat( "MM/dd/yyyy hh:mm aa zzz" );
		static SimpleDateFormat dbFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss aa zzz" );

		public static TimeZone getTimeZone( String zone , boolean isHitDB , String accountNumber )
			{
				if ( accountNumber != null && "8667324271".equalsIgnoreCase( accountNumber ) )
					{
						System.out.println( "Inside account specific block ***************************" );
						return TimeZone.getTimeZone( "US/Arizona" );
					}
				if ( zone.equals( "EDT" ) || zone.equals( "EST" ) )
					return TimeZone.getTimeZone( "America/New_York" );
				else if ( zone.equals( "PDT" ) || zone.equals( "PST" ) )
					return TimeZone.getTimeZone( "America/Los_Angeles" );
				else if ( zone.equals( "CDT" ) || zone.equals( "CST" ) )
					return TimeZone.getTimeZone( "America/Mexico_City" );
				else if ( zone.equals( "MDT" ) || zone.equals( "MST" ) )
					return TimeZone.getTimeZone( "America/Edmonton" );
				else if ( zone.equals( "HADT" ) || zone.equals( "HAST" ) )
					return TimeZone.getTimeZone( "America/Adak" );
				else if ( zone.equals( "AKDT" ) || zone.equals( "AKST" ) )
					return TimeZone.getTimeZone( "America/Anchorage" );
				else if ( zone.equals( "NDT" ) || zone.equals( "NST" ) )
					return TimeZone.getTimeZone( "Canada/Newfoundland" );
				else if ( zone.equals( "ADT" ) || zone.equals( "AST" ) )
					return TimeZone.getTimeZone( "America/Halifax" );
				else if ( zone.equals( "CET" ) || zone.equals( "CEST" ) )
					return TimeZone.getTimeZone( "Europe/Madrid" );
				else if ( zone.equals( "EET" ) || zone.equals( "EEST" ) )
					return TimeZone.getTimeZone( "Europe/Athens" );
				else if ( zone.equals( "WET" ) )
					return TimeZone.getTimeZone( "Europe/Lisbon" );
				else if ( zone.equals( "WST" ) )
					return TimeZone.getTimeZone( "Australia/West" );
				else if ( zone.equals( "NFT" ) )
					return TimeZone.getTimeZone( "Pacific/Norfolk" );
				else if ( zone.equals( "BST" ) )
					return TimeZone.getTimeZone( "Europe/London" );
				else if ( zone.equals( "GMT" ) )
					return TimeZone.getTimeZone( "GMT" );
				else if ( zone.equals( "IST" ) )
					return TimeZone.getTimeZone( "IST" );
				else if ( zone.equals( "CXT" ) )
					return TimeZone.getTimeZone( "Indian/Christmas" );
				else if ( zone.equals( "CAST" ) )
					return TimeZone.getTimeZone( "Antarctica/Casey" );
				else if ( zone.equals( "CCT" ) )
					return TimeZone.getTimeZone( "Indian/Cocos" );
				else if ( zone.equals( "ACST" ) )
					return TimeZone.getTimeZone( "Australia/Adelaide" );
				else if ( zone.equals( "ACWST" ) )
					return TimeZone.getTimeZone( "Australia/Eucla" );
				else if ( zone.equals( "DAVT" ) )
					return TimeZone.getTimeZone( "Antarctica/Davis" );
				else if ( zone.equals( "AEST" ) )
					return TimeZone.getTimeZone( "Australia/Sydney" );
				else if ( zone.equals( "AHMT" ) )
					return TimeZone.getTimeZone( "Antarctica/Mawson" );
				else if ( zone.equals( "LHST" ) )
					return TimeZone.getTimeZone( "Australia/Lord_Howe" );
				else if ( zone.equals( "MAWT" ) )
					return TimeZone.getTimeZone( "Antarctica/Mawson" );
				else if ( zone.equals( "AWST" ) )
					return TimeZone.getTimeZone( "Australia/Perth" );
				else if ( zone.equals( "ChST" ) )
					return TimeZone.getTimeZone( "Pacific/Guam" );
				else if ( zone.equals( "MTA" ) )
					return TimeZone.getTimeZone( "US/Arizona" );
				else if ( zone.equals( "SST" ) )
					return TimeZone.getTimeZone( "Pacific/Apia" );
				else
					return null;
			}

		public static String convertTimeZone( long dateAddedInMillis , String pAccountNumber , String lTimeZoneID ) throws ParseException
			{
				outputFormat.setTimeZone( CommonUtil.getTimeZone( lTimeZoneID , false , pAccountNumber ) );
				return outputFormat.format( dbFormat.parse( dbFormat.format( dateAddedInMillis ) ) );
			}
	}
