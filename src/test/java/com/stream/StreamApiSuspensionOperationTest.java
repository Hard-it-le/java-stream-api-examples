package com.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * forEach	遍历Stream中的每个元素
 * forEachOrdered	遍历Stream中的每个元素 区别： 在串行流（stream）中没有区别，在并行流（parallelStream）中如果数据源是有序集合，forEachOrdered输出顺序与数据源中顺序一致，forEach则是乱序。
 * toArray	将流转换为Object[]或者指定类型的数组
 * reduce	将集合中的每个元素聚合成一条数据
 * collect	将流转换成集合或聚合元素
 * min	获取集合中最小值
 * max	获取集合中最大值
 * count	获取集合中元素个数
 * anyMatch	Stream 中任意一个元素符合传入的 predicate，返回 true
 * allMatch	Stream 中全部元素符合传入的 predicate，返回 true
 * noneMatch	Stream 中没有一个元素符合传入的 predicate，返回 true
 * findFirst	如果数据源是有序集合，返回Stream 中第一个元素的Optional对象，如果是无序集合，则返回Stream 中任意一个元素的Optional对象。
 * findAny	返回Stream 中任意一个元素的Optional对象。
 * iterator	返回一个无限元素的有序的Stream对象。
 * builder	返回一个Builder对象，Builder对象在调用build()返回Stream对象。
 * empty	返回一个空的有序的Stream对象。
 * of	返回包含单个元素的有序的Stream对象。
 * generate	返回一个无限元素的无序的的Stream对象。
 * concat	将两个Stream连接成一个Stream。
 **/
@Slf4j
class StreamApiSuspensionOperationTest {

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
    @DisplayName("stream method forEach use")
    void forEach() {
        Stream.of("a", "b", "c", "d", "e", "f").forEach(log::info);
    }

    @Test
    @DisplayName("stream method forEach use")
    void toArray() {
        Object[] objects = Stream.of("a", "b", "c", "d", "e", "f").toArray();
        log.info("objects {}: ", objects);
    }

    @Test
    @DisplayName("stream method reduce use")
    void reduce() {
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        Optional<Integer> count2 =
                ints.stream().map((e) -> 1).reduce(Integer::sum);
        log.info("reduce count2: {}", count2);
    }

    @Test
    @DisplayName("stream method collect use")
    void collect() {
        List<String> collect = Stream.of("a", "b", "c", "d", "e", "f").collect(Collectors.toList());
        log.info("collect {}: ", collect);
    }

    @Test
    @DisplayName("stream method min use")
    void min() {
        List<Integer> numList = Arrays.asList(42, 44, 43, 41);
        Comparator<Integer> comparator = Comparator.comparing(Integer::intValue);
        Optional<Integer> minOptional = numList.stream().min(comparator);
        minOptional.ifPresent(e -> log.info(e.toString()));
    }

    @Test
    @DisplayName("stream method max use")
    void max() {
        List<Integer> numList = Arrays.asList(42, 44, 43, 41);
        Comparator<Integer> comparator = Comparator.comparing(Integer::intValue);
        Optional<Integer> maxOptional = numList.stream().max(comparator);
        maxOptional.ifPresent(e -> log.info(e.toString()));
    }

    @Test
    @DisplayName("stream method count use")
    void count() {
        long count = Stream.of("a", "b", "c", "d", "e", "f").count();
        log.info("count {}: ", count);
    }

    @Test
    @DisplayName("stream method empty use")
    void emptyStream() {
        Stream<Object> empty = Stream.empty();
        log.info("empty {}: ", empty);
    }

    @Test
    @DisplayName("stream method of use")
    void of() {
        Stream<Object> of = Stream.of("a", "b", "c", "d", "e", "f");
        log.info("of {}: ", of);
    }

    @Test
    @DisplayName("stream method generate use")
    void generate() {
        Stream.generate(() -> new Random().nextInt(10)).limit(3)
                .forEach(e -> log.info("of {}: ", e));

        Stream.generate(() -> new Random().nextBoolean()).limit(3)
                .forEach(e -> log.info("of {}: ", e));

        Stream.generate(() -> "Hello World!").limit(3)
                .forEach(e -> log.info("of {}: ", e));
    }

    @Test
    @DisplayName("stream method concat use")
    void concat() {
        Stream<String> a = Stream.of("a", "b", "c", "d", "e", "f");
        Stream<String> b = Stream.of("g", "h", "i", "j", "k", "l");
        Stream<String> concat = Stream.concat(a, b);
        concat.forEach(e -> log.info("of {}: ", e));
    }
}