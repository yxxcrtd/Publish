package com.chinaedustar.publish.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ValidCodeImage {
	public final static String VALID_CODE_NAME = "__ValidCodeName";
	private String strValidCode = "";
	
	public ValidCodeImage() {

	}
	
	/**
	 *  给定范围获得随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 输出图片
	 * @param response
	 */
	public void outputImage(HttpServletResponse response, HttpSession session) {
		try {
			response.setContentType("image/jpg;charset=GB2312");//设定输出的类型
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
         	encoder.encode(getBufferedImage(getContent(), 56, 18));//对图片进行输出编码 
         	session.setAttribute(VALID_CODE_NAME, strValidCode);
       }catch(Exception e){
    	   e.printStackTrace();
       }
	}
	/**
	 * 检查验证码是否正确
	 * @param session
	 * @param code
	 * @return
	 */
	public static boolean checkValidCode(HttpSession session, String code) {
		String sessionCode = (String)session.getAttribute(VALID_CODE_NAME);
		if (sessionCode != null && sessionCode.equalsIgnoreCase(code)){
			return true;
		} 
		return false;
	}

	/**
	 * 创建图像 格式为jpg类型
	 * 
	 * @param content 图片输出内容
	 * @param width  图片宽度
	 * @param height 图片高度
	 */
	private BufferedImage getBufferedImage(String content, int width, int height) {
		// 在内存中创建图象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics2D g = image.createGraphics();
		// Graphics g=image.getGraphics();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(Color.GREEN);// 黑色文字
		g.drawString(content, 5, 14);
		g.dispose();
		return image;
	}

	/**
	 * 返回一个4位的验证码
	 */
	private String getContent() throws InterruptedException {
		String content = "";
		for (int i = 0; i < 4; i++) {
			content += getChar();
			Thread.sleep(new Random().nextInt(10) + 10);// 休眠以控制字符的重复问题
		}
		strValidCode = content;
		return content;
	}

	/**
	 * 获取随机字符
	 */
	private char getChar() {
		Random random = new Random();
		char ch = '0';
		LinkedList ls = new LinkedList();
		for (int i = 0; i < 10; i++) {// 0-9
			ls.add(String.valueOf(48 + i));
		}
		for (int i = 0; i < 26; i++) {// A-Z
			ls.add(String.valueOf(65 + i));
		}
		for (int i = 0; i < 26; i++) {// a-z
			ls.add(String.valueOf(97 + i));
		}
		int index = random.nextInt(ls.size());
		if (index > (ls.size() - 1)) {
			index = ls.size() - 1;
		}
		ch = (char) Integer.parseInt(String.valueOf(ls.get(index)));
		return ch;
	}

	
}
