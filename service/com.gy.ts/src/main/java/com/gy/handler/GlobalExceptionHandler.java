package com.gy.handler;

import com.gy.enums.StatusEnum;
import com.gy.exception.SBCException;
import com.gy.domain.BaseResponse;
import com.gy.domain.NULLBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice  
@ResponseBody  
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public Object handleMethodArgumentNotValidException(Exception e) {

		BaseResponse<NULLBody> response = new BaseResponse<NULLBody>();
		response.setCode(StatusEnum.FAIL.getCode());
		if (e instanceof SBCException){
			response.setMessage(e.getMessage());

		} else {
			response.setMessage(StatusEnum.FAIL.getMessage());
		}
		return response ;
	}  

}  