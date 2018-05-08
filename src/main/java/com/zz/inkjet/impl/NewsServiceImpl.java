/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: NewsServiceImpl
 * Author: zengweiqiang
 * Date: 2018/5/1  1:05
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.impl;

import com.zz.framework.service.BaseServiceImpl;
import com.zz.inkjet.domain.News;
import com.zz.inkjet.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by zengweiqiang on 2018/5/1.
 */
@Service("NewsService")
public class NewsServiceImpl extends BaseServiceImpl<News> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        super(newsRepository);
        this.newsRepository = newsRepository;
    }
}
