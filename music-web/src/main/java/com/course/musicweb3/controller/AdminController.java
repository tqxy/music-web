package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.service.AdminService;
import com.course.musicweb3.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * 判断是否登陆成功
     */
    @RequestMapping(value = "/admin/login/status",method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest request, HttpSession session){
        JSONObject loginResult = new JSONObject();
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        boolean flag=adminService.login(username,password);
        if(flag){
            loginResult.put(Consts.CODE,200);
            loginResult.put("status","success");
            loginResult.put(Consts.MSG,"登录成功");
            session.setAttribute(Consts.USERNAME,username);
        }else{
            loginResult.put(Consts.CODE,500);
            loginResult.put("status","error");
            loginResult.put(Consts.MSG,"用户名或密码错误");
        }
        return loginResult;
    }
}
