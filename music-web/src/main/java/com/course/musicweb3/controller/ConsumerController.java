package com.course.musicweb3.controller;

import com.alibaba.fastjson.JSONObject;
import com.course.musicweb3.domain.Consumer;
import com.course.musicweb3.service.ConsumerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
    @RequestMapping("/add")

    public Object addConsumer(@RequestBody Consumer consumer){
        JSONObject jsonObject=new JSONObject();
        consumer.setAvator("avatorImages/hhh.png");
        if(consumer.getPassword().equals("")){
            jsonObject.put("code",0);
            jsonObject.put("msg","密码不能为空");
            return jsonObject;
        }
        else if(consumer.getUsername().equals("")){
            jsonObject.put("code",0);
            jsonObject.put("msg","用户名不能为空");
            return jsonObject;
        }
        else if(consumerService.getByUsername(consumer.getUsername())!=null){
            jsonObject.put("code",0);
            jsonObject.put("msg","用户已存在");
            return jsonObject;
        }
        else if(consumerService.insert(consumer)){
            jsonObject.put("code",1);
            jsonObject.put("msg","添加成功");
            return jsonObject;
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","添加失败");
            return jsonObject;
        }
    }
    @RequestMapping("/update")
    public Object updateConsumer(@RequestBody Consumer consumer){
        JSONObject jsonObject=new JSONObject();
        if(consumerService.update(consumer)){
            jsonObject.put("code",1);
            jsonObject.put("msg","修改成功");
            return jsonObject;
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","修改失败");
            return jsonObject;
        }
    }
    @RequestMapping("/delete")
    public Object deleteConsumer(HttpServletRequest request){
        String id=request.getParameter("id");
        return consumerService.delete(Integer.parseInt(id));
    }
    @RequestMapping("/login/status")
    public Object login(HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        boolean flag= consumerService.verifyPassword(username,password);
        JSONObject jsonObject=new JSONObject();
        if(flag){
            jsonObject.put("code",1);
            jsonObject.put("status","success");
            jsonObject.put("msg","登录成功");
            jsonObject.put("userMsg",consumerService.getByUsername(username));
            return jsonObject;
        }else{
            jsonObject.put("code",0);
            jsonObject.put("status","error");
            jsonObject.put("msg","用户名或密码错误");
            return jsonObject;
        }
    }
    @RequestMapping("/allConsumer")
    public Object allConsumer(){
        return consumerService.allConsumer();
    }
    @RequestMapping("/updateConsumerAvator")
    public Object updateConsumerAvator(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages";
        //如果文件路径不存在新增路径
        File file1 = new File(filePath);
        if (file1.exists()) {
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/avatorImages/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = consumerService.selectByPrimaryKey(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag = consumerService.update(consumer);
            if (flag) {
                jsonObject.put("code", 1);
                jsonObject.put("avator", storeAvatorPath);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            }
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败");
        } catch (Exception e) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败" + e.getMessage());
        }
        return jsonObject;
    }
    @RequestMapping("/detail")
    public Object detail(HttpServletRequest request){
        String id=request.getParameter("id");
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }
}
