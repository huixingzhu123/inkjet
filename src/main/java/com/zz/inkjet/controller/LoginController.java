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
import com.zz.inkjet.domain.Product;
import com.zz.inkjet.domain.QueryProduct;
import com.zz.inkjet.domain.User;
import com.zz.inkjet.impl.ProductServiceImpl;
import com.zz.inkjet.impl.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
