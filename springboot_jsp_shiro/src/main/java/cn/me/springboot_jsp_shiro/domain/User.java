package cn.me.springboot_jsp_shiro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * chain的中文含义是链式的，设置为true，则setter方法返回当前对象
 * 生成的setter方法如下，方法体略
 * public User setId(String id) {}
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    private String id;
    private String username;
    private String password;
    private String salt;

    //定义角色集合
    private List<Role> roles;
}
