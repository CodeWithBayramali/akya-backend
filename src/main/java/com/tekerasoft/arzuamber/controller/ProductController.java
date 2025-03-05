package com.tekerasoft.arzuamber.controller;

import com.tekerasoft.arzuamber.dto.ProductDto;
import com.tekerasoft.arzuamber.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<PagedModel<EntityModel<ProductDto>>> getAllProducts(@RequestParam int page,
                                                                              @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/get-all-new-season")
    public ResponseEntity<List<ProductDto>> getAllNewSeasonProduct(@RequestParam int page,
                                                                   @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllNewSeasonProduct(page, size));
    }

    @GetMapping("/get-all-populate")
    public ResponseEntity<List<ProductDto>> getAllPopulateProduct(@RequestParam int page,
                                                                   @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllPopulateProduct(page, size));
    }

    @GetMapping("/get-product")
    public ResponseEntity<ProductDto> getProduct(@RequestParam String slug) {
        return ResponseEntity.ok(productService.getProductBySlug(slug));
    }

    @GetMapping("/filter-product")
    public ResponseEntity<PagedModel<EntityModel<ProductDto>>> getProductFilter(
                                                             @RequestParam(required = false) String subCategory,
                                                             @RequestParam int page, @RequestParam int pageSize)
    {
        return ResponseEntity.ok(productService.filterProducts(subCategory,page,pageSize));
    }

}
