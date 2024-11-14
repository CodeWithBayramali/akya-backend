package com.codewithali.akyabutik.repository;

import com.codewithali.akyabutik.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
