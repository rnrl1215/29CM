package com.cm.order;

import com.cm.order.domain.OrderProduct;
import com.cm.order.domain.Orders;
import com.cm.order.domain.Product;
import com.cm.order.exception.OrderException;
import com.cm.order.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class OrderApplication {

	private static OrderService staticOrderService;
	private final OrderService orderService;

	@PostConstruct
	void init() {
		staticOrderService = orderService;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(OrderApplication.class, args);
		Scanner sc = new Scanner(System.in);
		Long order = null;

		while (true) {
			System.out.print("상품번호: ");
			String productId = sc.next();
			if(productId.equals("q")) {
				break;
			}

			System.out.print("수량: ");
			int quantity = sc.nextInt();
			System.out.println();

			try {
				if (order == null) {
					order = staticOrderService.order(Long.valueOf(productId), quantity);
				} else {
					staticOrderService.order(order,Long.valueOf(productId), quantity);
				}
			} catch (OrderException e) {
				log.info(e.getMessage());
			} catch (Exception e) {
				break;
			}
		}
		staticOrderService.printOrderProducts(order);
		run.close();
	}
}
