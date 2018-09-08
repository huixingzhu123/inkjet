/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: NewsController
 * Author: zengweiqiang
 * Date: 2018/5/1  1:06
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.controller;

import com.zz.framework.domain.ListEntity;
import com.zz.framework.exception.ValidException;
import com.zz.framework.support.Constants;
import com.zz.inkjet.domain.News;
import com.zz.inkjet.dto.NewsDto;
import com.zz.inkjet.impl.NewsServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengweiqiang on 2018/5/1.
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    private Log logger = LogFactory.getLog(NewsController.class);

    private final NewsServiceImpl newsService;

    @Autowired
    public NewsController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(value = "/News", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity save(@RequestBody @Valid News entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        if (null != entity.getSystemid() && !"".equals(entity.getSystemid())) {
            //抛出异常，修改不能调用此方法
            throw new Exception("修改不能调用此方法。请使用[PUT]请求。");
        }
        //创建完成返回201状态码created
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.save(entity));
    }

    @RequestMapping(value = "/news", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getLatestNews(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "5") Integer size) throws Exception {
        return ResponseEntity.ok(newsService.findNewsCriteria(page, size, new NewsDto()));
    }

    @RequestMapping(value = "/batch-news", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity batchSave(@RequestBody @Valid ListEntity<News> entityList, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        //检查是否包含systemid
        if (entityList.getList().stream().anyMatch(entity -> null != entity.getSystemid() && !"".equals(entity.getSystemid()))) {
            //抛出异常，修改不能调用此方法
            throw new Exception("修改不能调用此方法。请使用[PUT]请求。");
        }
        //创建完成返回201状态码created
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.batchSave(entityList.getList()));
    }

    @RequestMapping(value = "/news/{systemid}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity update(@PathVariable("systemid") String systemid,
                                 @RequestBody @Valid News entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        if (null == newsService.getBySystemid(systemid)) {
            throw new Exception("[" + systemid + "]找不到对应实体。");
        }
        entity.setSystemid(systemid);
        return ResponseEntity.ok(newsService.update(entity));
    }

    @RequestMapping(value = "/news/{ids}", method = RequestMethod.DELETE)
    public ResponseEntity logicRemove(@PathVariable("ids") String ids) throws Exception {
        Map<String, String> mapDto = new HashMap<String, String>();
        mapDto.put("success", "0");
        try {
            newsService.logicRemove(ids.split(Constants.SPLIT_COMMA));
            mapDto.put("success", "1");
        } catch (Exception e) {

        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapDto);
    }

}
