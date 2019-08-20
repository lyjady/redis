package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public void printResult(ListOperations<String, String> list, String key) {
        List<String> language = list.range(key, 0, -1);
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
        printResult(list);
        String s = list.rightPopAndLeftPush("language", "language");
        System.out.println("弹出的值: " + s);
        printResult(list);
    }

    @Test
    public void testLrem() {
        //测试 lrem key count value
        ListOperations<String, String> list = template.opsForList();
        System.out.print("移除之前的集合");
        printResult(list);
        Long remove = list.remove("language", -2L, "Erlang");
        System.out.println("移除了" + remove + "个");
        System.out.print("移除之后的集合");
        printResult(list);
    }

    @Test
    public void testLlen() {
        //测试llen key
        ListOperations<String, String> list = template.opsForList();
        Long language = list.size("language");
        System.out.println("集合的长度: "+ language);
        printResult(list);
    }

    @Test
    public void testLindex() {
        //测试lindex key index
        ListOperations<String, String> list = template.opsForList();
        String first = list.index("language", 0);
        String last = list.index("language", -1);
        System.out.println("第一个元素: "+ first);
        System.out.println("最后一个元素: " + last);
        printResult(list);
    }

    @Test
    public void testLinsert() {
        //测试linsert key before | after pivot value
        ListOperations<String, String> list = template.opsForList();
        System.out.println("原集合: ");
        printResult(list);
        //linsert key before pivot value
        list.leftPush("language", "javascript", "html");
        System.out.println("左插入后的集合");
        printResult(list);
        //linsert key after pivot value
        list.rightPush("language", "C", "C#");
        System.out.println("右插入后的集合");
        printResult(list);
    }

    @Test
    public void testLset() {
        //测试lset key index value
        ListOperations<String, String> list = template.opsForList();
        list.set("language", 5, "C##");
        printResult(list);
    }

    @Test
    public void testLrange() {
        //测试lrange key start end
        ListOperations<String, String> list = template.opsForList();
        List<String> language = list.range("language", 0, -1);
        System.out.println(language);
    }

    @Test
    public void testLtrim() {
        //测试ltrim key start end
        ListOperations<String, String> list = template.opsForList();
        list.trim("language", 0, -2);
        printResult(list);
    }

    @Test
    public void testBlpop() {
        //测试blpop key... timeout
        ListOperations<String, String> list = template.opsForList();
        String language = list.leftPop("l2", 100L, TimeUnit.SECONDS);
        System.out.println(language);
        printResult(list, "l2");
    }

    @Test
    public void testBrpop() {
        //测试brpop key... timeout
        ListOperations<String, String> list = template.opsForList();
        String language = list.rightPop("l2", 100L, TimeUnit.SECONDS);
        System.out.println(language);
        printResult(list, "l2");
    }

    @Test
    public void testBrpoplpush() {
        //测试brpoplpush source destination timeout
        ListOperations<String, String> list = template.opsForList();
        String s = list.rightPopAndLeftPush("l2", "l2", 100L, TimeUnit.SECONDS);
        System.out.println(s);
        printResult(list, "l2");
    }
}
