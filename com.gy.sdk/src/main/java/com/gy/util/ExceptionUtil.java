package com.gy.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @描述: 获取异常堆栈信息工具类
 * @作者: DuKai
 * @创建时间: 2018/7/26 16:36
 * @版本号: V3.0
 */
public class ExceptionUtil {

	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			e.printStackTrace(pw);
			return sw.toString();
		} finally {
			if(pw != null) {
				pw.close();
				pw = null;
			}
		}
	}

}
