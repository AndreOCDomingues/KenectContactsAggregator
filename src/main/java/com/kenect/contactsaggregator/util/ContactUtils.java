package com.kenect.contactsaggregator.util;

import com.kenect.contactsaggregator.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContactUtils {

    public static Contact addSource(Contact contact, String source) {
        log.debug("Adding source: {} to the contact {}", source, contact);
        contact.setSource(source);
        return contact;
    }

}
