package cn.me.springboot_thymeleaf_shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
    @RequestMapping("index")
    public String index()
    {
        System.out.println("index thymeleaf");
        return "index";
    }
}
