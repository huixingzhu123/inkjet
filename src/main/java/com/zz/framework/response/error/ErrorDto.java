package com.zz.framework.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 控制器全局异常数据传输对象
 * @author yanjunhao
 * @date 2017年10月31日
 */
public class ErrorDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    private String errorMsg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> fieldErrors;

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
