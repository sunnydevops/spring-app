package com.ltfs.ConcentApp.utility;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;

public class DateUtility {

	public static final String YEARS = "YEARS";
	public static final String DAYS = "DAYS";
	public static final String HOURS = "HRS";
	public static final String MINUTES = "MIN";
	public static final String SECONDS = "SEC";
	public final static long SECOND_MILLIS = 1000;
	public final static long MINUTE_MILLIS = SECOND_MILLIS * 60;
	public final static long HOUR_MILLIS = MINUTE_MILLIS * 60;
	public final static long DAY_MILLIS = HOUR_MILLIS * 24;
	public final static long YEAR_MILLIS = DAY_MILLIS * 365;
	public static Hashtable hashTab;

	public DateUtility() {
		super();
	}

	public static Timestamp getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}

	public static Timestamp addDays(Timestamp timestamp, int intDays) {
		Calendar c1 = GregorianCalendar.getInstance();
		// // PerfiosLogger.logInfo(timestamp.toString());
		// // PerfiosLogger.logInfo(timestamp.getYear());
		c1.set(Integer.parseInt(timestamp.toString().substring(0, 4)), timestamp.getMonth(), timestamp.getDate()); // 1999
																													// jan
																													// 20
		c1.add(Calendar.DATE, intDays);
		java.util.Date date = c1.getTime();
		return new java.sql.Timestamp(date.getTime());
	}

	public static Timestamp stripTime(Timestamp timestamp) {
		return timestamp.valueOf(timestamp.toString().substring(0, 10) + " 00:00:00.000");
	}

	public static Timestamp appendTime(Timestamp timestamp) {
		return timestamp.valueOf(timestamp.toString().substring(0, 10) + " 23:59:59.000");
	}

	public static String parseDateToString(java.util.Date today) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yy");
		return df.format(today);
	}

	public static String parseDateToString(java.util.Date today, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(today);

	}

	public static Timestamp parseStringToDate(String today) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			// you can change format of date
			Date date = (Date) formatter.parse(today);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			System.out.println(timeStampDate);
			return timeStampDate;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

	public String checkNull(Object constant) {
		if (constant != null) {
			return constant.toString();
		} else {
			return "";
		}
	}

	public static Integer getTimeInInteger() {
		java.util.Date today = new java.util.Date();
		DateFormat df = new SimpleDateFormat("kkmmssSSS");
		return Integer.parseInt(df.format(today));
	}

	public static long getDaysDifference(Timestamp time1, Timestamp time2, String strType) {

		long timeDifference = time1.getTime() - time2.getTime();

		// // PerfiosLogger.logInfo(" DATEUTIL:"+strType+"_"+timeDifference);
		if (strType.equals(YEARS)) {

			long years = (timeDifference / (1000l * 60 * 60 * 24 * 365));
			return years;
		} else if (strType.equals(DAYS)) {
			time1 = stripTime(time1);
			time2 = stripTime(time2);
			timeDifference = time1.getTime() - time2.getTime();
			timeDifference = timeDifference / (24 * 60 * 60 * 1000);
		} else if (strType.equals(HOURS)) {
			timeDifference = timeDifference / (60 * 60 * 1000);
		} else if (strType.equals(MINUTES)) {
			timeDifference = timeDifference / (60 * 1000);
		} else if (strType.equals(SECONDS)) {
			timeDifference = timeDifference / (1000);
		} else {
			timeDifference = 0L;
		}
		// // PerfiosLogger.logInfo(" DATEUTIL:"+strType+"_"+timeDifference);
		return timeDifference;
	}

	public static int hoursDiff(Date earlierDate, Date laterDate) {
		if (earlierDate == null || laterDate == null) {
			return 0;
		}

		return (int) ((laterDate.getTime() / HOUR_MILLIS) - (earlierDate.getTime() / HOUR_MILLIS));
	}
	
	public static long getYearsDiffrence(String lnClosedDate) {

		
		
		
		long yearsDiffrence = 0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = formatter.parse(lnClosedDate);
			Timestamp modDate = getCurrentDate();

			Timestamp ts = new Timestamp(date.getTime());

			yearsDiffrence = getDaysDifference(modDate, ts, "YEARS");
			System.out.println();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return yearsDiffrence;
	}

public static String getDateFormate(String datefromated) {
	String dateSplit[]= datefromated.split("-");
	
		String date = dateSplit[2];
		String mnth = dateSplit[1];
		String year = dateSplit[0];

		int month = Integer.parseInt(mnth);

		Locale localDate = new Locale("EN", "INDIA");

		DateFormat formatter = new SimpleDateFormat("MMM", localDate);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month - 1);

		String m = formatter.format(calendar.getTime());

		String finalDate = date + "-" + m + "-" + year;

		return finalDate;
	}
	
	
public static void main(String[] args) throws ParseException {
	
//	System.out.println(DateUtility.getCurrentDate());
	
	//System.out.println(DateUtility.getCurrentDate());

	String lnClosedDate = "2019-12-30";
	
	String timstamp="2019-12-30 15:21:56";
	
	String ar[]=timstamp.split(" ");
	
	
	String dateString=ar[0];
	System.out.println(dateString);
	
	
	DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	// you can change format of date
	java.util.Date date = (java.util.Date) formatter.parse(dateString);
	System.out.println(date);

}


	public static String getTodayDateAndTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh.mm a");
		LocalDateTime now = LocalDateTime.now();
		String rpDate = dtf.format(now);

		return rpDate;
	}



	public static int getRandomNumber() {

		Random rnd = new Random();
		int servideId = 5999 +rnd.nextInt(999);
		return servideId;
	}	
	
	
	public static String getToDayDate()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		return dtf.format(now);
	}
	
	public static String getDateWithoutSaparator() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		return dtf.format(now);
	}
	public static String getDateFormate2(String datefromated) {
		
		
		String  dateSplit[] = datefromated.split("/");
		
			String date = dateSplit[0];
			String mnth = dateSplit[1];
			String year = dateSplit[2];

			int month = Integer.parseInt(mnth);

			Locale localDate = new Locale("EN", "INDIA");

			DateFormat formatter = new SimpleDateFormat("MMM", localDate);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, month - 1);

			String m = formatter.format(calendar.getTime());

			String finalDate = date + "-" + m + "-" + year;

			return finalDate;
		}
	

}
