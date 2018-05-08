package com.zz.framework.response.error;

/**
 * 字段错误
 * @author yanjunhao
 * @date 2017年10月31日
 */
public class FieldError {
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String fieldName;
    private Object fieldValue;
    private String message;
}
