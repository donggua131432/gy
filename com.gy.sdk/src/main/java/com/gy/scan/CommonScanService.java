package com.gy.scan;

import com.gy.spi.SPI;

@SPI(isDefault=true,value="common")
public class CommonScanService implements CustomerScanService {

	@Override
	public String getMybatisScanPath() {
		return "classpath:mappers/*.xml";
	} 
}
