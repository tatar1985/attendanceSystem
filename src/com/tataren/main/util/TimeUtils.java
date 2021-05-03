/* ========================================================================= *
 *                                                                           *
 *                 The QinSoft Ltd. Software License                         *
 *                                                                           *
 *         (C)Copyright 2006 QinSoft Ltd.                                    *
 *                    All rights reserved.                                   *
 *                                                                           *
 * ========================================================================= *
 *                                                                           *
 * THIS SOFTWARE IS PROVIDED   ``AS IS'' AND ANY EXPRESSED OR IMPLIED        *
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES         *
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE               *
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR          *
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,          *
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT          *
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF          *
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND       *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,        *
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT        *
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF        *
 * SUCH DAMAGE.                                                              *
 * ========================================================================= */
package com.tataren.main.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


public class TimeUtils {

	private static Logger logger = Logger.getLogger(TimeUtils.class);

	public static final String DAY_FORMAT_1 = "yyyy-MM-dd";

	public static final String DAY_FORMAT_2 = "yyyyMMdd";
	
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final long MILLS_PER_DAY = 1000 * 60 * 60 * 24;

	/**
	 * Get current date
	 * 
	 * @return java.util.Date
	 */
	public static java.util.Date getCurrentTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Get current year
	 * 
	 * @return int
	 */
	public static int getCurrentYear() {
		return getYear(getCurrentTime());
	}

	/**
	 * Get current month
	 * 
	 * @return int
	 */
	public static int getCurrentMonth() {

		return getMonth(getCurrentTime());
	}

