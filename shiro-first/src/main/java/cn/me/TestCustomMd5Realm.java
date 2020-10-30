package cn.me;

import cn.me.realm.CustomMd5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * 使用md5的自定义realm
 */
public class TestCustomMd5Realm
{
    public static void main(String[] args)
    {
        //安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //注入realm
        //默认的realm匹配器是类似equals的，这样导致123和202cb962ac59075b964b07152d234b70不匹配
        //设置realm使用hash凭证匹配器
        CustomMd5Realm realm = new CustomMd5Realm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        //设置散列次数
        matcher.setHashIterations(1024);
        realm.setCredentialsMatcher(matcher);
        securityManager.setRealm(realm);
        //注入安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken("dada", "123");
        try
        {
            //认证
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

        //对认证的用户实现授权
        if (subject.isAuthenticated())
        {
            //基于角色的权限控制
            System.out.println(subject.hasRole("admin"));
            //基于多角色的权限控制，某用户必须同时具有几个角色才行
            System.out.println(subject.hasAllRoles(Arrays.asList("admin", "user")));
            //只要具有其中一个角色就行
            System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("admin", "super"))));

            //基于权限字符串的访问控制，资源标识符:操作:资源类型
            System.out.println("===========");
            System.out.println("权限" + subject.isPermitted("item:*:01"));
            System.out.println("权限" + subject.isPermitted("product:update"));
            //分别具有哪些权限
            System.out.println("权限"+Arrays.toString(subject.isPermitted("item:*:01", "order:*:10")));
            //同时具有哪些权限
            System.out.println(subject.isPermittedAll("item:*:01", "order:*:10"));
        }
    }
}
