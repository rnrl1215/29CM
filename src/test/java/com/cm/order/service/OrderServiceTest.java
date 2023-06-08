package com.cm.order.service;

import com.cm.order.domain.OrderProduct;
import com.cm.order.domain.Orders;
import com.cm.order.domain.Product;
import com.cm.order.dto.OrderPriceInfo;
import com.cm.order.exception.NotEnoughStockException;
import com.cm.order.repository.OrderRepository;
import com.cm.order.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getTotalOrderPriceTest() {
        // given
        Long orderId = 1L;

        // when
        Product item1 = Product.builder().price(BigDecimal.valueOf(100))
                .name("item1")
                .quantity(2)
                .build();

        Product item2 = Product.builder().price(BigDecimal.valueOf(200))
                .name("item2")
                .quantity(3)
                .build();

        OrderProduct orderProduct1 = OrderProduct.createOrderProduct(item1, 1);
        OrderProduct orderProduct2 = OrderProduct.createOrderProduct(item2, 1);

        Orders order = Orders.createOrder(orderProduct1);
        order.addOrderProduct(orderProduct2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // then
        OrderPriceInfo totalOrderPrice = orderService.getTotalOrderPrice(orderId);

        assertThat(totalOrderPrice).isNotNull();
        assertThat(totalOrderPrice.getOrderAmount()).isEqualTo(300);
        assertThat(totalOrderPrice.getPaymentAmount()).isEqualTo(2800);
    }



    @Test
    void exceedOrderPriceTest() {
        // given
        Long orderId = 1L;

        // when
        Product item1 = Product.builder().price(BigDecimal.valueOf(100))
                .name("item1")
                .quantity(2)
                .build();

        // then
        Assertions.assertThatThrownBy(()->OrderProduct.createOrderProduct(item1, 3))
                .isInstanceOf(NotEnoughStockException.class);
    }


    @Test
    void concurrentOrderTest() throws InterruptedException {

        // given
        Long productId1 = 1L;
        Long productId2 = 2L;
        Long productId3 = 3L;

        Product product1 = Product.builder().price(BigDecimal.valueOf(100))
                .name("1")
                .quantity(3)
                .build();

        Product product2 = Product.builder().price(BigDecimal.valueOf(200))
                .name("2")
                .quantity(3)
                .build();


        Product product3 = Product.builder()
                .price(BigDecimal.valueOf(300))
                .name("3")
                .quantity(3)
                .build();

        OrderProduct orderProduct1 = OrderProduct.createOrderProduct(product1, 1);

        Orders order1 = Orders.createOrder(orderProduct1);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));
        when(productRepository.findById(productId3)).thenReturn(Optional.of(product3));

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        // when
        AtomicReference<Orders> order = new AtomicReference<>();
        for (int i = 1; i <= 3; i++) {
            long finalI = i;
            executorService.execute(() -> {
                order.set(orderService.order(1L, finalI, 1));
                countDownLatch.countDown();
            });
        }

        // then
        countDownLatch.await();

        Orders orders = order.get();
        List<OrderProduct> orderProducts = orders.getOrderProducts();
        for (OrderProduct orderProduct : orderProducts) {
            BigDecimal price = orderProduct.getProduct().getPrice();
            String name = orderProduct.getProduct().getName();
            Assertions.assertThat(price.intValue()).isEqualTo(Integer.valueOf(name)*100);
        }
    }
}