package com.gotway.gotway.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64ImageUtil {

//	   public static void getUrlImgae(String imgUrl ,String filename) throws Exception {
//		   InputStream inStream=null;
//	       if(imgUrl.contains("https:")){
//	    	   inStream = HttpsUtil.httpsRequest(imgUrl, "GET", null);
//	       }else if(imgUrl.contains("http:")){
//	    	 //new一个URL对象
//		        URL url = new URL(imgUrl);
//		        //打开链接
//		        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//		        //设置请求方式为"GET"
//		        conn.setRequestMethod("GET");
//		        //超时响应时间为5秒
//		        conn.setConnectTimeout(5 * 1000);
//		        //通过输入流获取图片数据
//		        inStream = conn.getInputStream();
//	       }else {
//	    	   throw new RuntimeException("非网络图片，请传网络图片URL");
//	       }
//
//	        try {
//				//得到图片的二进制数据，以二进制封装得到数据，具有通用性
//				byte[] data = readInputStream(inStream);
//				//new一个文件对象用来保存图片，默认保存当前工程根目录
//				File imageFile = new File(filename);
//				//创建输出流
//				FileOutputStream outStream = new FileOutputStream(imageFile);
//				//写入数据
//				outStream.write(data);
//				//关闭输出流
//				outStream.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new RuntimeException("图片解析失败，请检查图片url对应的资源是否存在");
//			}
//	    }
	   public static byte[] readInputStream(InputStream inStream) throws Exception{  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        //创建一个Buffer字符串  
	        byte[] buffer = new byte[1024];  
	        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
	        int len = 0;  
	        //使用一个输入流从buffer里把数据读取出来  
	        while( (len=inStream.read(buffer)) != -1 ){  
	            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
	            outStream.write(buffer, 0, len);  
	        }  
	        //关闭输入流  
	        inStream.close();  
	        //把outStream里的数据写入内存  
	        return outStream.toByteArray();  
	    } 
	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: 
	 * @CreateTime: 
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	*/
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
				b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
}
