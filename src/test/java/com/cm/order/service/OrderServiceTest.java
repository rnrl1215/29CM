package com.cm.order.service;

import com.cm.order.domain.OrderProduct;
import com.cm.order.domain.Orders;
import com.cm.order.domain.Product;
import com.cm.order.dto.OrderPriceInfo;
import com.cm.order.repository.OrderRepository;
import com.cm.order.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

}