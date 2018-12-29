package com.gy.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 公共字段
 * @作者: DuKai
 * @创建时间: 2018/7/30 15:56
 * @版本号: V3.0
 */
@Data
public class PubInfo implements Serializable {

	private static final long serialVersionUID = -1742320123066664497L;

	private String createUserId;
	private String operUserId;
	private String createUserName;
	private String operUserNmae;
	

}
