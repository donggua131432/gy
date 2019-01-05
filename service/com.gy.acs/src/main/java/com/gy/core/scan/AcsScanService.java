package com.gy.core.scan;

import com.gy.scan.CommonScanService;
import com.gy.spi.SPI;

@SPI(isDefault=true,value="GY_CUSTOM_SPI")
public class AcsScanService extends CommonScanService {
	@Override
	public String getMybatisScanPath() {
		return "classpath:mappers/*/*.xml";
	} 
}
