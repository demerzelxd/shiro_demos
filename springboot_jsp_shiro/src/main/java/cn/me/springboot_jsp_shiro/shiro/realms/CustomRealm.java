package cn.me.springboot_jsp_shiro.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm
{
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        //subject.login方法调用后会走这里
//        System.out.println("==================");
        String principal = (String) token.getPrincipal();
        if("dada".equals(principal))
        {
            //数据库中查到的密码为123，需要和表单提交的密码进行比对
            return new SimpleAuthenticationInfo(principal, "123", this.getName());
        }
        return null;
    }
}
