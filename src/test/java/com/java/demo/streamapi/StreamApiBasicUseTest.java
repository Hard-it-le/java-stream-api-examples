package com.java.demo.streamapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author yujiale
 * @Date 2022/8/20 13:02
 * @Description stream 基本操作（forEach、filter、distinct、sorted、map、flatMap)
 **/
@Slf4j
@DataJpaTest
public class StreamApiBasicUseTest {

    private Long startTime;


    @BeforeEach()
    void before() {
        startTime = System.currentTimeMillis();
        log.info("method run startTime:" + startTime);
    }

    @AfterEach()
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


    @Test
    @DisplayName("stream method filter use")
    void exampleByFilter() {
        // filter() 方法的作用是返回符合条件的Stream。
        Stream.of("Try", "It", "Now")
                .filter(ele -> ele.length() == 3)
                .forEach(log::info);
    }


    @Test
    @DisplayName(" stream method distinct use ")
    void exampleByDistinct() {
        // distinct() 方法返回一个去重的stream
        Stream.of("Try", "It", "Now", "Now")
                .distinct()
                .forEach(log::info);
    }


    @Test
    @DisplayName(" stream method sorted use")
    void exampleBySorted() {
        // 排序函数有两个，一个是自然顺序，还有一个是自定义比较器排序。
        Arrays.asList("Try", "It", "Now")
                .stream()
                .sorted((str1, str2) -> str1.length() - str2.length())
                .forEach(log::info);
    }


    @Test
    @DisplayName("stream method map use")
    void exampleByMap() {
        //map() 方法对每个元素按照某种操作进行转换，转换后流的元素不会改变，但是元素类型取决于转换之后的类型。
        Arrays.asList("Try", "It", "Now")
                .stream()
                .map(String::toUpperCase)
                .forEach(log::info);
    }


    @Test
    @DisplayName("stream method fiatMap use")
    void exampleByFlatMap(){
        // flat的英文就是”平坦的“意思，而flatMap()方法的作用就是将流的元素摊平，原来的stream有两个元素，分别是两个List，执行了flatMap()之后，
        // 将每个List都”摊平“成了一个个的元素，所以会产生一个有三个字符串组成的流。
        Stream.of(Arrays.asList("Try", "It"), Arrays.asList("Now"))
                .flatMap(Collection::stream)
                .forEach(log::info);
    }
}
