package com.codewithali.akyabutik.repository;

import com.codewithali.akyabutik.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> getProductsBySubCategory(String subCategory);

}
