package cn.me.springboot_jsp_shiro.config;

import cn.me.springboot_jsp_shiro.shiro.cache.RedisCacheManager;
import cn.me.springboot_jsp_shiro.shiro.realms.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig
{
    //1.创建ShiroFilter负责拦截请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统受限资源
        //配置系统公共资源
        Map<String, String> map = new HashMap<>();
//        map.put("/index.jsp", "authc");//authc代表请求这个资源需要认证和授权
        map.put("/user/login", "anon");//将login请求设置为anon公共的，不拦截
        map.put("/user/image", "anon");//将验证码请求设置为anon公共的，不拦截
        map.put("/login.jsp", "anon");//将login页面设置为anon公共的，不拦截
        map.put("/user/register", "anon");//将register请求设置为anon公共的，不拦截
        map.put("/register.jsp", "anon");//将register页面设置为anon公共的，不拦截
        map.put("/**", "authc");//authc代表请求这个资源需要认证和授权
        //默认认证界面路径
//        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //2.创建ShiroFilter需要的安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realm") Realm realm)
    {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    //3.创建自定义realm
    @Bean("realm")
    public Realm getRealm()
    {
        CustomRealm customRealm = new CustomRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        customRealm.setCredentialsMatcher(matcher);

        //开启缓存管理，设置缓存为EhCache
//        customRealm.setCacheManager(new EhCacheManager());
//        //开启全局缓存
//        customRealm.setCachingEnabled(true);
//        //开启认证和授权的缓存管理
//        customRealm.setAuthenticationCachingEnabled(true);
//        //设置认证的缓存名
//        customRealm.setAuthenticationCacheName("AuthenticationCache");
//        customRealm.setAuthorizationCachingEnabled(true);
//        //设置授权的缓存名
//        customRealm.setAuthorizationCacheName("AuthorizationCache");
        customRealm.setCacheManager(new RedisCacheManager());
        customRealm.setCachingEnabled(true);//开启全局缓存
        customRealm.setAuthenticationCachingEnabled(true);//认证认证缓存
        customRealm.setAuthenticationCacheName("authenticationCache");
        customRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        customRealm.setAuthorizationCacheName("authorizationCache");
        return customRealm;
    }
}
