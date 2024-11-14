package com.codewithali.akyabutik.controller;

import com.codewithali.akyabutik.dto.ContactDto;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createContact(@RequestBody ContactDto contactDto) {
        return ResponseEntity.ok(contactService.createContact(contactDto));
    }
}
