package com.cm.order.dto;

import lombok.Getter;

@Getter
public class OrderPriceInfo {
    private final Integer orderAmount;
    private final Integer paymentAmount;

    public OrderPriceInfo(Integer orderAmount, Integer paymentAmount) {
        this.orderAmount = orderAmount;
        this.paymentAmount = paymentAmount;
    }
}
