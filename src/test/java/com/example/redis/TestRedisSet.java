package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
        template.delete("language");
        //测试set key member
        SetOperations<String, String> set = template.opsForSet();
        Long add = set.add("language", "Python", "Java", "C++", "C", "JavaScript", "Go");
        System.out.println("集合长度: "+ add);
        printResult(set);
    }

    @Test
    public void testSismember() {
        //测试sismember key member
        SetOperations<String, String> set = template.opsForSet();
        Boolean member = set.isMember("language", "Java");
        Boolean member2 = set.isMember("language", "Java2");
        System.out.println(member);
        System.out.println(member2);
    }

    @Test
    public void testSrandMember() {
        //测试srandmember key count
        SetOperations<String, String> set = template.opsForSet();
        printResult(set);
        String language = set.randomMember("language");
        System.out.println(language);
        printResult(set);
        List<String> language1 = set.randomMembers("language", 3L);
        System.out.println(language1);
        printResult(set);
    }

    @Test
    public void testSpop() {
        //测试spop key count
        SetOperations<String, String> set = template.opsForSet();
        printResult(set);
        String language = set.pop("language");
        System.out.println(language);
        printResult(set);
        List<String> language1 = set.pop("language", 3L);
        System.out.println(language1);
        printResult(set);
    }

    @Test
    public void testSrem() {
        //测试srem key member...
        SetOperations<String, String> set = template.opsForSet();
        Long remove = set.remove("language", "Python", "Java");
        System.out.println("移除了" + remove + "个元素");
        printResult(set);
    }
}
