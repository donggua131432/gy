package com.gy.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeUtil {

	private static final Logger log = LoggerFactory.getLogger(CodeUtil.class);

	private static final String ENCODING = "UTF-8";

	private static final String KEY = "@mxd2016^0620)8(";

	private static final String ALGORITHM_SHA1 = "SHA-1";

	/**
	 * BASE64编码
	 * 
	 * @param info
	 * @return
	 */
	public static String base64Encode2String(byte[] info) {
		return Base64.getEncoder().encodeToString(info);
	}
	
	public static String base64Encode2String(String info) {
		try {
			byte[] b = info.getBytes(ENCODING);
			return Base64.getEncoder().encodeToString(b);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public static byte[] base64Encode(byte[] info) {
		return Base64.getEncoder().encode(info);
	}

	public static byte[] base64Decode(byte[] info) {
		return Base64.getDecoder().decode(info);
	}
	
	public static String base64Decode2String(byte[] info) {
		byte[] b = Base64.getDecoder().decode(info);
		
		try {
			return new String(b, ENCODING);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static String base64Decode2String(String str) {
		if(StringUtil.isEmpty(str))
			return null;
		
		try {
			byte[] b = str.getBytes(ENCODING);
			return base64Decode2String(b);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		
	}

	/**
	 * BASE64解码 *
	 * @param info
	 * @return
	 * @throws IOException
	 */

	public static byte[] base64Decode(String info) {
		return Base64.getDecoder().decode(info);
	}

	public static class SHA {
		public static String sha256(final String strText) {
			return sha(strText, "SHA-256");
		}

		/**
		 * 传入文本内容，返回 SHA-512 串
		 * 
		 * @param strText
		 * @return
		 */
		public static String sha512(final String strText) {
			return sha(strText, "SHA-512");
		}

		/**
		 * 字符串 SHA 加密
		 * @return
		 */
		private static String sha(final String strText, final String strType) {
			// 返回值
			String strResult = null;

			// 是否是有效字符串
			if (strText != null && strText.length() > 0) {
				try {
					// SHA 加密开始
					// 创建加密对象 并傳入加密類型
					MessageDigest messageDigest = MessageDigest.getInstance(strType);
					// 传入要加密的字符串
					messageDigest.update(strText.getBytes());
					// 得到 byte 類型结果
					byte byteBuffer[] = messageDigest.digest();

					// 將 byte 轉換爲 string
					StringBuffer strHexString = new StringBuffer();
					// 遍歷 byte buffer
					for (int i = 0; i < byteBuffer.length; i++) {
						String hex = Integer.toHexString(0xff & byteBuffer[i]);
						if (hex.length() == 1) {
							strHexString.append('0');
						}
						strHexString.append(hex);
					}
					// 得到返回結果
					strResult = strHexString.toString();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			return strResult;
		}

		/**
		 * sha1计算后进行16进制转换
		 * 
		 * @param data
		 *            待计算的数据
		 * @param encoding
		 *            编码
		 * @return 计算结果
		 */
		public static byte[] sha1X16(String data, String encoding) {
			try {
				byte[] bytes = sha1(data, encoding);
				StringBuilder sha1StrBuff = new StringBuilder();
				for (int i = 0; i < bytes.length; i++) {
					if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
						sha1StrBuff.append("0").append(Integer.toHexString(0xFF & bytes[i]));
					} else {
						sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
					}
				}
				return sha1StrBuff.toString().getBytes(encoding);
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}

		/**
		 * sha1计算
		 * 
		 * @param datas
		 *            待计算的数据
		 * @param encoding
		 *            字符集编码
		 * @return
		 * @throws UnsupportedEncodingException
		 * @throws NoSuchAlgorithmException
		 */
		public static byte[] sha1(String datas, String encoding)
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
			return sha1(datas.getBytes(encoding));
		}

		/**
		 * sha1计算.
		 * @param data 待计算的数据
		 * @return 计算结果
		 * @throws NoSuchAlgorithmException
		 */
		public static byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA1);
			md.reset();
			md.update(data);
			return md.digest();
		}
	}

	public static class DES {

		private static byte[] a(byte[] src, byte[] key, int mode) throws Exception {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, securekey, sr);
			return cipher.doFinal(src);
		}

		/**
		 * DES加密
		 * 
		 * @param src
		 * @param key
		 * @return
		 * @throws Exception
		 */
		private static byte[] e(byte[] src, byte[] key) throws Exception {
			return a(src, key, Cipher.ENCRYPT_MODE);
		}

		/**
		 * DES解密
		 * 
		 * @param src
		 * @param key
		 * @return
		 * @throws Exception
		 */
		private static byte[] d(byte[] src, byte[] key) throws Exception {
			return a(src, key, Cipher.DECRYPT_MODE);
		}

		/**
		 * 先对消息体进行DES编码再进行BASE64编码
		 * 
		 * @param info
		 * @return
		 */
		public static String encrypt(String info) {
			return encrypt(info, KEY);
		}

		/**
		 * 先对消息体进行DES编码再进行BASE64编码
		 * 
		 * @param info
		 * @return
		 */
		public static String encrypt(String info, String key) {
			try {
				byte[] temp = e(info.getBytes(ENCODING), key.getBytes(ENCODING));
				return base64Encode2String(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * 先对消息体进行BASE64解码再进行DES解码
		 * 
		 * @param info
		 * @return
		 * @throws Exception
		 * @throws UnsupportedEncodingException
		 */
		public static String decrypt(String info) throws UnsupportedEncodingException, Exception {
			return decrypt(info, KEY);
		}

		public static String decrypt(String info, String key) throws UnsupportedEncodingException, Exception {
			byte[] temp = base64Decode(info);
			byte[] buf = d(temp, key.getBytes(ENCODING));
			return new String(buf, ENCODING);
		}
	}
	/**
	 * 3DES 加密类型
	 * 
	 * @author cyf
	 *
	 */
	public static class DES3 {

		private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";
		private static final String ALGORITM = "DESede";

		/**
		 * 加密
		 * 
		 * @param toEncode
		 *            待加密字符串
		 * @param key
		 *            密钥
		 * @return
		 * @throws Exception
		 */
		public static String encrypt(String toEncode, String key) throws Exception {
			return encrypt(toEncode, key, null);
		}

		/**
		 * 加密
		 * 
		 * @param toEncode
		 *            待加密字符串
		 * @param key
		 *            密钥
		 * @param vector
		 *            自定义字符串
		 * @return
		 * @throws Exception
		 */
		public static String encrypt(String toEncode, String key, String vector) throws Exception {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			System.out.println(key.getBytes().length);
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITM);
			SecretKey securekey = keyFactory.generateSecret(dks);
			if (StringUtil.hasValue(vector)) {
				IvParameterSpec iv = new IvParameterSpec(vector.getBytes(), 0, cipher.getBlockSize());
				cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, securekey);
			}
			byte[] encoded = cipher.doFinal(toEncode.getBytes(ENCODING));
			return base64Encode2String(encoded);
		}

		/**
		 * 解密
		 * 
		 * @param toDecode
		 *            待解密字符串
		 * @param key
		 *            密钥
		 * @return
		 * @throws Exception
		 */
		public static String decrypt(String toDecode, String key) throws Exception {
			return decrypt(toDecode, key, null);
		}

		/**
		 * 解密
		 * 
		 * @param toDecode
		 *            待解密字符串
		 * @param key
		 *            密钥
		 * @param vector
		 *            自定义随机数
		 * @return
		 * @throws Exception
		 */
		public static String decrypt(String toDecode, String key, String vector) throws Exception {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);

			if (StringUtil.hasValue(vector)) {
				IvParameterSpec iv = new IvParameterSpec(vector.getBytes(), 0, cipher.getBlockSize());
				cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, securekey);
			}

			byte[] todecodeBytes = base64Decode(toDecode);
			String decoded = new String(cipher.doFinal(todecodeBytes), ENCODING);
			return decoded;
		}
	}

	public static class RSA {
		public static String sign(String datas, String privates) throws Exception {
			byte[] data = datas.getBytes();
			byte[] privateKey = base64Decode(privates);

			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

			Signature signature = Signature.getInstance("MD5withRSA");

			signature.initSign(priKey);

			signature.update(data);
			return base64Encode2String(signature.sign());
		}

		public static boolean verify(String datas, String publicKeys, String signs) throws Exception {
			byte[] data = datas.getBytes();
			byte[] publicKey = base64Decode(publicKeys);
			byte[] sign = base64Decode(signs);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);

			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

			Signature signature = Signature.getInstance("MD5withRSA");

			signature.initVerify(pubKey);

			signature.update(data);

			return signature.verify(sign);
		}
	}

}
