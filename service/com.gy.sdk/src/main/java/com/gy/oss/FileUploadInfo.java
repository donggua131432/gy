package com.gy.oss;

/**
 * @描述: 文件上传实体
 * @作者: DuKai
 * @创建时间: 2018/12/14 15:48
 * @版本号: V1.0
 */
public class FileUploadInfo {
	/**
	 * 对应阿里云 bucket
	 */
	private String bucket;
	/**
	 * 文件路径（文件目录 + 文件名）
	 */
	private String fileSrc;
	/**
	 * 文件字节流
	 */
	private byte[] fileByteArray;
	
	public FileUploadInfo(String bucket, String fileSrc, byte[] fileByteArray) {
		this.bucket = bucket;
		this.fileSrc = fileSrc;
		this.fileByteArray = fileByteArray;
	}
	
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getFileSrc() {
		return fileSrc;
	}
	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}
	public byte[] getFileByteArray() {
		return fileByteArray;
	}
	public void setFileByteArray(byte[] fileByteArray) {
		this.fileByteArray = fileByteArray;
	}
}
