package com.cm.order.domain;

import com.cm.order.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderProduct(Product product) {
        this.product = product;
    }

    private BigDecimal orderPrice;
    private int quantity;

    public void setOrderPrice (BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }

    public static OrderProduct createOrderProduct(Product product, int quantity) throws NotEnoughStockException {
        OrderProduct orderProduct = new OrderProduct(product);
        orderProduct.setOrderPrice(product.getPrice());
        orderProduct.setQuantity(quantity);
        product.removeQuantity(quantity);
        product.addOrderProduct(orderProduct);
        return orderProduct;
    }

    public int getTotalPrice() {
         return this.orderPrice
                 .multiply(BigDecimal.valueOf(quantity)).intValue();
    }
}
