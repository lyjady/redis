package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Test
    public void testIncr() {
        //测试incr key and incrby key increment and incrbyflaot key increment指令
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        String originAge = string.get("age");
        //根据第二个参数来指定增量,以执行不同的指令
        string.increment("age", 2);
        String afterAge = string.get("age");
        System.out.println("之前的age: " + originAge);
        System.out.println("之后的age: " + afterAge);
    }

    @Test
    public void testDecr() {
        //测试decr key and decrby key increment 指令
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        String originAge = string.get("age");
        //根据第二个参数来指定减量,以执行不同的指令
        string.decrement("age", 4);
        String afterAge = string.get("age");
        System.out.println("之前的age: " + originAge);
        System.out.println("之后的age: " + afterAge);
    }

    @Test
    public void testMset() {
        //测试mset key value...
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        Map<String, String> keyValue = new HashMap<>();
        keyValue.put("gender", "girl");
        keyValue.put("hobby", "swim");
        string.multiSet(keyValue);
        System.out.println(string.get("gender"));
        System.out.println(string.get("hobby"));
    }

    @Test
    public void testMetnxAndMget() {
        //测试 msetnx key value... and mget key...
        ValueOperations<String, String> string = stringTemplate.opsForValue();
        Map<String, String> keyValue = new HashMap<>();
        //存在的key(原子操作,如果已经有键存在那个键将不被设置,那么其他的键也不会被设置)
//        keyValue.put("name", "Jane");
//        keyValue.put("password", "112233");
        //不存在的key
        keyValue.put("email", "Rose@hh.com");
        keyValue.put("language", "China");
        string.multiSetIfAbsent(keyValue);
        //mget
        List<String> keys = new ArrayList<>();
        keys.add("name");
        keys.add("password");
        keys.add("email");
        keys.add("language");
        keys.add("gender");
        keys.add("hobby");
        List<String> values = string.multiGet(keys);
        System.out.println(values);
    }
}
