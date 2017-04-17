package com.hznu.mzkq.util;

public class TextUtils {

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !str.isEmpty()) {
			return false;
		}
		return true;
	}
}
