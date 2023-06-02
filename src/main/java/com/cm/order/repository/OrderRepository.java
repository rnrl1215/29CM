package com.cm.order.repository;

import com.cm.order.domain.Order;
import com.cm.order.domain.OrderProduct;
import com.cm.order.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
