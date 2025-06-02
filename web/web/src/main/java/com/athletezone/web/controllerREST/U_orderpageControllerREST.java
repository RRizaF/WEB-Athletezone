package com.athletezone.web.controllerREST;

import com.athletezone.web.dto.OrderDTO;
import com.athletezone.web.models.Order;
import com.athletezone.web.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class U_orderpageControllerREST {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDTO> orderDTOs = orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }
}
