package com.cm.order.service;

import com.cm.order.domain.Order;
import com.cm.order.domain.OrderProduct;
import com.cm.order.domain.Product;
import com.cm.order.exception.NotFoundOrderException;
import com.cm.order.exception.NotFoundProductException;
import com.cm.order.repository.OrderRepository;
import com.cm.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long order(Long productId, Integer quantity) {
        Product product = findProduct(productId);
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, quantity);
        Order order = Order.createOrder(orderProduct);
        return orderRepository.save(order).getId();
    }

    @Transactional
    public void order(Long orderId, Long productId, Integer quantity) {
        Order findOrder = findByOrder(orderId);
        Product product = findProduct(productId);
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, quantity);
        findOrder.addOrderProduct(orderProduct);
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

    public Order findByOrder(Long orderId) {
         return orderRepository.findById(orderId)
                 .orElseThrow(NotFoundOrderException::new);
    }
}
