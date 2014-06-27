package common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Test
	{
		public static void main( String[] args ) throws ParseException
			{
			
				String data = "	8777123123|hello world";
				System.out.println("1:: "+data.split("[|]")[0]);
				System.out.println("2:: "+data.split("[|]")[1]);
//				SimpleDateFormat outputFormat = new SimpleDateFormat( "MM/dd/yyyy hh:mm aa zzz" );
//				outputFormat.setTimeZone( TimeZone.getTimeZone( "Antarctica/Casey" ) );
//				System.out.println(outputFormat.format( new Date() ));
//				
//				SimpleDateFormat outputFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa Z");
//				  TimeZone tz = TimeZone.getTimeZone("Antarctica/Casey");
//				  outputFormat1.setTimeZone(tz);
//
//				  Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//
//				  //May 2014 = GMT + 8
//				  System.out.println(outputFormat1.format(c.getTime()));
//
//				  //january 2012 = GMT + 11
//				  c.set(Calendar.YEAR, 2012);
//				  c.set(Calendar.MONTH, 0);
//				  System.out.println(outputFormat1.format(c.getTime()));
//				  filterFlag
				  String dateFrom="2014-06-04";
				  String dateTo="2014-06-06";
				  String filterFlag="true";
				  Test t=new Test();
				  t.formatDate(dateFrom, "start");
				  Long minimumDate = null;
					Long maximumDate = null;

					if (dateFrom != null && !dateFrom.equals("")) {
						System.out.println(dateFrom);

						System.out.println(filterFlag);
						if (filterFlag.equals("true")) {
							maximumDate = t.formatDate(dateFrom, "start");
							System.out.println("Formatting dateFrom ");
						} else {
							maximumDate = Long.valueOf(dateFrom);
						}
						System.out.println(maximumDate);
					}

					if (dateTo != null && !dateTo.equals("")) {
						minimumDate = t.formatDate(dateTo, "end");
						System.out.println(minimumDate);
						System.out.println("Formatting dateTO " + minimumDate);
					}
				  
				  
			}
		
		public long formatDate(String dateString, String offset) {
			long millisec = 0;
			if (offset != null && !offset.equals("")) {
//				Calendar cal = Calendar.getInstance();
//				cal.set(Calendar.YEAR, Integer.parseInt(dateString.split("-")[0]));
//				cal.set(Calendar.MONTH, Integer.parseInt(dateString.split("-")[1]));
//				cal.set(Calendar.DAY_OF_MONTH,
//						Integer.parseInt(dateString.split("-")[2]));
//				
//					cal.set(Calendar.HOUR, 0);
//					cal.set(Calendar.MINUTE, 0);
//					cal.set(Calendar.SECOND, 0);
//					System.out.println(cal.toString());
//					System.out.println("Formatting Date"+cal.getTimeInMillis());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateInString = "2014-06-04 00:00:00";		
				 
				try {
					Date date = formatter.parse(dateInString);
					System.out.println(date);
					System.out.println(formatter.format(date));
					System.out.println(date.getTime());
			 
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (offset.equals("end")) {
					Calendar cal = Calendar.getInstance();
					 
					cal.set(Calendar.YEAR, Integer.parseInt(dateString.split("-")[0]));
					cal.set(Calendar.MONTH, Integer.parseInt(dateString.split("-")[1]));
					cal.set(Calendar.DAY_OF_MONTH,
							Integer.parseInt(dateString.split("-")[2]));
					cal.roll(Calendar.HOUR, 23);
					cal.roll(Calendar.MINUTE, 59);
					cal.roll(Calendar.SECOND, 59);
					System.out.println("Formatting End Date"+cal.getTimeInMillis());
					
				}
				
			}
			return millisec;
	}
}
