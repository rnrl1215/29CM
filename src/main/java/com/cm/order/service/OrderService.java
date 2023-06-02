package com.cm.order.service;

import com.cm.order.domain.Order;
import com.cm.order.domain.OrderProduct;
import com.cm.order.domain.Product;
import com.cm.order.exception.NotFoundProductException;
import com.cm.order.repository.OrderRepository;
import com.cm.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void order(Long productId, Integer quantity) {
        Product product = findProduct(productId);
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, quantity);
        Order order = Order.createOrder(orderProduct);
        orderRepository.save(order);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::new);
    }

    public int getTotalOrderPrice(Long orderId) {
        return  orderRepository
                .findById(orderId)
                .orElseThrow(NotFoundProductException::new)
                .getOrderPrice();
    }

    public List<Order> findByAllOrder() {
        return orderRepository.findAll();
    }
}
