package com.netty.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * @Title: filterEmoji   
	 * @Description: 微信 表情  过滤 方法
	 * @param source
	 * @return: String      
	 */
	private static final Pattern EMOJI = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
			Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
	public static String filterEmoji(String source) {
	        if (source == null) {
	            return null;
	        }
	        Matcher emojiMatcher = EMOJI.matcher(source);
	        if (emojiMatcher.find()) {
	            source = emojiMatcher.replaceAll("*");
	            return source;
	        }
	        return source;
	    }
 
	

	/**
	 * 转换为字节数组
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str){
		if (str != null){
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 * @return
	 */
	public static String toString(byte[] bytes){
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 * @param str 验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs){
		if (str != null){
			for (String s : strs){
				if (str.equals(trim(s))){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *  判断 字符串 是否 数字。
	 * @param str
	 * @return 
	 */

	private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");
	public static Boolean isNumber(String str) {
		return PATTERN.matcher("assd").matches();
	}


	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld"
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld"
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld"
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}
	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c :str.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("")||obj==null)) {
			return true;
		} else if (obj instanceof Short && ((Short) obj).shortValue() == 0) {
			return true;
		} else if (obj instanceof Integer && ((Integer) obj).intValue() == 0) {
			return true;
		} else if (obj instanceof Double && ((Double) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Float && ((Float) obj).floatValue() == 0) {
			return true;
		} else if (obj instanceof Long && ((Long) obj).longValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection<?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}
	  	/**
	     * 元转分，乘100，保留0位小数，多余小数舍弃
	     * @param yuan
	     * @return
	     */
	    public static Integer yuanToFen(Object yuan){
	        BigDecimal b = new BigDecimal(yuan+"");
	        return b.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN).intValue();
	    }


	    /**
	     * 分转元，除100，保,2位小数，多余小数舍弃
	     * @param fen
	     * @return
	     */
	    public static Double fenToYuan(Object fen){
	        BigDecimal b = new BigDecimal(fen+"");
	        return b.divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN).doubleValue();
	    }
	    
	    /**
	     * 生成编号工具
	     * @return
	     */
		public static String getOrderNo(){
			String orderNo = "";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
			String nowTime = sdf.format(new Date());
			Random rand=new Random();//生成随机数
	        String cardNnumer="";
	        for(int a=0;a<10;a++){
	        	cardNnumer+=rand.nextInt(10);//生成6位数字
	        }
			orderNo+=nowTime+cardNnumer;
			return orderNo;
		}
		
		
		/**
	     * 获取一定长度的随机字符串
	     * @param length 指定字符串长度
	     * @return 一定长度的字符串
	     */
	    public static String getRandomStringByLength(int length) {
	        String base = "0123456789";
	        Random random = new Random();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int number = random.nextInt(base.length());
	            sb.append(base.charAt(number));
	        }
	        return sb.toString();
	    }
	    
	    public static void main(String[] args) {
	    	Map<String, String> map  =urlSplit("siteCode=123&userUid=sfs&eleCode=siels234&time=123456789012");
	    	System.out.println(map);
		}
	    
	    public static Map<String, String> urlSplit(String URL){
            Map<String, String> mapRequest = new HashMap<String, String>();
            String[] arrSplit=URL.split("[&]");
            for(String strSplit:arrSplit){
                  String[] arrSplitEqual=null;         
                  arrSplitEqual= strSplit.split("[=]");
                  //解析出键值
                  if(arrSplitEqual.length>1){
                      //正确解析
                      mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
                  }else{
                      if(arrSplitEqual[0]!=""){
                      //只有参数没有值，不加入
                      mapRequest.put(arrSplitEqual[0], "");       
                      }
                  }
            }   
            return mapRequest;   
        }

}
