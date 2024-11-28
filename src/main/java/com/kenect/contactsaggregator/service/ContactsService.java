package com.kenect.contactsaggregator.service;

import com.kenect.contactsaggregator.model.ContactDTO;

import java.util.List;

public interface ContactsService {

    List<ContactDTO> getAllContacts();
    List<ContactDTO> getAllContacts(Integer pageSize);

}
