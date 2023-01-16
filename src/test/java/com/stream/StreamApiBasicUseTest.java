package com.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class StreamApiBasicUseTest {


    private Long startTime;


    @BeforeEach
    void before() {
        startTime = System.currentTimeMillis();
        log.info("method run startTime:" + startTime);
    }

    @AfterEach
    void after() {
        Long endTime = System.currentTimeMillis();

        log.info("method run endTime:" + endTime);
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
    }


    // 方法引用前提：如果lambda表达式的全部内容就是调用一个已有方法，那么可以用方法引用来替代lambda表达式。
    //引用静态方法	Integer::sum
    //引用某个对象的方法	list::add
    //引用某个类的方法	String::length
    //引用构造方法	HashMap::new
    @Test
    @DisplayName("stream method forEach use")
    void exampleByForEach() {
        List<String> strings = Arrays.asList("Try", "It", "Now");
        // strings.stream().forEach(System.out::println);
        strings.forEach(ele -> log.info(ele));
        log.info("================================================================");
        // strings.stream().forEach(System.out::println);
        strings.forEach(log::info);
        // 上述两个写法作用等价
    }
}
