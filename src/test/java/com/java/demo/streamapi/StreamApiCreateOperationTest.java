package com.java.demo.streamapi;

import com.java.demo.streamapi.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @Author yujiale
 * @Date 2022/8/20 11:49
 * @Description create stream method
 **/
@Slf4j
@DataJpaTest
class StreamApiCreateOperationTest {


    @Resource
    private ProductRepo productRepo;

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
    @DisplayName("create null stream ")
    void example1() {
        Stream<String> emptyStream = Stream.empty();
        log.info(String.valueOf(emptyStream));
    }

    @Test
    @DisplayName("create stream bases on array")
    void example2() {

        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        Stream<String> entireArrayStream = Arrays.stream(arr);
        Stream<String> partArrayStream = Arrays.stream(arr, 1, 4);
        log.info(String.valueOf(entireArrayStream));
        log.info(String.valueOf(partArrayStream));
    }


    @Test
    @DisplayName("create stream bases on collection")
    void example3() {
        Collection<String> collection = Arrays.asList("1", "2", "3");
        Stream<String> collectionStream = collection.stream();
        log.info(String.valueOf(collectionStream).toString());
    }

    @Test
    @DisplayName("create stream bases on Stream.Builder method")
    void example4() {
        Stream<Object> streamBuilder = Stream.builder().add("1").add("2").add("3").build();
        log.info(String.valueOf(streamBuilder).toString());

    }

    @Test
    @DisplayName("create stream bases on file ")
    void example5() throws IOException {
        Path path = Paths.get("/Users/yujiale/AllProject/PrivateProject/stream-api-examples/");
        Stream<String> fileStream = Files.lines(path);
        Stream<String> fileStreamWithCharset = Files.lines(path, StandardCharsets.UTF_8);
        log.info(fileStream.toString());
    }

    @Test
    @DisplayName("create stream bases on  Stream.iterate method")
    void example6() {
        // 创建一个连续元素的 stream,第1个元素是10，第2个元素是11，依此类推，直到元素数量达到size。
        Stream<Integer> iteratedStream = Stream.iterate(10, n -> n + 1).limit(10);
        log.info(iteratedStream.toString());
    }

    @Test
    @DisplayName("create stream bases on Stream.generate method")
    void example7() {
        // generate() 方法接受一个Supplier<T>来生成元素。因为流是无限的，所以我们需要设置流的size。下面会包含五个 ele 字符串
        Stream<String> generatedStream = Stream.generate(() -> "ele").limit(5);
        log.info(generatedStream.toString());
    }

    // 以下是基本类型 stream 的创建

    @Test
    @DisplayName("create stream bases on range function and rangeClosed function ")
    void example8() {
        // range(int start, int end) 方法会创建一个从start到end的有序流，它的步长是1，但是它不包括end
        IntStream intStream = (IntStream) IntStream.range(1, 3);//1,2
        // rangeClosed(int start, int end) 与range() 方法的区别在于，前者会包括end。
        LongStream longStream = LongStream.rangeClosed(1, 3);//1,2,3
        log.info(longStream.toString());
    }

    @Test
    @DisplayName("create stream bases on of function ")
    void example9() {
        int[] intArray = {1, 2, 3};
        IntStream intStream = IntStream.of(intArray);//1,2,3
        IntStream intStream2 = IntStream.of(1, 2, 3);//1,2,3
        long[] longArray = {1L, 2L, 3L};
        LongStream longStream = LongStream.of(longArray);//1,2,3
        LongStream longStream2 = LongStream.of(1L, 2L, 3L);//1,2,3
        double[] doubleArray = {1.0, 2.0, 3.0};
        DoubleStream doubleStream = DoubleStream.of(doubleArray);
        DoubleStream doubleStream2 = DoubleStream.of(1.0, 2.0, 3.0);//1.0,2.0,3.0
        log.info(doubleStream2.toString());
    }

    @Test
    @DisplayName("create stream bases on random class")
    void example10() {
        Random random = new Random();
        IntStream intStream = random.ints(3);
        LongStream longStream = random.longs(3);
        DoubleStream doubleStream = random.doubles(3);
        log.info(doubleStream.toString());
    }

    // 以下是字符串 create stream

    @Test
    @DisplayName("create stream bases on character ")
    void example11() {
        IntStream charStream = "abc".chars();
        log.info(charStream.toString());
    }


    @Test
    @DisplayName("create stream bases on character string ")
    void example12() {
        Stream<String> stringStream = Pattern.compile(",").splitAsStream("a,b,c");
        log.info(stringStream.toString());
    }
}
