package com.codewithali.akyabutik.controller;

import com.codewithali.akyabutik.dto.ProductDto;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.service.ProductService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/v1/api/product")
public class ProductController {

    private final ProductService productService;
    private final ResourceLoader resourceLoader;

    public ProductController(ProductService productService, ResourceLoader resourceLoader) {
        this.productService = productService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/get-product-category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryName));
    }

    @GetMapping("/get-new-products")
    public ResponseEntity<List<ProductDto>> getNewProducts(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllNewProducts(page,size));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> deleteProductById(@PathVariable String id) {
       return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        try {
            Resource resource = resourceLoader.getResource("file:uploads/"+filename);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = Files.probeContentType(resource.getFile().toPath());
            return ResponseEntity.ok()
                    .contentType(contentType != null ? MediaType.parseMediaType(contentType): MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }

    }
}
