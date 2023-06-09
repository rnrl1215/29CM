package kr.co._29cm.homework;

import kr.co._29cm.homework.exception.OrderException;
import kr.co._29cm.homework.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

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
			String productId = sc.nextLine();
			if(productId.equals("q")) {
				break;
			}

			System.out.print("수량: ");
			String  quantity = sc.nextLine();
			System.out.println();

			if (!StringUtils.hasText(productId) && StringUtils.hasText(quantity)) {
				break;
			}

			try {
				if (order == null) {
					order = staticOrderService.order(Long.valueOf(productId), Integer.valueOf(quantity));
				} else {
					staticOrderService.order(order,Long.valueOf(productId), Integer.valueOf(quantity));
				}
			} catch (OrderException e) {
				log.info(e.getMessage());
			} catch (Exception e) {
				break;
			}
		}

		if (order != null) {
			staticOrderService.printOrderProducts(order);
		}

		run.close();
	}
}
