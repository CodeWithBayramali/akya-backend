package com.codewithali.akyabutik.repository;

import com.codewithali.akyabutik.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact,String> {
}
