package com.java.demo.streamapi;

import com.java.demo.streamapi.models.Customer;
import com.java.demo.streamapi.models.Order;
import com.java.demo.streamapi.models.Product;
import com.java.demo.streamapi.repos.CustomerRepo;
import com.java.demo.streamapi.repos.OrderRepo;
import com.java.demo.streamapi.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@DataJpaTest
class StreamApiTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
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
    @DisplayName("Obtain a list of product with category = \"Books\" and price > 100")
    void exercise1() {

        List<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .filter(p -> p.getPrice() > 100)
                .collect(Collectors.toList());
        result.forEach(p -> log.info(p.toString()));
    }

    @Test
    @DisplayName("Obtain a list of product with category = \"Books\" and price > 100 (using Predicate chaining for filter)")
    void exercise1a() {
        Predicate<Product> categoryFilter = product -> product.getCategory().equalsIgnoreCase("Books");
        Predicate<Product> priceFilter = product -> product.getPrice() > 100;
        List<Product> result = productRepo.findAll()
                .stream()
                .filter(product -> categoryFilter.and(priceFilter).test(product))
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        result.forEach(p -> log.info(p.toString()));
    }

    @Test
    @DisplayName("Obtain a list of product with category = \"Books\" and price > 100 (using BiPredicate for filter)")
    void exercise1b() {
        BiPredicate<Product, String> categoryFilter = (product, category) -> product.getCategory().equalsIgnoreCase(category);
        List<Product> result = productRepo.findAll()
                .stream()
                .filter(product -> categoryFilter.test(product, "Books") && product.getPrice() > 100)
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        result.forEach(p -> log.info(p.toString()));
    }

    @Test
    @DisplayName("Obtain a list of order with product category = \"Baby\"")
    void exercise2() {
        List<Order> result = orderRepo.findAll()
                .stream()
                .filter(o ->
                        o.getProducts()
                                .stream()
                                .anyMatch(p -> p.getCategory().equalsIgnoreCase("Baby"))
                )
                .collect(Collectors.toList());
        result.forEach(o -> log.info(o.toString()));

    }

    @Test
    @DisplayName("Obtain a list of product with category = “Toys” and then apply 10% discount\"")
    void exercise3() {
        List<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Toys"))
                .map(p -> p.withPrice(p.getPrice() * 0.9))
                .collect(Collectors.toList());
        result.forEach(o -> log.info(o.toString()));

    }

    @Test
    @DisplayName("Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021")
    void exercise4() {
        List<Product> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getCustomer().getTier() == 2)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 4, 1)) <= 0)
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        result.forEach(o -> log.info(o.toString()));
    }

    @Test
    @DisplayName("Get the 3 cheapest products of \"Books\" category")
    void exercise5() {
//              Optional<Product> result = productRepo.findAll()
//                              .stream()
//                              .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
//                              .sorted(Comparator.comparing(Product::getPrice))
//                              .findFirst();

        Optional<Product> result = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .min(Comparator.comparing(Product::getPrice));
        log.info(result.get().toString());

    }

    @Test
    @DisplayName("Get the 3 most recent placed order")
    void exercise6() {
        List<Order> result = orderRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
        result.forEach(o -> log.info(o.toString()));
    }

    @Test
    @DisplayName("Get a list of products which was ordered on 15-Mar-2021")
    void exercise7() {
        List<Product> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(o -> System.out.println(o.toString()))
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        result.forEach(o -> log.info(o.toString()));
    }

    @Test
    @DisplayName("Calculate the total lump of all orders placed in Feb 2021")
    void exercise8() {
        double result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 3, 1)) < 0)
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        log.info("Total lump sum = " + result);
    }

    @Test
    @DisplayName("Calculate the total lump of all orders placed in Feb 2021 (using reduce with BiFunction)")
    void exercise8a() {
        BiFunction<Double, Product, Double> accumulator = (acc, product) -> acc + product.getPrice();
        double result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 3, 1)) < 0)
                .flatMap(o -> o.getProducts().stream())
                .reduce(0D, accumulator, Double::sum);
        log.info("Total lump sum = " + result);
    }

    @Test
    @DisplayName("Calculate the average price of all orders placed on 15-Mar-2021")
    void exercise9() {
        double result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average().getAsDouble();
        log.info("Average = " + result);
    }

    @Test
    @DisplayName("Obtain statistics summary of all products belong to \"Books\" category")
    void exercise10() {
        DoubleSummaryStatistics statistics = productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("Books"))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();

        log.info(String.format("count = %1$d, average = %2$f, max = %3$f, min = %4$f, sum = %5$f",
                statistics.getCount(), statistics.getAverage(), statistics.getMax(), statistics.getMin(), statistics.getSum()));

    }

    @Test
    @DisplayName("Obtain a mapping of order id and the order's product count")
    void exercise11() {
        Map<Long, Integer> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Order::getId,
                                order -> order.getProducts().size())
                );

        long endTime = System.currentTimeMillis();
        log.info(result.toString());
    }

    @Test
    @DisplayName("Obtain a data map of customer and list of orders")
    void exercise12() {
        Map<Customer, List<Order>> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
        log.info(result.toString());
    }

    @Test
    @DisplayName("Obtain a data map of customer_id and list of order_id(s)")
    void exercise12a() {
        HashMap<Long, List<Long>> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                order -> order.getCustomer().getId(),
                                HashMap::new,
                                Collectors.mapping(Order::getId, Collectors.toList())));
        log.info(result.toString());
    }

    @Test
    @DisplayName("Obtain a data map with order and its total price")
    void exercise13() {
        Map<Order, Double> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                order -> order.getProducts().stream()
                                        .mapToDouble(Product::getPrice).sum())
                );
        log.info(result.toString());
    }

    @Test
    @DisplayName("Obtain a data map with order and its total price (using reduce)")
    void exercise13a() {
        Map<Long, Double> result = orderRepo.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Order::getId,
                                order -> order.getProducts().stream()
                                        .reduce(0D, (acc, product) -> acc + product.getPrice(), Double::sum)
                        ));
        log.info(result.toString());
    }

    @Test
    @DisplayName("Obtain a data map of product name by category")
    void exercise14() {
        Map<String, List<String>> result = productRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.mapping(Product::getName, Collectors.toList()))
                );
        log.info(result.toString());
    }

    @Test
    @DisplayName("Get the most expensive product per category")
    void exercise15() {

        Map<String, Optional<Product>> result = productRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)))
                );
        log.info(result.toString());
        result.forEach((k, v) -> log.info("key=" + k + ", value=" + v.get()));
    }

    @Test
    @DisplayName("Get the most expensive product (by name) per category")
    void exercise15a() {
        Map<String, String> result = productRepo.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingDouble(Product::getPrice)),
                                        optionalProduct -> optionalProduct.map(Product::getName).orElse(null)
                                )
                        ));

        log.info(result.toString());
    }

}
