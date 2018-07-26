/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: LoginController
 * Author: zengweiqiang
 * Date: 2018/4/25  17:16
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.controller;

import com.zz.framework.result.pagination.Pagination;
import com.zz.framework.result.pagination.PaginationSuccessDTO;
import com.zz.inkjet.domain.B_Contact_Msg;
import com.zz.inkjet.domain.Product;
import com.zz.inkjet.domain.QueryProduct;
import com.zz.inkjet.domain.User;
import com.zz.inkjet.dto.MessageDto;
import com.zz.inkjet.impl.B_Contact_MsgServiceImpl;
import com.zz.inkjet.impl.ProductServiceImpl;
import com.zz.inkjet.impl.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengweiqiang on 2018/4/25.
 */
@Controller
@RequestMapping(value = "/back")
public class LoginController {
    private Log logger = LogFactory.getLog(LoginController.class);

    private final UserServiceImpl userService;

    @Autowired
    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public ProductServiceImpl productService;
    @Autowired
    public B_Contact_MsgServiceImpl msgService;

    @RequestMapping("/index")
    public String index() {

        return "main-index";
    }

    @RequestMapping("/admin-table")
    public String adminTable() {

        return "admin-table";
    }

    @RequestMapping("/admin-form")
    public String adminForm() {

        return "admin-form";
    }

    @RequestMapping("/admin-index")
    public String adminIndex() {

        return "admin-index";
    }

    @RequestMapping("/product-table")
    public String productTable(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size, Product product) {
        Pagination pagination = new Pagination(page,size);
        Page<Product> datas = productService.findProductNoCriteria(page, size);
        model.addAttribute("datas", datas) ;
        model.addAttribute("queryForm", product);
        return "product-table";
    }

    @RequestMapping(value = "/findProductQuery",method = {RequestMethod.GET,RequestMethod.POST})
    public String productTableQuery(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "cartridgeType") String cartridgeType,
                                    @RequestParam(value = "pid") String pid,
                                    @RequestParam(value = "itemId") String itemId,
                                    @RequestParam(value = "oemCode") String oemCode,
                                    @RequestParam(value = "suitableMachine") String suitableMachine) {
        Pagination pagination = new Pagination(page,size);
        Product product = new Product();
        product.setCartridgeType(cartridgeType);
        product.setPid(pid);
        product.setItemId(itemId);
        product.setOemCode(oemCode);
        product.setSuitableMachine(suitableMachine);
        Page<Product> datas = productService.findProductCriteria(page, size, product);
        model.addAttribute("datas", datas) ;
        model.addAttribute("queryForm", product);
        return "product-table";
    }

    @RequestMapping("/news-table")
    public String newsTable() {

        return "news-table";
    }

    @RequestMapping(value = "/contact-table")
    public String contactTable(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                               @RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "email", defaultValue = "") String email,
                               @RequestParam(value = "startTime", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                               @RequestParam(value = "endTime", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime
                                ) {
        MessageDto dto = new MessageDto();
        dto.setEmail(email);
        dto.setName(name);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        Pagination pagination = new Pagination(page,size);
        Page<B_Contact_Msg> datas = msgService.findMsgCriteria(page,size,dto);
        model.addAttribute("datas", datas) ;
        model.addAttribute("queryForm", dto);
        return "contact-table";
    }

//    @RequestMapping(value = "/contact-table",method = RequestMethod.POST)
//    public String contactPostTable(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
//                                   @RequestParam(value = "size", defaultValue = "10") Integer size,
//                                   @ModelAttribute MessageDto dto) {
//        Pagination pagination = new Pagination(page,size);
//        Page<B_Contact_Msg> datas = msgService.findMsgCriteria(page,size,dto);
//        model.addAttribute("datas", datas) ;
//        model.addAttribute("queryForm", dto);
//        return "contact-table";
//    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.findByUsernameAndPassword(username,password);
        String str = "";
        if (user !=null){
            str = "admin-index";
        }else {
            str = "index";
        }
        return str;
    }
}
