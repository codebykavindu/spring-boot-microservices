package com.codebykavindu.bookstore.orders.web.controllers;

import com.codebykavindu.bookstore.orders.domain.OrderService;
import com.codebykavindu.bookstore.orders.domain.SecurityService;
import com.codebykavindu.bookstore.orders.domain.models.CreateOrderRequest;
import com.codebykavindu.bookstore.orders.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kavindu Perera
 */
@RestController
@RequestMapping("api/v1/orders")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginUsername();
        log.info("Creating order for user={}", userName);
        return orderService.createOrder(userName, request);
    }
}
