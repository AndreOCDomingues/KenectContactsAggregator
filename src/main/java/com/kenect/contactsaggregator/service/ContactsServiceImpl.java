package com.kenect.contactsaggregator.service;

import com.kenect.contactsaggregator.exception.NoContactsFoundException;
import com.kenect.contactsaggregator.integration.LabContactsClient;
import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.ContactDTO;
import com.kenect.contactsaggregator.model.LabContactsResponse;
import com.kenect.contactsaggregator.mapper.ContactToContactDTOMapper;
import com.kenect.contactsaggregator.util.Constants;
import com.kenect.contactsaggregator.util.ContactUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ContactsServiceImpl implements ContactsService {

    private final LabContactsClient labContactsClient;

    @Override
    public List<ContactDTO> getAllContacts() {
        return getAllContacts(null);
    }

    @Override
    public List<ContactDTO> getAllContacts(Integer pageSize) {
        log.info("Fetching all contacts {}", pageSize == null
                ? "without a page size..."
                : "with a page size of " + pageSize + "...");

        int currentPage = 1;
        int totalPages;

        List<Contact> contactList = new ArrayList<>();

        do {
            Optional<LabContactsResponse> labContactsResponse = labContactsClient.getAllContacts(currentPage, pageSize);

            if (labContactsResponse.isEmpty()) {
                log.warn("No contacts found for page {}", currentPage);
                break;
            }

            LabContactsResponse response = labContactsResponse.get();
            totalPages = response.getTotalPages();

            contactList.addAll(response.getContactList());

            log.info("Processing page {} of {}", currentPage, totalPages);
            currentPage++;
        } while (currentPage <= totalPages);

        if (contactList.isEmpty())
            throw new NoContactsFoundException("No contacts found after fetching all pages.");


        return contactList.stream()
                .map(c -> ContactUtils.addSource(c, Constants.KENECT_LABS))
                .map(ContactToContactDTOMapper::mapper)
                .toList();
    }

}
