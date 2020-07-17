package cn.xy.st.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 	@author xuyao
 * */
public class DateUtil {
	
	public static final String STR_DATE_PATTERN = "yyyy-MM-dd";
	public static final String STR_YM_PATTERN = "yyyy";
	
	public static String format(Date date, String pattern) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String formatNYR(Date date) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(STR_DATE_PATTERN);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
}
