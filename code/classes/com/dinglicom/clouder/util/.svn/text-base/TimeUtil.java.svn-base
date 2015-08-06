package com.dinglicom.clouder.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间转换公共方法
 * 
 */
public final class TimeUtil {
	/**
	 * 
	 */
	private static final int MILL_CONST = 1000;
	/**
	 * 
	 */
	public static final int NANO_CONST = 1000000000;
	/**
	 * 时间格式
	 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 长整型(秒)时间转换成字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param time(s)　长整型(秒)时间
	 * @return
	 */
	public static String long2SecondTime(long time) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTimeZone(TimeZone.getDefault());
		c.setTimeInMillis(time * MILL_CONST);
		String dateStr = df.format(c.getTime());
		return dateStr;
	}

	/**
	 * 字符串(yyyy-MM-dd HH:mm:ss)时间转换成长整型(秒)
	 * 
	 * @param dateStr 字符串时间(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static long secondTime2long(String dateStr) {
		Calendar c = Calendar.getInstance();
		c.clear();
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			return 0;
		}
		c.setTimeZone(TimeZone.getDefault());
		c.setTime(date);
		return c.getTimeInMillis() / MILL_CONST;
	}

	/**
	 * 时间输入格式为:yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @param nano
	 * @return
	 * @throws ParseException 
	 */
	/*public static long nanoTime2long(String time, long nano) throws ParseException
	{
		Date date = Time.parse_ts(time);
		
		return date.getTime()/MILL_CONST * NANO_CONST + nano;
	}*/
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public static String long2NanoTime(long time)
	{
		String secondTime = long2SecondTime(time/NANO_CONST);
		return secondTime +"."+ time%NANO_CONST;
	}
	
	/**
	 * 获取当前纳秒时间
	 * @return
	 */
	/*public static long currentNanoTime()
	{
		try {
			long currentTimeMillis = System.currentTimeMillis();
			return nanoTime2long(long2SecondTime(currentTimeMillis/1000), currentTimeMillis%1000);
		} catch (ParseException e) {
			return 0;
		}
	}*/
	
	public static void main(String[] args) throws ParseException {
//		System.out.println(nanoTime2long("2013-11-07 07:27:11", 706946426));
		System.out.println(long2NanoTime(1363649231706946426L));
		System.out.println(long2NanoTime(Long.parseLong("1347382836320858936")));
		System.out.println(long2SecondTime(System.currentTimeMillis()/1000));
		long timeInMill = System.currentTimeMillis();
		System.out.println(timeInMill);
		Timestamp time = new Timestamp(1363649231L*1000 + 500);
		time.setNanos(706946426);
		System.out.println("wx toString :" + time.toString());
		System.out.println("wx nano :" + time.getNanos());
		
//		System.out.println("wx current nano : " + currentNanoTime());
//		System.out.println(long2NanoTime(currentNanoTime()));
	}
}
