package cn.me.springboot_jsp_shiro.mapper;

import cn.me.springboot_jsp_shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper
{
    void saveUser(User user);
}
