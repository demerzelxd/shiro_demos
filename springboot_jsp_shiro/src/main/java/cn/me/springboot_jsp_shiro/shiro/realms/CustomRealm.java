package cn.me.springboot_jsp_shiro.shiro.realms;

import cn.me.springboot_jsp_shiro.domain.Role;
import cn.me.springboot_jsp_shiro.domain.User;
import cn.me.springboot_jsp_shiro.service.UserService;
import cn.me.springboot_jsp_shiro.utils.ApplicationContextUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义realm
 */
@Component
public class CustomRealm extends AuthorizingRealm
{
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        //获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        //根据主身份查询数据库，看这个用户有什么角色
        //参数为数据库中查询到的该用户角色信息
//        if("dada".equals(primaryPrincipal))
//        {
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            simpleAuthorizationInfo.addRole("user");
//            simpleAuthorizationInfo.addStringPermission("user:update:*");
//            simpleAuthorizationInfo.addStringPermission("user:select:*");
//            return simpleAuthorizationInfo;
//        }
        User user = userService.findRolesByUsername(primaryPrincipal);
        //授权角色信息
        if(!CollectionUtils.isEmpty(user.getRoles()))
        {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        //subject.login方法调用后会走这里
//        System.out.println("==================");
        String principal = (String) token.getPrincipal();
        //在容器中获取service对象
//        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
//        if("dada".equals(principal))
//        {
//            //假设数据库中查到的密码为123，需要和表单提交的密码进行比对
//            return new SimpleAuthenticationInfo(principal, "123", this.getName());
//        }
        User user = userService.findByUsername(principal);
        if (!ObjectUtils.isEmpty(user))
        {
            return new SimpleAuthenticationInfo(
                    user.getUsername(),
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),
                    this.getName());
        }
        return null;
    }
}
