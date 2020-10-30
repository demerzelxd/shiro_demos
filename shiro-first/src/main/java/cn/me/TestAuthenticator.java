package cn.me;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class TestAuthenticator
{
    public static void main(String[] args)
    {
        //1.创建安全管理器对象，Ctrl H按结构图
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.给安全管理器设置realm，用于数据交互。realm主要用于认证授权时候数据的提供
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        //3.SecureUtils全局安全工具类进行认证，给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4.关键对象Subject主体
        Subject subject = SecurityUtils.getSubject();
        //5.先创建令牌，会比对ini的身份中有没有dada这个用户来进行认证
        UsernamePasswordToken token = new UsernamePasswordToken("dada", "dada");
        try
        {
            System.out.println("认证状态:" + subject.isAuthenticated());//没有登录，认证失败
            subject.login(token);//用户认证需要令牌，ctrl alt t代码包裹异常处理
            System.out.println("认证状态:" + subject.isAuthenticated());
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
    }
}
