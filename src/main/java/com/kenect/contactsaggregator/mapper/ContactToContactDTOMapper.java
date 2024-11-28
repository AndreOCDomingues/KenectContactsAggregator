package com.kenect.contactsaggregator.mapper;

import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.ContactDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ContactToContactDTOMapper {

    public static ContactDTO mapper(Contact contact){
        ContactDTO contactDTO = new ContactDTO();
        BeanUtils.copyProperties(contact, contactDTO);
        return contactDTO;
    }

}
