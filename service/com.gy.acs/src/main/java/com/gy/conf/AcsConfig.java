package com.gy.conf;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: Acs系统配置
 * @作者: DuKai
 * @创建时间: 2018/11/16 20:04
 * @版本号: V1.0
 */
@Configuration
public class AcsConfig extends WebMvcConfigurationSupport {

    /**
     * 跨域处理
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //表示所有的请求路径都经过跨域处理
                .allowedOrigins("*")
                /*.allowedMethods("*")
                .allowedHeaders("*")*/
                .allowedMethods("POST", "GET", "DELETE", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Content-Length", "Accept", "X-Requested-With", "remember-me",
                        "auth", "Cookie", "Authorization", "AppId")
                //允许在请求头里存放信息,后端通过请求头来获取前端传来的信息
				.exposedHeaders("Authorization", "AppId")
                //设置是否允许跨域传cookie
				.allowCredentials(true)
                .maxAge(3600);
        super.addCorsMappings(registry);
    }


    /**
     * 对静态资源的配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * json返回中文乱码处理
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        //定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //添加fastjson的配置信息，比如：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //将为null的字段值显示为""
        //DisableCircularReferenceDetect:消除循环引用
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat,
                //SerializerFeature.WriteNullListAsEmpty, //当集合为空的时候也返回空值
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteNonStringKeyAsString);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //解决Long转json精度丢失的问题
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        //处理中文乱码问题(不然出现中文乱码)
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        //将convert添加到converters当中
        converters.add(fastConverter);
    }

}
