package com.kenect.contactsaggregator.model.mapper;

import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.ContactResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ContactToContactResponse {

    public ContactResponse mapper(Contact contact){
        ContactResponse contactResponse = new ContactResponse();
        BeanUtils.copyProperties(contact, contactResponse);
        return contactResponse;
    }

}
