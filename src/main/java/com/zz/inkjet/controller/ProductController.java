/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: ProductController
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
import com.zz.inkjet.domain.Product;
import com.zz.inkjet.impl.ProductServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/Product")
public class ProductController {
    private Log logger = LogFactory.getLog(ProductController.class);

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity save(@RequestBody @Valid Product entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        if (null != entity.getSystemid() && !"".equals(entity.getSystemid())) {
            //抛出异常，修改不能调用此方法
            throw new Exception("修改不能调用此方法。请使用[PUT]请求。");
        }
        //创建完成返回201状态码created
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(entity));
    }

    @RequestMapping(value = "/batch-products", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity batchSave(@RequestBody @Valid ListEntity<Product> entityList, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        //检查是否包含systemid
        if (entityList.getList().stream().anyMatch(entity -> null != entity.getSystemid() && !"".equals(entity.getSystemid()))) {
            //抛出异常，修改不能调用此方法
            throw new Exception("修改不能调用此方法。请使用[PUT]请求。");
        }
        //创建完成返回201状态码created
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.batchSave(entityList.getList()));
    }

    @RequestMapping(value = "/product/{systemid}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity update(@PathVariable("systemid") String systemid,
                                 @RequestBody @Valid Product entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        if (null == productService.getBySystemid(systemid)) {
            throw new Exception("[" + systemid + "]找不到对应实体。");
        }
        entity.setSystemid(systemid);
        return ResponseEntity.ok(productService.update(entity));
    }

    @RequestMapping(value = "/products/{systemid}", method = RequestMethod.GET)
    public ResponseEntity<Product> getBySystemId(@PathVariable("systemid") String systemid) throws Exception {
        return ResponseEntity.ok(productService.getBySystemid(systemid));
    }

    @RequestMapping(value = "/getProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity productTableQuery(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "20") Integer size,
                                            @RequestParam(value = "kind") String kind, @RequestParam(value = "itemId") String itemId,
                                            @RequestParam(value = "oemCode") String oemCode, @RequestParam(value = "suitableMachine") String suitableMachine) {
        Product product = new Product();
        product.setCartridgeType(kind);
        product.setItemId(itemId);
        product.setOemCode(oemCode);
        product.setSuitableMachine(suitableMachine);
//        Pagination pagination = new Pagination(1,20);
        Page<Product> datas = productService.findProductCriteria(page, size, product);
        model.addAttribute("datas", datas);

        return ResponseEntity.ok(datas);
    }

    @RequestMapping(value = "/products/{systemids}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> physicalRemove(@PathVariable("systemids") String systemids) throws Exception {
        Map<String, String> mapDto = new HashMap<String, String>();
        mapDto.put("success", "0");

        try {
            productService.physicalRemove(systemids.split(Constants.SPLIT_COMMA));
            mapDto.put("success", "1");
        } catch (Exception e) {
        }
        //返回204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapDto);
    }
}
