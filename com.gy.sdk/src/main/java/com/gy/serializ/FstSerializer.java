package com.gy.serializ;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @描述: FstSerializer 序列化工具类
 * @作者: DuKai
 * @创建时间: 2018/11/15 16:36
 * @版本号: V1.0
 */
public class FstSerializer {

	private static final Logger logger = LoggerFactory.getLogger(FstSerializer.class);
	private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration().setForceSerializable(false);

	private static FstSerializer instance = null;

	/**
	 * 
	 * @return
	 */
	public static FstSerializer getInstance() {
		if (instance == null) {
			synchronized (FstSerializer.class) {
				if (instance == null) {
					instance = new FstSerializer();
				}
			}
		}
		return instance;
	}

	private FstSerializer() {
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public byte[] serialize(Object object) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		FSTObjectOutput out = this.conf.getObjectOutput(stream);
		try {
			out.writeObject(object);
			out.flush();
			stream.close();
		} catch (IOException e) {
			logger.error("serialize error", e);
		}
		return stream.toByteArray();
	}

	/**
	 * 
	 * @param arr
	 * @param cls
	 * @return
	 */
	public <T> T deserialize(byte[] arr, Class<?> cls) {
		T result = null;
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(arr);
			FSTObjectInput in = this.conf.getObjectInput(stream);
			result = (T) in.readObject();
			stream.close();
		} catch (Exception e) {
			logger.error("deserialize error", e);
		}
		return result;
	}


}