	public static int getMonth(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.MONTH);
	}

	/**
	 * Get current minute
	 * 
	 * @return int
	 */
	public static int getCurrentMinute() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE);
	}

	/**
	 * Get current second
	 * 
	 * @return int
	 */
	public static int getCurrentSecond() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.SECOND);
	}

	/**
	 * Get current second
	 * 
	 * @return int
	 */
	public static int getCurrentMillSecond() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MILLISECOND);
	}

	/**
	 * Get current day in the month
	 * 
	 * @return int
	 */
	public static int getCurrentDay() {
		return getDay(TimeUtils.getCurrentTime());
	}

	public static int getDay(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Get current hour
	 * 
	 * @return int
	 */
	public static int getCurrentHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Construct the Date object according to the string and the specifed
	 * format.
	 * 
	 * @param dateValue
	 * @param dateFormat
	 * @return Date
	 */
	public static java.util.Date string2Date(String dateValue, String dateFormat) {
		return string2Date(dateValue, dateFormat, null);
	}

	/**
	 * Parse string to date according to the specified format,if exception
	 * occurs,return the specified default value
	 * 
	 * @param dateValue
	 * @param dateFormat
	 * @param defaultValue
	 * @return date
	 */
	public static java.util.Date string2Date(String dateValue, String dateFormat, Date defaultValue) {
		java.util.Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			date = sdf.parse(dateValue);
		} catch (Exception eDate) {
			return defaultValue;
		}
		return date;
	}

	/**
	 * Parse the given string into date.
	 * 
	 * @param dateValue
	 * @return Date
	 */
	public static Date string2Date(String dateValue) {
		if (dateValue == null)
			return null;
		if (dateValue.length() == DAY_FORMAT_1.length())
			return string2Date(dateValue, DAY_FORMAT_1);
		else if (dateValue.length() == TIME_FORMAT.length())
			return string2Date(dateValue, TIME_FORMAT);
		else
			return null;
	}

	/**
	 * Parse string to date according to the specified format,if exception
	 * occurs,return null
	 * 
	 * @param dateValue
	 * @param defaultValue
	 * @return
	 */
	public static Date string2Date(String dateValue, Date defaultValue) {
		return string2Date(dateValue, DAY_FORMAT_1, defaultValue);
	}

	/**
	 * Adds the specified (signed) amount of time to the date time field.
	 * 
	 * @param date
	 * @param days
	 * @return date
	 */
	public static Date addDate(Date date, int days) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}

	/**
	 * Adds the specified (signed) amount of time to the hour-of-day field.
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHour(Date date, int hours) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hours);
		return c.getTime();
	}

	/**
	 * Adds the specified (signed) amount of time to the minutes field.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinute(Date date, int minutes) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);
		return c.getTime();
	}

	/**
	 * Adds the specified (signed) amount of time to the seconds field.
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSecond(Date date, int seconds) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		return c.getTime();
	}

	/**
	 * Adds the specified (signed) amount of time to the date time field.
	 * 
	 * @param date
	 * @param days
	 * @return date
	 */
	public static Timestamp addDate(Timestamp date, int days) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Timestamp(c.getTime().getTime());
	}

	/**
	 * Adds the specified (signed) amount of time to the hour-of-day field.
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Timestamp addHour(Timestamp date, int hours) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hours);
		return new Timestamp(c.getTime().getTime());
	}

	/**
	 * Adds the specified (signed) amount of time to the minutes field.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Timestamp addMinute(Timestamp date, int minutes) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);
		return new Timestamp(c.getTime().getTime());
	}

	/**
	 * Adds the specified (signed) amount of time to the seconds field.
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Timestamp addSecond(Timestamp date, int seconds) {
		if (date == null)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		return new Timestamp(c.getTime().getTime());
	}

	/**
	 * Parse the give date time to string format with the default time format
	 * 
	 * @param dateValue
	 * @return string
	 */
	public static String date2String(Date dateValue) {
		return date2String(dateValue, DAY_FORMAT_1);
	}

	/**
	 * Parse the given time to string format
	 * 
	 * @param dateValue
	 * @param dateFormat
	 * @return string
	 */
	public static String date2String(java.util.Date dateValue, String dateFormat) {
		if (dateValue == null)
			return "";
		String sDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sDate = sdf.format(dateValue);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
		return sDate;
	}

	/**
	 * Returns the time when is the fist monday from now on. Hours,minutes,and
	 * seconds are all set to be zero.
	 * 
	 * @return time
	 */
	public static Calendar getFirstMonday() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return getFirstMonday(c);
	}

	/**
	 * Returns the time when is the fist monday from now on. Hours,minutes,and
	 * seconds are all set to be zero.
	 * 
	 * @return time
	 */
	public static Calendar getFirstMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getFirstMonday(c);
	}

	/**
	 * Returns the time when is the fist monday from the given time.
	 * Hours,minutes,and seconds are all set to be zero.
	 * 
	 * @return time
	 */
	public static Calendar getFirstMonday(Calendar c) {
		for (int dow = c.get(Calendar.DAY_OF_WEEK); dow != Calendar.MONDAY; dow = c
				.get(Calendar.DAY_OF_WEEK)) {
			c.add(Calendar.DATE, 1);
		}
		return c;
	}

	public static Date getFirstDayOfYear() {
		Calendar c = Calendar.getInstance();
		return getFirstDayOfYear(c.get(Calendar.YEAR));
	}

	private static Date getFirstDayOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * Format time by the default time.
	 * 
	 * @param time
	 * @return string
	 */
	public static String format(Date time) {
		return format(time, DAY_FORMAT_1);
	}

	/**
	 * Format time by the specified time.
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String format(Date time, String format) {
		if (time == null)
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(time);
		} catch (Exception eDate) {
			return time.toString();
		}
	}

	/**
	 * Returns year of the specified time.
	 * 
	 * @param time
	 * @return year
	 */
	public static int getYear(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.YEAR);
	}

	/**
	 * Returns true iff the specified time is <code>Sunday</code>
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isSunday(Date time) {
		if (time == null)
			throw new NullPointerException(" argument is null ");
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	/**
	 * Returns true iff the specified time is <code>Saturday</code>
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isSaturday(Date time) {
		if (time == null)
			throw new NullPointerException(" argument is null ");
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	public static Date getMonday4Sunday2Saturday(Date date) {
		if (date == null)
			throw new NullPointerException(" specified date is null");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			c.add(Calendar.DATE, 1);
		else
			while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
				c.add(Calendar.DATE, -1);

		return c.getTime();
	}

	/**
	 * Returns the time when is the monday of the current week.
	 * 
	 * @return Date
	 */
	public static Date getMondayOfWeek() {
		return getMondayOfWeek(getCurrentTime());
	}

	/**
	 * Returns the time when is the monday of the current week.
	 * 
	 * @param thisWeek
	 * @return Date
	 */
	public static Date getMondayOfWeek(Date thisWeek) {
		Calendar c = Calendar.getInstance();
		c.setTime(thisWeek);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
			c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	public static int differentDays(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.set(Calendar.HOUR, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		c2.set(Calendar.HOUR, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);

		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();

		return (int) ((l1 - l2) / MILLS_PER_DAY);
	}

	public static void main(String[] args) {
		java.util.Date date = TimeUtils.string2Date("20041222 12:12:22", "yyyyMMdd HH:mm:ss");
		// date = TimeUtils.getMonday4Sunday2Saturday(date);
		System.out.println(date + "  " + TimeUtils.date2String(date, TimeUtils.TIME_FORMAT));
		date = TimeUtils.getMonday4Sunday2Saturday(TimeUtils.string2Date("20041120 13:12",
				"yyyyMMdd hh:mm"));
		System.out.println(date + "  " + TimeUtils.date2String(date));
		date = TimeUtils.string2Date("20041222 13:12", "yyyyMMdd hh:mm");
		System.out.println(date + "  " + TimeUtils.date2String(date));
		date = TimeUtils.string2Date("200503012000", "yyyyMMddhhmm");
		System.out.println(date + "  " + TimeUtils.date2String(date));
		date = TimeUtils.string2Date("200503021630", "yyyyMMddhhmm");
		System.out.println(date + "  " + TimeUtils.date2String(date));
		date = TimeUtils.string2Date("13:29", "HH:mm");
		System.out.println(date + "  " + TimeUtils.date2String(date));

		System.out.println(TimeUtils.string2Date("2006-12-22", DAY_FORMAT_1));

		date = TimeUtils.string2Date("20041222 12:12:22", "yyyyMMdd HH:mm:ss");
		date = TimeUtils.getCurrentTime();
		java.util.Date date2 = TimeUtils.string2Date("20061209 00:00:00", "yyyyMMdd HH:mm:ss");
		System.out.println(date);
		System.out.println(date2);
		System.out.println(TimeUtils.differentDays(date, date2));
	}
}