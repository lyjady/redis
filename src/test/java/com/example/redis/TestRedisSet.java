package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author linyongjin
 * @date 2019/8/20 11:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisSet {

    @Autowired
    private StringRedisTemplate template;

    private static void printResult(SetOperations<String, String> set) {
        Set<String> language = set.members("language");
        System.out.println(language);
    }

    private static void printResult(SetOperations<String, String> set, String key) {
        Set<String> members = set.members(key);
        System.out.println(members);
    }

    @Test
    public void testSadd() {
        //测试set key member
        SetOperations<String, String> set = template.opsForSet();
        Long add = set.add("language", "Python", "Java", "C++", "C", "JavaScript", "Go");
        System.out.println("集合长度: "+ add);
        printResult(set);
    }
}
