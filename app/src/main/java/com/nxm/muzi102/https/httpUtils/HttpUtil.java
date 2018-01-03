package com.nxm.muzi102.https.httpUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
	/**
	 * 发送post请求--用于接口接收的参数为JSON字符串
	 * @param url 请求地址
	 * @param params json格式的字符串
	 * @return
	 */
	public static String httpPost(String url, String params){
		String result = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
            /*
             * 发送json字符串，这两句需要设置
             */
			httpPost.addHeader("Content-type","application/json; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");

			httpPost.setEntity(new StringEntity(params, "UTF-8"));

			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				// Read the response body
				result = EntityUtils.toString(response.getEntity(),"UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * http请求的get方式
	 *
	 * @param http
	 * @return
	 */
	public static String get(String http) {
		String result = null;
		try {
			URL url = new URL(http);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 200) {
				// 获取服务器端返回内容的输入流
				InputStream in = conn.getInputStream();
				// 创建内容输出流(作用是临时存储内容然后转为byte数组)
				ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int num;
				while ((num = in.read(buffer)) != -1) {
					dataOut.write(buffer, 0, num);
				}
				dataOut.flush();
				result = new String(dataOut.toByteArray());
				dataOut.close();
				in.close();
			}
			conn.disconnect();
		} catch (Exception e) {
e.getStackTrace();
		}
		return result;
	}

	/**
	 * http的post请求
	 *
	 * @param http 请求地址
	 * @param map 请求参数列表
	 * @return
	 */
	public static String post(String http, Map<String, String> map) {
		String result = null;
		try {
			URL url = new URL(http);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(30000);
			// 设置请求方式为post
			conn.setRequestMethod("POST");
			conn.connect();
			if (map != null) {
				// 提交参数
				StringBuffer sb = new StringBuffer();
				Iterator<Entry<String, String>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
				}
				// 将最后一个&去掉
				sb.delete(sb.length() - 1, sb.length());
				String par = sb.toString();
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), "UTF-8");
				out.write(par);
				out.flush();
				out.close();
			}
			// 判断是否连接成功
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int num;
				while ((num = in.read(buffer)) != -1) {
					dataOut.write(buffer, 0, num);
				}
				dataOut.flush();
				result = new String(dataOut.toByteArray());
				in.close();
				dataOut.close();
			}
			conn.disconnect();
		} catch (Exception e) {
		e.getStackTrace();
		}
		return result;
	}

	public static String post(String actionUrl, Map<String, String> params,
							  Map<String, File> files) {
		return post(actionUrl, params, files, null);
	}

	// 上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件
	public static String post(String actionUrl, Map<String, String> params,
							  Map<String, File> files, OnUploadListener l) {
		if (null == files) {
			return post(actionUrl, params);
		}
		String result = null;
		try {
			// 将内容以二进制流形式提交到服务器(需要服务器单独解析)
			String BOUNDARY = java.util.UUID.randomUUID().toString();
			String PREFIX = "--", LINEND = "\r\n";
			String MULTIPART_FROM_DATA = "multipart/form-data";
			String CHARSET = "UTF-8";
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			// 读取超时的设置
			conn.setReadTimeout(5 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);
			// 组装文本类型参数列表
			StringBuilder sb = new StringBuilder();
			if (null != params) {
				for (Entry<String, String> entry : params.entrySet()) {
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINEND);
					// 传递文本参数的key部分
					sb.append("Content-Disposition: form-data; name=\""
							+ entry.getKey() + "\"" + LINEND);
					sb.append("Content-Type: text/plain; charset=" + CHARSET
							+ LINEND);
					sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
					sb.append(LINEND);
					// 传递文本参数的value部分
					sb.append(entry.getValue());
					sb.append(LINEND);
				}
			}
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(sb.toString().getBytes());
			// 发送文件数据
			if (files != null) {
				int totalSize = 0;
				int currentSize = 0;
				for (Entry<String, File> file : files.entrySet()) {
					totalSize += file.getValue().length();
				}
				for (Entry<String, File> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
							+ URLEncoder.encode(file.getKey(), "utf-8") + "\"" + LINEND);
					sb1.append("Content-Type: multipart/form-data; charset="
							+ CHARSET + LINEND);
					sb1.append(LINEND);
					outStream.write(sb1.toString().getBytes());
					InputStream is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
						// 上传进度的计算
						currentSize += len;
						if (null != l) {
							l.onProgress(currentSize, totalSize);
						}
					}
					is.close();
					outStream.write(LINEND.getBytes());
				}
			}
			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 判断是否连接成功
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int num;
				while ((num = in.read(buffer)) != -1) {
					dataOut.write(buffer, 0, num);
				}
				dataOut.flush();
				result = new String(dataOut.toByteArray());
				in.close();
				dataOut.close();
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
//	/***
//	 *
//	 * @param fileName
//	 *            1.jpg
//	 * @return
//	 */
//	public static boolean download(String fileName,String userId, long from, long to) { // 1024-456789
//		boolean result = false;
//		long lenght = 0;
//		try {
//			URL url = new URL(
//					Common.DOWNLOAD_URL+ "?userId="+userId+"&file_name="
//							+ URLEncoder.encode(fileName, "utf-8"));
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			if (to != 0) {
//				// 设置请求属性(HTTP协议的头部信息)
//				conn.setRequestProperty("Range", "bytes=" + from + "-" + to);
//			} else {
//				conn.setRequestProperty("Range", "bytes=" + from + "-");
//			}
//			// 获取内容总大小
//			lenght = conn.getContentLength();
//			Log.e("m_tag", "code=" + conn.getResponseCode());
//			if (conn.getResponseCode() == 200 || conn.getResponseCode() == 206) {
//				result = true;
//				InputStream in = conn.getInputStream();
//				byte[] buffer = new byte[1024];
//				int num;
//				// FileOutputStream out = new FileOutputStream("/mnt/sdcard/"
//				// + fileName);
//				// 随机存储的文件对象
//				RandomAccessFile rs = new RandomAccessFile("/mnt/sdcard/"
//						+ fileName, "rw");
//				// 将操作游标指定到一个具体位置
//				rs.seek(from);
//				int count = 0;
//				while ((num = in.read(buffer)) != -1) {
//					rs.write(buffer, 0, num);
//					count += num;
//				}
//				Log.e("m_tag", "count=" + count);
//				rs.close();
//				in.close();
//			}
//			conn.disconnect();
//		} catch (Exception e) {
//		}
//		File f = new File("/mnt/sdcard/" + fileName);
//		if (lenght > 0 && lenght == f.length()) {
//			Log.e("22222222222","585555");
//
//		}
//		return result;
//	}


	public interface OnUploadListener {
		void onProgress(int current, int total);
	}

}
