package com.java.demo.streamapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @Author yujiale
 * @Date 2022/8/20 15:52
 * @Description stream terminal operation method
 *
 * orEach	遍历Stream中的每个元素
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
@DataJpaTest
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
}
