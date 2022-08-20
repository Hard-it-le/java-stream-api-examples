package com.java.demo.streamapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author yujiale
 * @Date 2022/8/20 15:51
 * @Description   stream intermediate operation method
 * <p>
 * 方法名	说明
 * map	将对应的元素使用给定方法进行转换
 * mapToInt(mapToLong,mapToDouble)	将对应的元素使用给定方法进行转换(返回类型必须是 int,long,double)
 * flatMap	如果流的元素为数组或者Collection，flatMap就是将每个Object[]元素或Collection<Object>元素都转换为Object元素
 * filter	通过设置条件来过滤元素
 * distinct	集合中的元素去重
 * sorted	将集合中的元素排序
 * peek	生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数即引用的方法A，当Stream每个元素被消费的时候都会先执行新Stream给定的方法A。peek是中间操作，如果pee后没有最终操作，则peek不会执行
 * limit	返回Stream的前n个元素
 * skip	删除Stream的前n个元素
 **/
@Slf4j
@DataJpaTest
class StreamApiMiddleOperationTest {

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

    @Test
    @DisplayName("stream map method use ")
    void map() {
        List<String> arrayList = Arrays.asList("a", "b", "c", "d", "e", "f");
        List<String> collect = arrayList.stream().map(String::toLowerCase).collect(Collectors.toList());
        log.info(String.valueOf(collect));
        //map() 方法对每个元素按照某种操作进行转换，转换后流的元素不会改变，但是元素类型取决于转换之后的类型。
        Arrays.asList("Try", "It", "Now")
                .stream()
                .map(String::toUpperCase)
                .forEach(log::info);
    }

    @Test
    @DisplayName("stream mapToInt method use ")
    void mapToInt() {
        // List使用mapToInt
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        // 改造Integer流
        list.stream().mapToInt(t -> t * 2).forEach(i -> log.info(String.valueOf(i)));
        // 对Integer流内元素进行计算，除了sum()还有许多操作
        log.info(String.valueOf(list.stream().mapToInt(t -> t * 2).sum()));
    }


    @Test
    @DisplayName("stream flatMap method use")
    void flatMap() {
        // 将每个List都”摊平“成了一个个的元素，所以会产生一个有三个字符串组成的流。
        Stream.of(Arrays.asList("Try", "It"), Collections.singletonList("Now"))
                .flatMap(Collection::stream)
                .forEach(log::info);
    }

    @Test
    @DisplayName("stream filter method use")
    void filter() {
        // filter() 方法的作用是返回符合条件的Stream。
        Stream.of("Try", "It", "Now")
                .filter(ele -> ele.length() == 3)
                .forEach(log::info);
    }

    @Test
    @DisplayName("stream distinct method use")
    void distinct() {
        // distinct() 方法返回一个去重的stream
        Stream.of("Try", "It", "Now", "Now")
                .distinct()
                .forEach(log::info);
    }

    @Test
    @DisplayName(" stream  sorted  method use")
    void sorted() {
        // 排序函数有两个，一个是自然顺序，还有一个是自定义比较器排序。
        Stream.of("Try", "It", "Now")
                .sorted((str1, str2) -> str1.length() - str2.length())
                .forEach(log::info);
    }

    @Test
    @DisplayName("stream peek method use")
    void peek() {
        List<String> list = Arrays.asList("AA", "BB", "CC", "BB", "CC", "AA", "AA");
        List<String> collect = list.stream()
                .distinct()
                .peek(log::info).collect(Collectors.toList());
        log.info("collect {}: ", collect.size());
    }

    @Test
    @DisplayName("stream limit method use")
    void limit() {
        // limit 返回  stream 的前 n 个元素
        Stream.of("Try", "It", "Now", "Then", "Now", "Then").limit(2).forEach(log::info);
    }

    @Test
    @DisplayName("stream skip method use")
    void skip() {
        // skip 删除 stream 的前 n 个元素
        Stream.of("Try", "It", "Now", "Then").skip(2).forEach(log::info);
    }
}
