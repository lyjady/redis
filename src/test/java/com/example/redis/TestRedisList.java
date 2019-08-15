package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author linyongjin
 * @date 2019/8/15 14:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisList {

    @Autowired
    private StringRedisTemplate template;

    public void printResult(ListOperations<String, String> list) {
        List<String> language = list.range("language", 0, -1);
        System.out.println(language);
    }

    @Test
    public void testLpush() {
        template.delete("language");
        //测试lpush key value...
        ListOperations<String, String> list = template.opsForList();
        list.leftPush("language", "java");
        //也可以传入Collection集合
        list.leftPushAll("language", "C", "C++", "Erlang", "Go", "python");
        printResult(list);
    }

    @Test
    public void testLpushx() {
        //测试lpushx key value
        ListOperations<String, String> list = template.opsForList();
        list.leftPush("language", list.index("language", 0), "javascript");
        printResult(list);
    }

    @Test
    public void testRpush() {
        template.delete("language");
        //测试rpush key value....
        ListOperations<String, String> list = template.opsForList();
        list.rightPush("language", "java");
        //也可以传入Collection集合
        list.rightPushAll("language", "C", "C++", "Erlang", "Go", "python");
        printResult(list);
    }

    @Test
    public void testRpushx() {
        //测试rpushx key value
        ListOperations<String, String> list = template.opsForList();
        list.rightPush("language", list.index("language", -1), "javascript");
        printResult(list);
    }

    @Test
    public void testLpopAndRpop() {
        //测试lpop key value and rpop key value
        ListOperations<String, String> list = template.opsForList();
        list.leftPop("language");
        list.rightPop("language");
        printResult(list);
    }

    @Test
    public void testRpopLpush() {
        //测试 rpoplpush source destination
        ListOperations<String, String> list = template.opsForList();

    }
}
