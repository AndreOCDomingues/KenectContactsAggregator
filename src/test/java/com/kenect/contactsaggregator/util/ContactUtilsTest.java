package com.kenect.contactsaggregator.util;

import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.testutils.TestContactUtilFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ContactUtilsTest {

    public static final String TEST_SOURCE = "TEST_SOURCE";
    private List<Contact> expectedContactList;

    @BeforeEach
    void setUp() {
        expectedContactList = TestContactUtilFactory.createContactList();
        for(Contact contact : expectedContactList){
            contact.setSource(TEST_SOURCE);
        }
    }

    @Test
    void whenAddSource_thenSourceShouldBeAddedToContact() {
        List<Contact> contactsToAddSource = TestContactUtilFactory.createContactList();
        contactsToAddSource.forEach(c -> ContactUtils.addSource(c, TEST_SOURCE));

        Assertions.assertEquals(expectedContactList, contactsToAddSource);
    }

}