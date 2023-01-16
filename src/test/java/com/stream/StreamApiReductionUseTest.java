package com.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author yujiale
 * @Date 2022/8/20 15:26
 * @Description stream reduction use 归约操作
 **/
@Slf4j
class StreamApiReductionUseTest {

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

    /**
     * Collection.stream() 和 Collection.parallelStream() 分别产生序列化流(普通流)和并行流。
     * 并行(parallel)和并发(concurrency)是有区别的。
     * 并发是指一个处理器同时处理多个任务。而并行是指多个处理器或者是多核的处理器同时处理多个不同的任务。
     * 并发是逻辑上的同时发生，而并行是物理上的同时发生。
     * 打个比方：并发是一个人同时吃三个馒头，而并行是三个人同时吃三个馒头。
     * 并且并行不一定快，尤其在数据量很小的情况下，可能比普通流更慢。只有在大数据量和多核的情况下才考虑并行流。
     * 在并行处理情况下，传入给reduce()的集合类，需要是线程安全的，否则执行结果会与预期结果不一样。
     */
    @Test
    @DisplayName("stream reduce use")
    void exampleByReduceMethod() {
        /**
         * 1. identity-初始值
         *
         * 2. accumulator-累加器
         *
         * 3. combiner-拼接器，只有并行执行时才会用到。
         */
        // Optional<T> reduce(BinaryOperator<T> accumulator)
        Optional<Integer> reducedInt = Stream.of(1, 2, 3).reduce((a, b) -> a + b);
        // T reduce(T identity, BinaryOperator<T> accumulator)
        int reduceIntWithTwoParams = Stream.of(1, 2, 3).reduce(10, (a, b) -> a + b);
        // <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        int reducedIntWithAllParams = Stream.of(1, 2, 3).reduce(10, (a, b) -> a + b, (a, b) -> {
            log.info("Combiner was invoked.");
            return a + b;
        });
        // <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        int reducedIntWithAllParamsParallelStream = Arrays.asList(1, 2, 3).parallelStream().reduce(10, (a, b) -> a + b, (a, b) -> {
            log.info("Combiner was invoked.");
            return a + b;
        });
        log.info("reduceIntWithTwoParams:" + reduceIntWithTwoParams);
        log.info("reducedIntWithAllParams:" + reducedIntWithAllParams);
        log.info("reducedIntWithAllParamsParallelStream:" + reducedIntWithAllParamsParallelStream);
    }
}