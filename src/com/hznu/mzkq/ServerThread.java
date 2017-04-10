package com.hznu.mzkq;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;

public class ServerThread extends Thread {

	// 与本线程相关的Socket
	Socket socket = null;
	private static final int DATA_LENGTH = 4;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	// 线程执行的操作，响应客户端的请求
	public void run() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			is = socket.getInputStream();
			// 字节流转字符流，提高传输效率
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			int data;
			String received = "";
			int count = 0;

			DataInputStream in = new DataInputStream(socket.getInputStream());

			while (socket.isConnected() && (data = in.read()) != 0) {
				count++;
				received += formatData(data);
				if (count == 4) {
					count = 0;
					System.out.println("接收到的卡号为：" + received);
					received = "";
				}
				// System.out.println("received---" + data);
			}

			if (!socket.isConnected()) {
				System.out.println("连接断掉");
			}
			System.out.println(received);
			socket.shutdownInput(); // 关闭输入流

			// 获取输出流，相应客户端的请求
			// os = socket.getOutputStream();
			// pw = new PrintWriter(os); // 包装为打印流
			// pw.write("认证失败");
			// pw.flush(); // 调用flush()方法将缓冲输出

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭相关的资源
			try {
				if (pw != null) {
					pw.close();
				}
				if (os != null) {
					os.close();
				}
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (is != null) {
					is.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 规范会数据 将接收到的数保持三位
	 * 
	 * @param data
	 * @return
	 */
	private String formatData(int data) {
		// if (data >= 0 && data < 10) {
		// return "00" + data;
		// } else if (data >= 10 && data < 100) {
		// return "0" + data;
		// }
		// return String.valueOf(data);
		String hexData = Integer.toHexString(data);
		int length = hexData.length();
		if (length == 1) {
			return "0" + hexData;
		}
		return hexData;
	}
}
