package com.mercury.order;

public class OrderMapper {
    public static OrderResponse toOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getName());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalPrice(order.getTotalPrice());
        response.setItems(order.getOrderItems().stream()
                .map(OrderMapper::toOrderItemResponse)
                .toList());
        return response;
    }
    public static OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setProductId(orderItem.getProduct().getId());
        response.setProductName(orderItem.getProduct().getName());
        response.setQuantity(orderItem.getQuantity());
        response.setPriceAtPurchase(orderItem.getPriceAtPurchase());
        return response;
    }
}
