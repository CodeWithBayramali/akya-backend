package com.codewithali.akyabutik.controller;

import com.codewithali.akyabutik.dto.ContactDto;
import com.codewithali.akyabutik.dto.OrderDto;
import com.codewithali.akyabutik.dto.ProductDto;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.service.ContactService;
import com.codewithali.akyabutik.service.OrderService;
import com.codewithali.akyabutik.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {
    private final ProductService productService;
    private final ContactService contactService;
    private final OrderService orderService;

    public AdminController(ProductService productService, ContactService contactService, OrderService orderService) {
        this.productService = productService;
        this.contactService = contactService;
        this.orderService = orderService;
    }

    @PostMapping("/create-product")
    public ResponseEntity<ApiResponse<?>> createProduct(@RequestPart("productJson") String dto,
                                                        @RequestParam(value = "image",required = false) MultipartFile[] images) {
        return ResponseEntity.ok(productService.createProduct(dto,images));
    }

    @PutMapping("/update-product")
    public ResponseEntity<ApiResponse<?>> updateProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(dto));
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(contactService.getAllContacts(page,size));
    }

    @PutMapping("/update-stock")
    public ResponseEntity<ApiResponse<?>> updateStock(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateStock(dto));
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(orderService.getOrders(page, size));
    }
}
