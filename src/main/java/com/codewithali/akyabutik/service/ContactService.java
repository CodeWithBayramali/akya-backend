package com.codewithali.akyabutik.service;

import com.codewithali.akyabutik.dto.ContactDto;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.repository.ContactRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ApiResponse<?> createContact(ContactDto contactDto) {
        contactRepository.save(ContactDto.convertToEntity(contactDto));
        return new ApiResponse<>("Gönderildi en kısa sürede dönüş sağlayacağız",null,true);
    }

    public List<ContactDto> getAllContacts(int page, int size) {
        return contactRepository.findAll(PageRequest.of(page,size, Sort.Direction.DESC, "createdAt"))
                .map(ContactDto::convertToDto)
                .stream().toList();
    }

    public ApiResponse<?> delteContact(String contactId) {
        contactRepository.deleteById(contactId);
        return new ApiResponse<>("Silindi",null,true);
    }
}
