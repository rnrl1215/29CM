package com.cm.order.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderResponseDto {
    List<OrderProduct> orders;
    Integer orderPrice;
    Integer totalPrice;
    boolean isFressShiping;

    public OrderResponseDto(List<OrderProduct> orders,
                            Integer orderPrice,
                            Integer totalPrice,
                            boolean isFressShiping) {
        this.orders = orders;
        this.orderPrice = orderPrice;
        this.totalPrice = totalPrice;
        this.isFressShiping = isFressShiping;
    }
}
