package com.zz.framework.global;

import com.zz.framework.exception.ValidException;
import com.zz.framework.response.error.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author yanjunhao
 * @date 2017年10月18日
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 添加全局异常处理流程，根据需要设置需要处理的异常，此处以捕获Exception为例子
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws Exception {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMsg(e.getMessage());
        if(logger.isDebugEnabled()){
            logger.error("exceptionHandler error...",e);
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ResponseBody
    @ExceptionHandler(value = ValidException.class)
    public ResponseEntity<ErrorDto> validExceptionHandler(HttpServletRequest request, HttpServletResponse response, ValidException e)
            throws Exception {
        if(logger.isDebugEnabled()){
            logger.error("validExceptionHandler error...",e);
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getErrorDto());
    }

}
