package com.kenect.contactsaggregator.testutils;

import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.LabContactsResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestContactUtilFactory {

    public static List<Contact> createContactList() {
        List<Contact> contactList = new ArrayList<>();

        Contact contact1 = new Contact();
        contact1.setId(1);
        contact1.setName("Mrs. Willian Bradtk");
        contact1.setEmail("jerold@example.net");
        contact1.setSource(null);
        contact1.setCreatedAt(LocalDateTime.parse("2020-06-24T19:37:16.688"));
        contact1.setUpdatedAt(LocalDateTime.parse("2020-06-24T19:37:16.688"));
        contactList.add(contact1);

        Contact contact2 = new Contact();
        contact2.setId(2);
        contact2.setName("Mr. Samuel Dunbar");
        contact2.setEmail("samuel.dunbar@example.org");
        contact2.setSource(null);
        contact2.setCreatedAt(LocalDateTime.parse("2021-01-15T14:25:50.123"));
        contact2.setUpdatedAt(LocalDateTime.parse("2021-02-10T10:10:10.456"));
        contactList.add(contact2);

        Contact contact3 = new Contact();
        contact3.setId(3);
        contact3.setName("Ms. Sophia Lee");
        contact3.setEmail("sophia.lee@domain.com");
        contact3.setSource(null);
        contact3.setCreatedAt(LocalDateTime.parse("2022-08-30T08:45:12.789"));
        contact3.setUpdatedAt(LocalDateTime.parse("2022-09-01T09:10:55.999"));
        contactList.add(contact3);

        return contactList;
    }

    public static LabContactsResponse createContactsResponse(List<Contact> expectedContactList) {
        LabContactsResponse labContactsResponse = new LabContactsResponse();
        labContactsResponse.setCurrentPage(1);
        labContactsResponse.setPageItems(3);
        labContactsResponse.setTotalCount(3);
        labContactsResponse.setTotalPages(1);
        labContactsResponse.setContactList(expectedContactList);
        return labContactsResponse;
    }

}
