package com.gy.spi;

import com.gy.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @描述: GySPIFactory
 * @作者: DuKai
 * @创建时间: 2018/7/26 16:36
 * @版本号: V3.0
 */
public class GySPIFactory {

	private static Logger logger = LoggerFactory.getLogger(GySPIFactory.class);

	private ConcurrentHashMap<String, GySPIList> cacheMap = null;

	private static class GySpiFactoryHolder {
		private static GySPIFactory instance = new GySPIFactory();
	}

	private GySPIFactory() {
		cacheMap = new ConcurrentHashMap<>();
	}

	/**
	 * 
	 * @return
	 */
	public static GySPIFactory getInstance() {
		return GySpiFactoryHolder.instance;
	}

	/**
	 * 
	 * @param interCls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getDefaultExtension(Class<?> interCls) {
		if (interCls == null || !interCls.isInterface()) {
			logger.error("interCls must a interface!");
			return null;
		}
		String interName = interCls.getName();
		if (this.cacheMap.containsKey(interName)) {
			return getDefaultExtension(this.cacheMap.get(interName));
		}
		GySPIList spiList = new GySPIList();
		ServiceLoader serviceLoader = ServiceLoader.load(interCls);
		Iterator iter = serviceLoader.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			SPI spi = obj.getClass().getAnnotation(SPI.class);
			if(spi==null)
			{
				spiList.addSPI(obj.getClass().getName(), false, obj);
			}
			else
			{
				spiList.addSPI(spi.value(), spi.isDefault(), obj);
			}
			
		}
		this.cacheMap.put(interName,spiList);
		return this.getDefaultExtension(spiList);
	}
	
	/**
	 * 
	 * @param interCls
	 * @param val
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getExtension(Class<?> interCls, String val) {
		if (interCls == null || !interCls.isInterface()) {
			logger.error("interCls must a interface!");
			return null;
		}
		String interName = interCls.getName();
		if (this.cacheMap.containsKey(interName)) {
			return getExtension(this.cacheMap.get(interName),val);
		}
		GySPIList spiList = new GySPIList();
		ServiceLoader serviceLoader = ServiceLoader.load(interCls);
		Iterator iter = serviceLoader.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			SPI spi = obj.getClass().getAnnotation(SPI.class);
			spiList.addSPI(spi.value(), spi.isDefault(), obj);
		}
		this.cacheMap.put(interName,spiList);
		return this.getExtension(spiList, val);
	}
	
	/**
	 * 
	 * @param list
	 * @param val
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T getExtension(GySPIList list,String val)
	{
		if(list!=null)
		{
			List<GySPIObj> spiList = list.getSPIList();
			if(spiList==null||spiList.isEmpty())
			{
				logger.error("not found spilist ");
				return null;
			} 
			for(GySPIObj obj:spiList)
			{
				 if(StringUtil.equals(obj.getValue(), val))
				 {
					 return (T)obj.getTarget();
				 } 
			}
			logger.info("find val:{} failed,use Default Object",val);
			return getDefaultExtension(list);
		}
		logger.error("GySPIList is null! ");
		return null;
	}
	
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T getDefaultExtension(GySPIList list)
	{
		if(list!=null)
		{
			List<GySPIObj> spiList = list.getSPIList();
			if(spiList==null||spiList.isEmpty())
			{
				logger.error("not found spilist ");
				return null;
			} 
			for(GySPIObj obj:spiList)
			{
				 if(obj.isDefault)
				 {
					 return (T)obj.getTarget();
				 } 
			}
			logger.error("Default Object is null,please check conf again!");
			return null;
		}
		logger.error("MhsSPIList is null! ");
		return null;
	}

	/**
	 * 
	 * @author Administrator
	 *
	 */
	private static class GySPIList implements java.io.Serializable {
		private List<GySPIObj> objs = null;

		public GySPIList() {
			objs = new ArrayList<>();
		}

		public void addSPI(GySPIObj obj) {
			if (obj != null) {
				objs.add(obj);
			}
		}

		public void addSPI(String val, boolean isDefault, Object target) {
			GySPIObj obj = new GySPIObj();
			obj.setDefault(isDefault);
			obj.setTarget(target);
			obj.setValue(val);
			this.addSPI(obj);
		}
		
		public List<GySPIObj> getSPIList() {
             return objs;
		}
	}

	/**
	 * 
	 * @author Administrator
	 *
	 */
	private static class GySPIObj implements java.io.Serializable {

		private boolean isDefault;

		private String value;

		private Object target;

		public boolean isDefault() {
			return isDefault;
		}

		public void setDefault(boolean isDefault) {
			this.isDefault = isDefault;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Object getTarget() {
			return target;
		}

		public void setTarget(Object target) {
			this.target = target;
		}

	}
}
