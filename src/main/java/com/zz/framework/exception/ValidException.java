package com.zz.framework.exception;


import com.zz.framework.response.error.ErrorDto;
import com.zz.framework.response.error.FieldError;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验自定义异常
 *
 * @author yanjunhao
 * @date 2017年10月18日
 */
public class ValidException extends Exception {
    private String message;
    private ErrorDto errorDto;

    /**
     * 构建校验异常信息
     *
     * @param bindingResult BindingResult
     */
    public ValidException(BindingResult bindingResult)  {
        List<FieldError> fieldErrorList = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            Object value = fieldError.getRejectedValue();

            FieldError itemError = new FieldError();
            itemError.setFieldName(field);
            itemError.setFieldValue(value);
            itemError.setMessage(defaultMessage);

            fieldErrorList.add(itemError);
        });

        this.setMessage("ValidException ERROR");

        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMsg(this.getMessage());
        errorDto.setFieldErrors(fieldErrorList);
        this.setErrorDto(errorDto);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
