package com.hznu.mzkq.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtils {
	
	private static DBUtils mInstance = null;
	
	private static String USERNAME = "admin";
	private static String PASSWORD = "admin123";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jabc:mysql://localhost:3306/kaoqin?useUnicode=true"
			+ "&autoReconnect=true&characterEncoding=UTF-8";
	
	private static PreparedStatement mStatement;
	
	private static ResultSet mResultSet;
	
	private static Connection mConn;
	
//	private DBUtils() {}
	
	/**
	 * 实例化
	 * @return
	 */
//	public static DBUtils getInstance() {
//		if (mInstance == null) {
//			synchronized (DBUtils.class) {
//				if (mInstance == null) {
//					mInstance = new DBUtils();
//				}
//			}
//		}
//		connect();
//		return mInstance;
//	}
	
	/**
	 * 连接数据库
	 * 
	 * @return 数据库连接
	 */
	public static Connection connect() {
		try {
			Class.forName(DRIVER);
			mConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			throw new RuntimeException("get connection error!", e);
		}
		return mConn;
	}
	
	/**
	 * 插入
	 * @param courseId
	 * @param cardNum
	 */
	public static int insert(String courseId, String cardNum) {
		
		int result = 0;
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datetime = df.format(new Date());
			String date = datetime.substring(0, datetime.indexOf(" "));
			String time = datetime.substring(datetime.indexOf(" ") + 1);
			String sql = "INSERT INTO arrival (course_id, card_num, arrival_date, arrival_time)"
					+ " VALUES('?', '?', '?', '?')";
			
			mStatement.setString(1, courseId);
			mStatement.setString(2, cardNum);
			mStatement.setString(3, date);
			mStatement.setString(3, time);
			
			result = mStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("insert error!", e);
		} finally {
			try {
				mStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	public static void releaseConnection() {
		if (mResultSet != null) {
			try {
				mResultSet.close();
				mResultSet = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (mStatement != null) {
			try {
				mStatement.close();
				mStatement = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (mConn != null) {
			try {
				mConn.close();
				mConn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
