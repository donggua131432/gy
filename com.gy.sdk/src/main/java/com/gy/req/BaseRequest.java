package com.gy.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * Function:
 * @author crossoverJie
 * Date: 2017/6/7 下午11:28
 * @since JDK 1.8
 */
public class BaseRequest {

    /**
     * ApiModelProperty()用于方法，字段 表示对model属性的说明或者数据操作更改
     * value–字段说明
     * name–重写属性名字
     * dataType–重写属性类型
     * required–是否必填
     * example–举例说明
     * hidden–隐藏
     */


    @ApiModelProperty(required=false, value="唯一请求号", example = "1234567890")
    private String reqNo;

    @ApiModelProperty(required=false, value="当前请求的时间戳", example = "0")
    private int timeStamp;

    public BaseRequest() {
        this.setTimeStamp((int)(System.currentTimeMillis() / 1000));
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }


    @Override
    public String toString() {
        return "BaseRequest{" +
                "reqNo='" + reqNo + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
