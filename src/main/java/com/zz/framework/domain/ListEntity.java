package com.zz.framework.domain;

import javax.validation.Valid;
import java.util.List;

/**
 * 检验用列表类
 * @author yanjunhao
 * @date 2017年10月31日
 * @param <T>
 */
public class ListEntity<T> {
    @Valid
    List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
