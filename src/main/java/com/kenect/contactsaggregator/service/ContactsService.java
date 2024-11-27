package com.kenect.contactsaggregator.service;

import com.kenect.contactsaggregator.model.ContactResponse;

import java.util.List;

public interface ContactsService {

    List<ContactResponse> getAllContacts();

}
