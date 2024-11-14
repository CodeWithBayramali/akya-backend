package com.codewithali.akyabutik.service;

import com.codewithali.akyabutik.dto.OrderDto;
import com.codewithali.akyabutik.dto.request.CreateOrderRequest;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public ApiResponse<OrderDto> createOrder(CreateOrderRequest req) {
        try {
           req.getOrderProducts().forEach(p -> {
               productService.decreaseProduct(p.getProductId(),p.getColor(),p.getSize(),p.getQuantity());
           });
            orderRepository.save(CreateOrderRequest.from(req));
            return new ApiResponse<>("Siparişiniz oluşturuldu",null,true);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(),null,false);
        }
    }

    public ApiResponse<OrderDto> updateOrder(OrderDto order) {
        try {
            orderRepository.save(OrderDto.convertToEntity(order));
            return new ApiResponse<>("Siparişiniz Güncellendi", null, true);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(),null,false);
        }
    }

    public List<OrderDto> getOrders(int page, int size) {
       return orderRepository.findAll(PageRequest.of(page, size))
               .map(OrderDto::convertToDto)
               .stream().toList();
    }

    public OrderDto getOrder(String id) {
        return OrderDto.convertToDto(orderRepository.findById(id)
                .orElseThrow());
    }
}
