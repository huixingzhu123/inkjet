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
import com.zz.inkjet.domain.User;
import com.zz.inkjet.impl.ProductServiceImpl;
import com.zz.inkjet.impl.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/index")
    public String index() {

        return "main-index";
    }

    @RequestMapping("/product-table")
    public String productTable(Model model) {
        Pagination pagination = new Pagination(1,2);
        PaginationSuccessDTO data = productService.getEntices(pagination, "createdTime-d");
        model.addAttribute("data", data) ;
        model.addAttribute("name", "hahahahaha");
        return "product-table";
    }

    @RequestMapping("/news-table")
    public String newsTable() {

        return "news-table";
    }

    @RequestMapping("/contact-table")
    public String contactTable() {
        return "contact-table";
    }

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
