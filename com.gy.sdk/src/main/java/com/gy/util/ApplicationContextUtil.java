package com.gy.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


/**
 * @描述: Spring 上下文工具类
 * @作者: DuKai
 * @创建时间: 2018/7/26 16:36
 * @版本号: V3.0
 */
public class ApplicationContextUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext target) {
		applicationContext = target;
	}
	
	/**
	 * 获取bean实例
	 * @param clz 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clz) {
		return applicationContext.getBean(clz);
	}
	
	/**
	 * 获取bean实例
	 * @param beanName
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> clz) {
		return (T)applicationContext.getBean(beanName);
	}
	
	
	/**
	 * 
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBeanIgnoreEx(Class<T> clz) {
		try {
			return applicationContext.getBean(clz);
		} catch (Exception ex) {
			logger.error("NoSuchLocalBean:{};Ignore this error.",clz.getName());
			return null;
		} 
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBeanIgnoreEx(String beanName) {
		try {
			return (T) applicationContext.getBean(beanName);
		} catch (Exception ex) {
			logger.error("NoSuchLocalBean:{};mes:{}",beanName,ExceptionUtils.getStackTrace(ex));
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}
	
	
	/**
	 * 获取上下文
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
