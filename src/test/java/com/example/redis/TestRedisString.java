package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author linyongjin
 * @date 2019/8/15 11:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisString {

    @Autowired
    private RedisTemplate<Object, Object> template;

    @Autowired
    private StringRedisTemplate stringTemplate;

    @Test
    public void testSetAndGet() {
        //测试set key value and get key 指令
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        //set key value
        string.set("name", "Jack");
        string.set("password", "123456");
        //get key
        String name = string.get("name");
        String password = string.get("password");
        System.out.println("name: " + name + ", password: " + password);
    }

    @Test
    public void testSetExSeconds() {
        //测试set key value EX seconds(带过期时间)
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        //100000ms过期
        //TimeUnit.XXXX(对应的时间单位)
        string.set("address", "Fujian", 100000, TimeUnit.SECONDS);
    }

    @Test
    public void testSetnx() {
        //测试setnx key value
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        //key name已经存在不会改动原来的值
        string.setIfAbsent("name", "Rose");
        //输出依旧是Jack
        System.out.println("name: " + string.get("name"));
    }

    @Test
    public void testSetex() {
        //测试setex key value
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        string.setIfPresent("address", "Fujian");
        string.setIfPresent("name", "Rose");
        //address不存在就不会设置输出null
        System.out.println("address: " + string.get("address"));
        //name存在就会设置输出Rose
        System.out.println("name: " + string.get("name"));
    }

    @Test
    public void testGetSet() {
        //测试getset key value
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        String originAddress = string.getAndSet("address", "Fujian");
        System.out.println(originAddress);
    }

    @Test
    public void testStrlenAndAppend() {
        //测试 strlenkey and append key value 指令
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        //strlen key value 指令
        Long size = string.size("address");
        //append key value指令
        Integer afterSize = string.append("address", " Fuzhou");
        System.out.println("追加之前的长度: " + size);
        System.out.println("追加之后的长度: " + afterSize);
        System.out.println("追加之后的内容 " + string.get("address"));
    }
}
