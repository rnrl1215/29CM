package com.cm.order.domain;

import com.cm.order.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @Column(name = "order_price")
    private BigDecimal orderPrice;


    @Column(name = "quantity")
    private Integer quantity;


    public OrderProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

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
         return orderPrice
                 .multiply(BigDecimal.valueOf(quantity)).intValue();
    }
}
