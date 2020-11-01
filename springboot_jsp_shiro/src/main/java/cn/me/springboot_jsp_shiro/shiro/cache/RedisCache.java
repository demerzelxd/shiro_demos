package cn.me.springboot_jsp_shiro.shiro.cache;

import cn.me.springboot_jsp_shiro.utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义redis缓存的实现
 */
//@Component
public class RedisCache<K, V> implements Cache<K, V>
{
    //注意：无法自动注入
//    @Autowired
//    private RedisTemplate<K, V> redisTemplate;
    private String cacheName;

    public RedisCache(String cacheName)
    {
        this.cacheName = cacheName;
    }

    public RedisCache()
    {
    }

    @Override
    public V get(K k) throws CacheException
    {
        System.out.println("get key:" + k);
//        return (V) getRedisTemplate().opsForValue().get(k.toString());
        return (V) getRedisTemplate().opsForHash().get(this.cacheName, k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException
    {
        System.out.println("put key:" + k);
        System.out.println("put value:" + v);

//        getRedisTemplate().opsForValue().set(k.toString(), v);
        getRedisTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException
    {
        return (V) getRedisTemplate().opsForHash().delete(this.cacheName, k.toString());
    }

    @Override
    public void clear() throws CacheException
    {
        getRedisTemplate().opsForHash().delete(this.cacheName);
    }

    @Override
    public int size()
    {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys()
    {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values()
    {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate()
    {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //对hash结构，只设置了缓存名的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //还要设置内部的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
