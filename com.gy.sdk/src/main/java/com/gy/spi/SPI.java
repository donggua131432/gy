package com.gy.spi;

import com.gy.util.StringUtil;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {

	String value()default StringUtil.EMPTY;
	
	boolean isDefault() default true;
}
