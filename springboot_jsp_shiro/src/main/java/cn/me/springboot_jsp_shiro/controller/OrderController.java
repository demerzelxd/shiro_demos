package cn.me.springboot_jsp_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
public class OrderController
{
    @RequestMapping("save")
    //注解方式进行授权
    @RequiresRoles("user")
    @RequiresPermissions("user:update:01")//用来判断权限字符串
    public String saveOrder()
    {
        System.out.println("进入方法");
        //代码方式进行授权
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.hasRole("admin"))
//        {
//            System.out.println("保存订单");
//        }
//        else
//        {
//            System.out.println("无权访问");
//        }
        //或者基于权限字符串
        //...
        return "redirect:/index.jsp";
    }
}
