package cn.me;

import cn.me.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 使用自定义realm
 */
public class TestCustomRealmAuthenticator
{
    public static void main(String[] args)
    {
        //创建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置自定义realm
        securityManager.setRealm(new CustomRealm());
        //将安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //通过安全工具类获取subject主体
        Subject subject = SecurityUtils.getSubject();
        //创建token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
        try
        {
            //测试登录
            subject.login(token);
            System.out.println("认证状态：" + subject.isAuthenticated());
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
