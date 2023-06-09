package kr.co._29cm.homework.domain;

import kr.co._29cm.homework.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
    }

    public void removeQuantity(int quantity) {
        int remainQuantity = this.quantity - quantity;
        if (remainQuantity < 0) {
            throw new NotEnoughStockException();
        }
        this.quantity = remainQuantity;
    }

    @Builder
    public Product(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
