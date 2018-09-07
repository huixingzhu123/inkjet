package com.zz.ueditor.controller;

import com.baidu.ueditor.ActionEnter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping(value = "/ueditor")
public class UEditorController {

    @RequestMapping("/editor")
    private String showPage(){
        return "editor";
    }

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
//            File file = ResourceUtils.getFile("classpath:static/ueditor/config.json");//rootPath + "config.json";
//            String exec = FileUtils.readFileToString(file);
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
