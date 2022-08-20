package com.java.demo.streamapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @Author yujiale
 * @Date 2022/8/20 15:37
 * @Description stream collect use 集合操作
 **/
@Slf4j
@DataJpaTest
class StreamApiCollectUseTest {

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
    @DisplayName("stream collection agv use ")

    void exampleByAvg(){

    }


}
