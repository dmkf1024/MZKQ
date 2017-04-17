package com.hznu.mzkq.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络工具 单例模式 created by 王杰
 */
public class NetUtils {

	private static NetUtils mInstance = null;

	private static final String ETHERNET = "en0";
	private static final String WIFI = "en1";
	private static final String LOCAL = "lo0";
	private static final String ETH1 = "eth1";

	private NetUtils() {
	}

	// IP地址
	private static Map<String, String> mIP = new HashMap();

	/**
	 * 实例化
	 * 
	 * @return
	 */
	public static NetUtils getInstance() {
		if (mInstance == null) {
			synchronized (NetUtils.class) {
				if (mInstance == null) {
					mInstance = new NetUtils();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 获取IP信息
	 * 
	 * @return
	 */
	public static void syncIP() {
		// 清除列表
		mIP.clear();

		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				String ipName = netInterface.getName();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						String ipAddress = ip.getHostAddress();
						if (!TextUtils.isEmpty(ipName)
								&& !TextUtils.isEmpty(ipAddress)) {
							mIP.put(ipName, ipAddress);
						}
					}
				}
			}
		} catch (SocketException e) {
			System.out
					.println("Somthing wrong while getting IP, see for details: "
							+ e.getMessage());
		}
	}

	/**
	 * 获取本机地址
	 * 
	 * @return
	 */
	public static String getLocalIP() {
		if (mIP.size() == 0)
			syncIP();
		return getIP(LOCAL);
	}

	/**
	 * 获取本机wifi ip
	 * 
	 * @return
	 */
	public static String getWifiIP() {
		if (mIP.size() == 0)
			syncIP();
		return getIP(WIFI);
	}
	
	public static String getEth1IP() {
		if (mIP.size() == 0) 
			syncIP();
		return getIP(ETH1);
	}

	/**
	 * 获取本机有线IP
	 * 
	 * @return
	 */
	public static String getEthernetIP() {
		if (mIP.size() == 0)
			syncIP();
		return getIP(ETHERNET);
	}

	/**
	 * 打印IP信息
	 */
	public static void printIPInfo() {
		String localIP = getLocalIP();
		String ethernetIP = getEthernetIP();
		String wifiIP = getWifiIP();
		String eth1IP = getEth1IP();

		if (!TextUtils.isEmpty(ethernetIP)) {
			System.out.println("有线网络IP: " + ethernetIP);
		}
		if (!TextUtils.isEmpty(wifiIP)) {
			System.out.println("无线网络IP: " + wifiIP);
		}
		if (!TextUtils.isEmpty(eth1IP)) {
			System.out.println("Eth1 IP: " + eth1IP);
		}
		
	}

	/**
	 * 获取IP地址
	 * 
	 * @param key
	 * @return
	 */
	private static String getIP(String key) {
		if (mIP.containsKey(key)) {
			return mIP.get(key);
		}
		return "";
	}

}
