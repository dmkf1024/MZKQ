package com.hznu.mzkq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 基于TCP协议的Socket通信，实现用户通信
 * 客户端
 * @author eric
 *
 */
public class Client {

	public static void main(String[] args) {
		// 创建客户端Socket
		try {
			Socket socket = new Socket("192.168.1.18", 8888);
			OutputStream os = socket.getOutputStream(); // 字节输出流
			PrintWriter pw = new PrintWriter(os); // 将输出流包装为打印流
			pw.write("用户名：scott;密码：123");
			pw.flush();
			socket.shutdownOutput(); // 关闭输出流
			
			// 获取输入流， 用来读取服务器端的响应信息
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String info = null;
			while ((info = br.readLine()) != null) {
				System.out.println("我是客户端，服务器说：" + info);
			}
			
			
			// 关闭资源
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
