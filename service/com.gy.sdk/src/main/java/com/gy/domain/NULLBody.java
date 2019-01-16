package com.gy.domain;

/**
 * @描述: 空对象,用在泛型中,表示没有额外的请求参数或者返回参数
 * @作者: DuKai
 * @创建时间: 2018/11/20 14:18
 * @版本号: V1.0
 */
public class NULLBody {
    public NULLBody() {}

    public static NULLBody create(){
        return new NULLBody();
    }
}
