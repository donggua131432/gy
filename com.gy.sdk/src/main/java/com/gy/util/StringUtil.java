package com.gy.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 字符工具
 * 
 * @author Administrator
 *
 */
public class StringUtil extends StringUtils {
	
	private static Logger logger =LoggerFactory.getLogger(StringUtil.class);
	
	/** */
	public static final String PATTERN_434 = "434";

	
	/**
	 * urlEncode 编码模板数据
	 * 
	 * @param content
	 * @return
	 */
	public static String encode2String(Map<String, String> content) {
		String charset = "UTF-8";
		if (content == null)
			return null;
		Set<Entry<String, String>> entries = content.entrySet();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.hasValue(key) && StringUtil.hasValue(value)) {
				try {
					key = URLEncoder.encode("#" + key + "#", charset);
					value = URLEncoder.encode(value, charset);

					sb.append(key).append("=").append(value);
				} catch (UnsupportedEncodingException e) {
					logger.error("URL编码失败", e);
				}
				sb.append("&");
			}
		}

		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		}

		return null;
	}


	/**
	 * 判断字符串是否为空
	 *
	 * @param s
	 * @return boolean
	 */
	public static boolean hasValue(String s) {
		if (s != null && s.trim().length() > 0)
			return true;
		return false;
	}

	/**
	 * 字符串是否为空，包括blank
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str){
		return null != str && 0 != str.trim().length() ? false : true;
	}

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}

	public static boolean hasValueBeforeTrim(String s) {
		if (s != null && s.trim().length() > 0)
			return true;
		return false;
	}

	/**
	 * 根式化字符串为 如：pattern=434 src=1234567890 则格式化为 1234***7890
	 * 
	 * @param src
	 *            被格式化字符串长度不能小等于格式化后的总长度
	 * @param pattern
	 *            必须为3位数
	 * @return
	 */
	public static String format(String src, String pattern) {
		if(isEmpty(src)) {
			return "";
		}
		char[] chars = pattern.toCharArray();

		int s = Integer.parseInt(chars[0] + "");

		int m = Integer.parseInt(chars[1] + "");

		int e = Integer.parseInt(chars[2] + "");

		StringBuffer sb = new StringBuffer(src.substring(0, s));

		String es = src.substring(src.length() - e);

		while (m-- > 0) {
			sb.append("*");
		}

		sb.append(es);

		return sb.toString();
	}

	/**
	 * 将形如key=value&key=value的字符串转换为相应的Map对象
	 * 
	 * @param result
	 * @return
	 */
	public static Map<String, String> convertResultStringToMap(String result) {
		Map<String, String> map = null;
		try {

			if (hasValue(result)) {
				if (result.startsWith("{") && result.endsWith("}")) {
					result = result.substring(1, result.length() - 1);
				}
				map = parseQString(result);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 解析应答字符串，生成应答要素
	 * 
	 * @param str
	 *            需要解析的字符串
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(String str) throws UnsupportedEncodingException {

		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		boolean isOpen = false;// 值里有嵌套
		char openName = 0;
		if (len > 0) {
			for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
				curChar = str.charAt(i);// 取当前字符
				if (isKey) {// 如果当前生成的是key

					if (curChar == '=') {// 如果读取到=分隔符
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else {// 如果当前生成的是value
					if (isOpen) {
						if (curChar == openName) {
							isOpen = false;
						}

					} else {// 如果没开启嵌套
						if (curChar == '{') {// 如果碰到，就开启嵌套
							isOpen = true;
							openName = '}';
						}
						if (curChar == '[') {
							isOpen = true;
							openName = ']';
						}
					}
					if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					} else {
						temp.append(curChar);
					}
				}

			}
			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map)
			throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * 是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	private static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否包含中文
	 * 
	 * @param strName
	 * @return
	 */
	public static final boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static final char[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 生成随机数
	 * @param length 随机数长度 必须大于0
	 * @return
	 */
	public static String randomNumber(int length) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		while (length-- > 0) {
			int index = r.nextInt(NUMBERS.length - 1);
			sb.append(NUMBERS[index]);
		}
		return sb.toString();
	}

	/**
	 * 生成随机数包含字符和数字
	 * @param length 随机数长度 必须大于0
	 * @return
	 */
	public static String randomNumStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		while (length-- > 0) {
			int index = r.nextInt(chars.length - 1);
			sb.append(chars[index]);
		}
		return sb.toString();
	}

	private static final char[] WORDS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * 生成随机字符串
	 */

	public static String randomString(int length) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		while (length-- > 0) {

			if (length % 2 == 0) {
				int index = r.nextInt(WORDS.length - 1);
				sb.append(WORDS[index]);
			} else {
				int index = r.nextInt(NUMBERS.length - 1);
				sb.append(NUMBERS[index]);
			}
		}

		return sb.toString();
	}

	/**
	 * 生成UUID 无分隔符
	 * 
	 * @return
	 */
	public static String UUID() {
		String uuid = UUID.randomUUID().toString();

		return uuid.replace("-", "").toUpperCase();
	}

	public static char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 8位UUID
	 *
	 * @return
	 */
	public static String shortUUID() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 获取主机名称
	 * 
	 * @return
	 * @auther ssjie
	 */
	public static String getHostName() {
		InetAddress netAddress;
		try {
			netAddress = InetAddress.getLocalHost();
			return netAddress.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String radomString(int size) {
		String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String rev = "";
		Random f = new Random();
		for (int i = 0; i < size; i++) {
			rev += charList.charAt(Math.abs(f.nextInt()) % charList.length());
		}
		return rev;
	}

	/**
	 * 拼接数组
	 * 
	 * @param array
	 * @param separator
	 *            分隔符
	 * @param end
	 *            结束符
	 * @return
	 */
	public static String concatEntries(String[] array, String separator, String end) {
		if (array == null)
			return null;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				sb.append(separator);
			String c = array[i];
			sb.append(c == null ? "" : c);
		}

		if (end != null) {
			sb.append(end);
		}

		return sb.toString();
	}

	/**
	 * 拼接集合
	 * 
	 * @param array
	 * @param separator
	 *            分隔符
	 * @param end
	 *            结束符
	 * @return
	 */
	public static String concatEntries(Collection<String> array, String separator, String end) {
		String[] arr = new String[array.size()];
		array.toArray(arr);
		return concatEntries(arr, separator, end);
	}

	public static boolean hasValue(String[] array, String param) {
		if (null == array || !hasValue(param)) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(param)) {
				return true;
			}
		}

		return false;
	}

	public static String addSpace(String s, int size) {
		if (!hasValue(s)) {
			return null;
		}
		StringBuilder str = new StringBuilder(s.replace(" ", ""));

		int i = str.length() / size;
		int j = str.length() % size;

		for (int x = (j == 0 ? i - 1 : i); x > 0; x--) {
			str = str.insert(x * size, " ");
		}
		return str.toString();
	}

	/**
	 * 去掉四个字节的字符串
	 * 
	 * @param content
	 * @return
	 */
	public static String removeFourChar(String content) {
		byte[] conbyte = content.getBytes();
		for (int i = 0; i < conbyte.length; i++) {
			if ((conbyte[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					conbyte[i + j] = 0x30;
				}
				i += 3;
			}
		}
		content = new String(conbyte);
		return content.replaceAll("0000", "");
	}

	/**
	 * 
	 * @desc 将对象转化为String
	 * @param val
	 * @return
	 */
	public static String obj2String(Object val) {
		if (val != null) {
			return String.valueOf(val);
		}
		return EMPTY;
	}
	
	
	public static byte[] getString2Utf8(Object val) {
		 String str = obj2String(val);
		 if(isNotBlank(str))
		 {
			 try {
				return str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(ExceptionUtil.getStackTrace(e));
			}
		 }
		 return null;
	}



	public static String charBreviary(String str, int start, int end){
		if(str.length()<=end){
			return substring(str, start, end);
		}else{
			return substring(str, start, end)+"...";
		}
	}
}
