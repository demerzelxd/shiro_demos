package cn.me.springboot_jsp_shiro.service.impl;

import cn.me.springboot_jsp_shiro.domain.Role;
import cn.me.springboot_jsp_shiro.domain.User;
import cn.me.springboot_jsp_shiro.mapper.UserMapper;
import cn.me.springboot_jsp_shiro.service.UserService;
import cn.me.springboot_jsp_shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void saveUser(User user)
    {
        //明文密码进行md5 + salt + hash散列
        String salt = SaltUtils.generateSalt();
        //将生成的随机盐保存
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        userMapper.saveUser(user);
    }

    @Override
    public User findByUsername(String username)
    {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findRolesByUsername(String username)
    {
        return userMapper.findRolesByUsername(username);
    }
}
