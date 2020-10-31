package cn.me.springboot_jsp_shiro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  由于CustomRealm不在容器中，不能调用service的方法，所以自定义ApplicationContextUtils来获取service
 *  可以不写，转为将CustomRealm直接@Component注入到容器中
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware
{
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
    }

    //根据bean名称获取容器中指定的bean对象
    public static Object getBean(String beanName)
    {
        return context.getBean(beanName);
    }
}
