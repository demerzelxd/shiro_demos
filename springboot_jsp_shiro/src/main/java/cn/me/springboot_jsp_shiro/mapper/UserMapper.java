package cn.me.springboot_jsp_shiro.mapper;

import cn.me.springboot_jsp_shiro.domain.Permission;
import cn.me.springboot_jsp_shiro.domain.Role;
import cn.me.springboot_jsp_shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper
{
    void saveUser(User user);

    User findByUsername(String username);

    //根据用户名查询所有角色
    User findRolesByUsername(String username);

    //根据角色id查询权限集合
    List<Permission> findPermsByRoleId(String id);
}
