package com.xcy.video.controller;


import com.xcy.video.pojo.Admin;
import com.xcy.video.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired //从spring容器中去一个它的实现类，赋值给它
    AdminService adminService;

    @RequestMapping("/showLogin")
    public String showLogin(){
       // int i= 10/0;
        return "behind/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(Admin admin, HttpSession session){

        boolean result = adminService.validateLogin(admin);
        if(result){
            session.setAttribute("adminAccount",admin.getUsername());
            return "success";
        }else{
            return "fail";
        }

    }
}
