package com.codewithali.akyabutik.controller;

import com.codewithali.akyabutik.dto.OrderDto;
import com.codewithali.akyabutik.dto.request.CreateOrderRequest;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }
}
