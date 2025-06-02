package com.athletezone.web.dto;

import com.athletezone.web.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private String username;
    private String createdOn;
    private String cartSummary;
    private double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String address;

    public static OrderDTO fromOrder(Order order) {
        String created = order.getCreatedOn().toString(); // Optional: pakai formatter

        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .createdOn(created)
                .cartSummary(order.getCart().getCartItems().stream()
                        .map(item -> item.getProduct().getName() + " x" + item.getQuantity())
                        .reduce((a, b) -> a + ", " + b).orElse("Kosong"))
                .totalAmount(order.getTotalPrice())
                .paymentMethod(order.getPayment().getPaymentMethod())
                .paymentStatus(order.getPayment().getStatus())
                .address(order.getPayment().getAddress())
                .build();
    }
}
