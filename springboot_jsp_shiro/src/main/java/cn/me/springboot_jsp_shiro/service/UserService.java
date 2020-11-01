package cn.me.springboot_jsp_shiro.service;

import cn.me.springboot_jsp_shiro.domain.Permission;
import cn.me.springboot_jsp_shiro.domain.Role;
import cn.me.springboot_jsp_shiro.domain.User;

import java.util.List;

public interface UserService
{
    void saveUser(User user);

    User findByUsername(String username);

    User findRolesByUsername(String username);

    //根据角色id查询权限集合
    List<Permission> findPermsByRoleId(String id);
}
