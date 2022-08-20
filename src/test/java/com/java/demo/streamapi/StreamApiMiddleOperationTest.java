package com.java.demo.streamapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @Author yujiale
 * @Date 2022/8/20 15:51
 * @Description stream intermediate operation method
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
}
