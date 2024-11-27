package com.kenect.contactsaggregator.service;

import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.ContactResponse;
import com.kenect.contactsaggregator.model.mapper.ContactToContactResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactsServiceImpl implements ContactsService {

    private LabContacts labContacts;

    private ContactToContactResponse contactToContactResponse;

    @Override
    public List<ContactResponse> getAllContacts() {
        List<Contact> contacts = labContacts.getAllContacts();
        return contacts
                .stream()
                .map(contactToContactResponse::mapper)
                .toList();
    }

}
