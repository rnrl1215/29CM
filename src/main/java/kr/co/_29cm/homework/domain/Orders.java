package kr.co._29cm.homework.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public static Orders createOrder(OrderProduct orderProduct) {
        Orders order = new Orders();
        order.getOrderProducts().add(orderProduct);
        orderProduct.setOrder(order);
        return order;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);
        orderProducts.add(orderProduct);
    }

    public int getOrderPrice() {
        return orderProducts.
                stream().
                mapToInt(OrderProduct::getTotalPrice)
                .sum();
    }
}
