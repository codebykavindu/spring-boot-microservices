package com.codebykavindu.bookstore.orders.jobs;

import com.codebykavindu.bookstore.orders.domain.OrderService;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Kavindu Perera
 */
@Component
class OrderProcessingJob {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessingJob.class);

    private final OrderService orderService;

    OrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${orders.new-orders-job-cron}")
    public void processNewOrders() {
        log.info("Processing new orders at {}", Instant.now());
        orderService.processNewOrders();
    }
}
