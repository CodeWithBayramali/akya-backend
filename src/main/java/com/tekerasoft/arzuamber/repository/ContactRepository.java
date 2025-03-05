package com.tekerasoft.arzuamber.repository;

import com.tekerasoft.arzuamber.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Page<Contact> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
