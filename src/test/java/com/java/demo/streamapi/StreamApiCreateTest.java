package com.java.demo.streamapi;

import com.java.demo.streamapi.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @Author yujiale
 * @Date 2022/8/20 11:49
 * @Description TODO
 **/
@Slf4j
@DataJpaTest
class StreamApiCreateTest {


    @Resource
    private ProductRepo productRepo;

    @Test
    @DisplayName("create null stream ")
    void example1() {
        long startTime = System.currentTimeMillis();
        Stream<String> emptyStream = Stream.empty();
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
        log.info(String.valueOf(emptyStream));
    }

    @Test
    @DisplayName("create stream bases on array")
    void example2() {
        long startTime = System.currentTimeMillis();
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        Stream<String> entireArrayStream = Arrays.stream(arr);
        Stream<String> partArrayStream = Arrays.stream(arr, 1, 4);
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
        log.info(String.valueOf(entireArrayStream));
        log.info(String.valueOf(partArrayStream));
    }


    @Test
    @DisplayName("create stream bases on collection")
    void example3() {
        long startTime = System.currentTimeMillis();
        Collection<String> collection = Arrays.asList("1", "2", "3");
        Stream<String> collectionStream = collection.stream();
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
        log.info(String.valueOf(collectionStream).toString());
    }

    @Test
    @DisplayName("create stream bases on Stream.Builder method")
    void example4() {
        long startTime = System.currentTimeMillis();
        Stream<Object> streamBuilder = Stream.builder().add("1").add("2").add("3").build();
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
        log.info(String.valueOf(streamBuilder).toString());

    }

    @Test
    @DisplayName("create stream bases on file ")
    void example5() throws IOException {
        long startTime = System.currentTimeMillis();
        Path path = Paths.get("/Users/yujiale/AllProject/PrivateProject/stream-api-examples/");
        Stream<String> fileStream = Files.lines(path);
        Stream<String> fileStreamWithCharset = Files.lines(path, StandardCharsets.UTF_8);
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
    }

    @Test
    @DisplayName("create stream bases on  Stream.iterate method")
    void example6() {
        // 创建一个连续元素的 stream,第1个元素是10，第2个元素是11，依此类推，直到元素数量达到size。
        long startTime = System.currentTimeMillis();
        Stream<Integer> iteratedStream = Stream.iterate(10, n -> n + 1).limit(10);
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
    }

    @Test
    @DisplayName("craete stream bases on Stream.generate method")
    void example7() {
        // generate() 方法接受一个Supplier<T>来生成元素。因为流是无限的，所以我们需要设置流的size。下面会包含五个 ele 字符串
        long startTime = System.currentTimeMillis();
        Stream<String> generatedStream = Stream.generate(() -> "ele").limit(5);
        long endTime = System.currentTimeMillis();
        log.info(String.format("exercise 1 - execution time: %1$d ms", (endTime - startTime)));
    }
}
