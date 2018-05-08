package com.zz.inkjet.controller;

import com.zz.demo.controller.DemoController;
import com.zz.demo.domain.Demo;
import com.zz.framework.domain.ListEntity;
import com.zz.framework.exception.ValidException;
import com.zz.framework.result.pagination.Pagination;
import com.zz.framework.result.pagination.PaginationSuccessDTO;
import com.zz.framework.support.Constants;
import com.zz.framework.support.log.annotation.Logging;
import com.zz.inkjet.domain.B_Contact_Msg;
import com.zz.inkjet.impl.B_Contact_MsgServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * rest控制器
 *
 * @author yanjunhao
 * @date 2017.10.09
 */
@RestController
@RequestMapping(value = "/contact")
public class B_Contact_MsgController {

    private Log logger = LogFactory.getLog(DemoController.class);

    private final B_Contact_MsgServiceImpl b_Contact_MsgService;

    @Autowired
    public B_Contact_MsgController(B_Contact_MsgServiceImpl b_Contact_MsgService) {
        this.b_Contact_MsgService = b_Contact_MsgService;
    }

    /**
     * Get请求-根据id获取单个实体
     *
     * @param systemid 主键
     * @return 实体
     */
    @Logging
    @RequestMapping(value = "/contacts/{systemid}", method = RequestMethod.GET)
    public ResponseEntity<B_Contact_Msg> getBySystemId(@PathVariable("systemid") String systemid) throws Exception {
        return ResponseEntity.ok(b_Contact_MsgService.getBySystemid(systemid));
    }

    /**
     * Get请求-默认按主键降序取10条
     *
     * @param pagination       分页对象
     * @param paginationResult 分页对象校验结果，校验结果必须跟在校验对象后，否则无法捕获
     * @param sort             排序
     * @return 实体
     */
    @Logging
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ResponseEntity<PaginationSuccessDTO<B_Contact_Msg>> getEntices(
            @Valid Pagination pagination, BindingResult paginationResult,
            @RequestParam(defaultValue = "systemid-d") String sort) throws Exception {
        if (paginationResult.hasErrors()) {
            throw new ValidException(paginationResult);
        }
        return ResponseEntity.ok(b_Contact_MsgService.getEntices(pagination, sort));
    }

    /**
     * Post请求-新增实体
     *
     * @param entity 需要保存的实体
     * @return 保存后的实体，包含了自动生成的主键
     */
    @Logging
    @RequestMapping(value = "/contacts", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity save(@RequestBody @Valid B_Contact_Msg entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        if (null != entity.getSystemid() && !"".equals(entity.getSystemid())) {
            //抛出异常，修改不能调用此方法
            throw new Exception("修改不能调用此方法。请使用[PUT]请求。");
        }
        //创建完成返回201状态码created
        return ResponseEntity.status(HttpStatus.CREATED).body(b_Contact_MsgService.save(entity));
    }

    @Logging
    @RequestMapping(value = "/batch-msgs", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity batchSave(@RequestBody @Valid ListEntity<B_Contact_Msg> entityList, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        //检查是否包含systemid
        if (entityList.getList().stream().anyMatch(entity -> null != entity.getSystemid() && !"".equals(entity.getSystemid()))) {
            //抛出异常，修改不能调用此方法
            throw new Exception("修改不能调用此方法。请使用[PUT]请求。");
        }
        //创建完成返回201状态码created
        return ResponseEntity.status(HttpStatus.CREATED).body(b_Contact_MsgService.batchSave(entityList.getList()));
    }

    /**
     * Put请求-更新实体
     *
     * @param entity 需要保存的实体
     * @return 保存后的实体
     */
    @Logging(changeObject = Demo.class)
    @RequestMapping(value = "/contact/{systemid}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity update(@PathVariable("systemid") String systemid,
                                 @RequestBody @Valid B_Contact_Msg entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        if (null == b_Contact_MsgService.getBySystemid(systemid)) {
            throw new Exception("[" + systemid + "]找不到对应实体。");
        }
        entity.setSystemid(systemid);
        return ResponseEntity.ok(b_Contact_MsgService.update(entity));
    }

    /**
     * Put请求-部分更新
     *
     * @param entity 更新内容
     */
    @Logging(changeObject = Demo.class)
    @RequestMapping(value = "/patch-msg", method = RequestMethod.PUT,consumes = "application/sino-patch")
    public ResponseEntity patchUpdate(@RequestBody @Valid B_Contact_Msg entity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidException(result);
        }
        return ResponseEntity.ok(b_Contact_MsgService.update(entity));
    }


    /**
     * 逻辑删除
     *
     * @param ids id组，英文逗号分隔
     * @return 响应体
     */
    //@Transactional
    @Logging
    @RequestMapping(value = "/contact/{ids}", method = RequestMethod.DELETE)
    public ResponseEntity logicRemove(@PathVariable("ids") String ids) throws Exception {
        b_Contact_MsgService.logicRemove(ids.split(Constants.SPLIT_COMMA));
        //返回204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
