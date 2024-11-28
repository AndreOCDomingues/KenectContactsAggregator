package com.kenect.contactsaggregator.controller;

import com.kenect.contactsaggregator.model.ContactDTO;
import com.kenect.contactsaggregator.service.ContactsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class ContactsController {

    private ContactsService contactsService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("Received request to fetch all contacts");

        List<ContactDTO> contacts = contactsService.getAllContacts();
        log.info("Successfully retrieved {} contacts", contacts.size());

        return ResponseEntity.ok(contacts);
    }


}
