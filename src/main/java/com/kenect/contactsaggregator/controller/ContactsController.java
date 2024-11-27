package com.kenect.contactsaggregator.controller;

import com.kenect.contactsaggregator.model.ContactResponse;
import com.kenect.contactsaggregator.service.ContactsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class ContactsController {

    private ContactsService contactsService;

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAllContacts(){
        return ResponseEntity.ok(contactsService.getAllContacts());
    }


}
