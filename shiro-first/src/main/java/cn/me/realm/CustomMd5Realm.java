package cn.me.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 使用md5+salt+hash散列的自定义realm
 */
public class CustomMd5Realm extends AuthorizingRealm
{
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
//        System.out.println("==========");
        //只能有一个主身份信息，也就是用户名
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("身份信息" + primaryPrincipal);
        //然后根据用户名查询数据库，看这个用户有什么角色
        //参数为数据库中查询到的该用户角色信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");

        //将数据库中查询的权限信息赋值给权限对象
        simpleAuthorizationInfo.addStringPermission("item:*:*");
        simpleAuthorizationInfo.addStringPermission("product:create:*");

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        //获取身份信息
        String principal = (String) token.getPrincipal();
        //根据用户名查询数据库
        if ("dada".equals(principal))
        {
            //与 数据库中的用户名，加密后的密码，随机盐 做比较
            return new SimpleAuthenticationInfo(principal,
                    "e4f9bf3e0c58f045e62c23c533fcf633",
                    ByteSource.Util.bytes("X0*7ps"),
                    this.getName());
        }
        return null;
    }
}
