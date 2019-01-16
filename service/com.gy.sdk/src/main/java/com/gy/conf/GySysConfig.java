package com.gy.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.io.Serializable;

/**
 * @描述: 系统配置
 * @作者: DuKai
 * @创建时间: 2018/12/14 18:12
 * @版本号: V1.0
 */

@ConfigurationProperties(prefix = "gysysconfig")
public class GySysConfig implements Serializable {
    private static final long serialVersionUID = -5753852835196005165L;

    private String bucket;//阿里云BUCKET名称
    private String filePath; //存放文件根目录


    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
