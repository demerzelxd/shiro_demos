package cn.me.springboot_jsp_shiro.controller;

import cn.me.springboot_jsp_shiro.domain.User;
import cn.me.springboot_jsp_shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping("register")
    public String register(User user)
    {
        try
        {
            userService.saveUser(user);
            return "redirect:/login.jsp";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

    /**
     * 用来处理身份认证
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public String login(String username, String password)
    {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("认证状态：" + subject.isAuthenticated());
            return "redirect:/index.jsp";
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
        return "redirect:/login.jsp";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("logout")
    public String logout()
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }
}
