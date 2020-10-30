package cn.me.springboot_jsp_shiro.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;

public class SaltUtils
{
    /**
     * 生成32位随机盐
     * @return
     */
    public static String generateSalt()
    {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

    /**
     * 生成指定位数盐
     * @param n
     * @return
     */
    public static String generateSalt(int n)
    {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
        {
            char ch = chars[new Random().nextInt(chars.length)];
            sb.append(ch);
        }
        return sb.toString();
    }

//    public static void main(String[] args)
//    {
//        System.out.println(generateSalt());
//        System.out.println(generateSalt(32));
//    }
}
