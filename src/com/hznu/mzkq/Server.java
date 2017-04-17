package com.hznu.mzkq;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.hznu.mzkq.util.NetUtils;
import com.hznu.mzkq.util.TimeUtils;

/**
 * 基于TCP协议的Socket通信，实现用户通信 服务端
 * 
 * @author eric
 *
 */
public class Server {

	/**
	 * ip: 192.168.1.54 port: 8181
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 创建一个服务端Socket，即ServerSocket，指定绑定的端口，并监听次端口
			ServerSocket serverSocket = new ServerSocket(8989);
			Socket socket = null;

			// 记录客户端的数量
			int count = 0;
			NetUtils.printIPInfo();
			System.out.println("服务器即将启动，等待客户端连接。。。");
			// 循环监听等待客户端的连接
			while (true) {
				socket = serverSocket.accept();
				if (socket != null) {
					// 创建一个新的线程
					ServerThread serverThread = new ServerThread(socket);
					serverThread.start();
					count++; // 统计客户端的数量
					System.out.println("客户端的数量为：" + count);
					InetAddress address = socket.getInetAddress();
					System.out.println("当前客户端地址：" + address.getHostAddress());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
