package com.icar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.icar.activity.R;
import com.icar.base.BaseApplication;


public class DateUtil {
	private final static SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 获取当前格式化日期
	 * 
	 * @return
	 */
	public static String getFormatDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 把视频的长度格式化为00:00这种格式
	 * 
	 * @param length
	 * @return
	 */
	public static String formatVideoLength(int length) {
		return BaseApplication
				.getInstance()
				.getResources()
				.getString(R.string.rcd_video_time,
						String.format("%02d", length));
	}

	/**
	 * 把音频的时间格式化为00:00这种格式
	 * 
	 * @param length
	 * @return
	 */
	public static String formatAudioLength(int time) {
		String strTime = time + "";
		switch (strTime.length()) {
		case 2:
			return "00:00";
		case 4:
			return "00:0" + (String) strTime.subSequence(0, 1);
		case 5:
			return "00:" + (String) strTime.subSequence(0, 2);
		default:
			return "00:00";
		}

		// if(strTime.length()<3){
		// return "00:01";
		// }else if(strTime.length()==4){
		// return "00:0"+(String) strTime.subSequence(0, 1);
		// }else{
		// return "00:"+(String) strTime.subSequence(0, 2);
		// }
	}

	/**
	 * 时间戳转换为日期
	 * 
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat(formats)
				.format(new java.util.Date(timestamp));
		return date;
	}

	/**
	 * 聊天日期显示
	 * 
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public static String ToChatTime(long time) {
		long nowtime = System.currentTimeMillis();
		long timeLong = nowtime - time;
		String time_str = "";
		long day = timeLong / 1000 / 60 / 60 / 24;
		long year = timeLong / 1000 / 60 / 60 / 24 / 12;
		if(day==0){
			time_str = ToRegTime(time, " HH:mm ");
		}else if(day==1){
			time_str = "昨天  "+ToRegTime(time, " HH:mm ");
		}else if(day>1&&year==0){
			time_str = ToRegTime(time, " MM-dd HH:mm ");
		}else{
			time_str = ToRegTime(time, " yyyy-MM-dd HH:mm ");
		}

		return time_str;
	}

	/**
	 * 聊天日期显示
	 * 
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public static String ToRegTime(long time, String reg) {
		Date date = new Date(time);
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat(reg);
		String time_str = format.format(date);
		return time_str;
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

}
