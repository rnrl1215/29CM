package kr.co._29cm.homework.service;

import kr.co._29cm.homework.domain.Orders;
import kr.co._29cm.homework.domain.OrderProduct;
import kr.co._29cm.homework.domain.Product;
import kr.co._29cm.homework.dto.OrderPriceInfo;
import kr.co._29cm.homework.exception.NotFoundOrderException;
import kr.co._29cm.homework.exception.NotFoundProductException;
import kr.co._29cm.homework.repository.OrderRepository;
import kr.co._29cm.homework.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Orders order = Orders.createOrder(orderProduct);
        return orderRepository.save(order).getId();
    }

    @Transactional
    public Orders order(Long orderId, Long productId, Integer quantity) {
        Orders findOrder = findByOrder(orderId);
        Product product = findProduct(productId);
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, quantity);
        findOrder.addOrderProduct(orderProduct);
        return findOrder;
    }

    public OrderPriceInfo getTotalOrderPrice(Long orderId) {
        int orderAmount = orderRepository
                .findById(orderId)
                .orElseThrow(NotFoundProductException::new)
                .getOrderPrice();

        int paymentAmount = orderAmount;
        if (orderAmount < 50000) {
            paymentAmount += 2500;
        }

       return new OrderPriceInfo(orderAmount, paymentAmount);
    }

    public Orders findByOrder(Long orderId) {
         return orderRepository.findById(orderId)
                 .orElseThrow(NotFoundOrderException::new);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::new);
    }

    public List<OrderProduct> findOrderProductsByOrderId(Long orderId) {
        List<OrderProduct> orderProducts
                = orderRepository.findById(orderId)
                                 .orElseThrow(NotFoundOrderException::new)
                                 .getOrderProducts();
        return orderProducts;
    }

    public void printOrderProducts(Long orderId) {
        List<OrderProduct> orderProductsByOrderId = findOrderProductsByOrderId(orderId);
        System.out.println("주문내역");
        System.out.println("-------------------------------------------------");
        for (OrderProduct orderProduct : orderProductsByOrderId) {
            System.out.print(orderProduct.getProduct().getName());
            System.out.print(" - ");
            System.out.print(orderProduct.getQuantity());
            System.out.println("");
        }
        OrderPriceInfo totalOrderPrice = getTotalOrderPrice(orderId);
        System.out.println("-------------------------------------------------");
        System.out.println("주문금액: " + totalOrderPrice.getOrderAmount());
        System.out.println("-------------------------------------------------");
        System.out.println("지불금액: " + totalOrderPrice.getPaymentAmount());
        System.out.println("-------------------------------------------------");
    }
}
