package cn.me;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 测试shiro的md5
 */
public class TestShiroMd5
{
    public static void main(String[] args)
    {
//        //创建md5算法，使用setBytes进行的加密结果不是我们想要的
//        Md5Hash md5Hash = new Md5Hash();
//        //给md5加入数据
//        md5Hash.setBytes("123".getBytes());
//        //进行16进制加密
//        String s = md5Hash.toHex();
//        System.out.println(s);

        //使用md5，必须要通过构造方法
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toHex());

        //使用md5加盐（仅把盐加在后面），默认散列一次
        Md5Hash md5Hash1 = new Md5Hash("123", "X0*7ps");
        System.out.println(md5Hash1.toHex());

        //使用md5加盐加哈希散列，对结果散列1024次
        Md5Hash md5Hash2 = new Md5Hash("123", "X0*7ps", 1024);
        System.out.println(md5Hash2.toHex());
    }
}
