package cn.me.springboot_thymeleaf_shiro.controller;

import cn.me.springboot_thymeleaf_shiro.domain.User;
import cn.me.springboot_thymeleaf_shiro.service.UserService;
import cn.me.springboot_thymeleaf_shiro.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * 跳转到register请求
     * @return
     */
    @RequestMapping("registerView")
    public String register()
    {
        System.out.println("跳转到register.html");
        return "register";
    }

    /**
     * 跳转到login请求
     * @return
     */
    @RequestMapping("loginView")
    public String login()
    {
        System.out.println("跳转到login.html");
        return "login";
    }

    /**
     * 验证码方法
     */
    @RequestMapping("image")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException
    {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //验证码放入session
        session.setAttribute("code", code);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, os, code);
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @RequestMapping("register")
    public String register(User user)
    {
        try
        {
            userService.saveUser(user);
            return "redirect:/user/loginView";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "redirect:/user/registerView";
        }
    }

    /**
     * 用来处理身份认证
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public String login(String username, String password, String code, HttpSession session)
    {
        //比较验证码
        String realCode = (String) session.getAttribute("code");
        try
        {
            if (realCode.equalsIgnoreCase(code))
            {
                //获取主体对象
                Subject subject = SecurityUtils.getSubject();
                subject.login(new UsernamePasswordToken(username, password));
                System.out.println("认证状态：" + subject.isAuthenticated());
                return "redirect:/index";
            }
            else
            {
                throw new RuntimeException("验证码错误");
            }
        }
        catch (UnknownAccountException e)
        {
            e.printStackTrace();
            System.out.println("认证失败：用户名不存在");
        }
        catch (IncorrectCredentialsException e)
        {
            e.printStackTrace();
            System.out.println("认证失败：密码错误");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/user/loginView";
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping("logout")
    public String logout()
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/user/loginView";
    }
}
