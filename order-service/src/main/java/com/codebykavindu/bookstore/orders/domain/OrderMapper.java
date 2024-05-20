package com.codebykavindu.bookstore.orders.domain;

import com.codebykavindu.bookstore.orders.domain.models.CreateOrderRequest;
import com.codebykavindu.bookstore.orders.domain.models.OrderItem;
import com.codebykavindu.bookstore.orders.domain.models.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Kavindu Perera
 */
class OrderMapper {
    private OrderMapper() {}

    static OrderEntity convertToEntity(CreateOrderRequest request) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItemEntity> orderItems = new HashSet<>();
        for (OrderItem item : request.items()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }
}