package com.gy.datasource;

/**
 * 
 * @author Administrator
 *
 */
public class SubJdbcConfig implements java.io.Serializable {

	private String name;

	private String url;

	private String uniqueResourceName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUniqueResourceName() {
		return uniqueResourceName;
	}

	public void setUniqueResourceName(String uniqueResourceName) {
		this.uniqueResourceName = uniqueResourceName;
	}
}
