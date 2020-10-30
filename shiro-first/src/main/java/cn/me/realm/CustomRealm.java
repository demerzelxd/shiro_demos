package cn.me.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm实现，将数据库的数据作为认证/授权的数据来源，抛弃ini文件
 */
public class CustomRealm extends AuthorizingRealm
{
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        //return null表示根据用户名始终没有查到，会抛出UnknownAccountException
        //return null;

        //在token中通过身份信息principal获取用户名
        String principal = (String) token.getPrincipal();
        System.out.println(principal);//打印出用户输入的用户名zhangsan
        //根据身份信息使用jdbc或mybatis查询数据库
        if ("dada".equals(principal))
        {
            //如果在数据库中能查到，SimpleAuthenticationInfo需要提供realm的名字，使用getName方法即可
            //参数为数据库中的用户名，正确的密码和realm名称
            return new SimpleAuthenticationInfo(principal, "123", this.getName());
        }
        return null;
    }
}
