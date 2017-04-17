package com.hznu.mzkq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurTime() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(new Date());
	}

}
