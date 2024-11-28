package com.kenect.contactsaggregator.service;

import com.kenect.contactsaggregator.exception.LabContactsClientException;
import com.kenect.contactsaggregator.exception.NoContactsFoundException;
import com.kenect.contactsaggregator.integration.LabContactsClient;
import com.kenect.contactsaggregator.mapper.ContactToContactDTOMapper;
import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.ContactDTO;
import com.kenect.contactsaggregator.model.LabContactsResponse;
import com.kenect.contactsaggregator.testutils.TestContactUtilFactory;
import com.kenect.contactsaggregator.util.Constants;
import com.kenect.contactsaggregator.util.ContactUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ContactsServiceImplTest {

    @InjectMocks
    private ContactsServiceImpl contactsService;

    @Mock
    private LabContactsClient labContactsClient;


    private LabContactsResponse expectedLabContactsResponse;
    private List<ContactDTO> expectedContactDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Contact> contactList = TestContactUtilFactory.createContactList();
        expectedLabContactsResponse = TestContactUtilFactory.createContactsResponse((contactList.stream().toList()));

        expectedContactDTO = new ArrayList<>();
        expectedContactDTO.addAll(contactList.stream()
                .map(c -> ContactUtils.addSource(c, Constants.KENECT_LABS))
                .map(ContactToContactDTOMapper::mapper)
                .toList());
    }

    @Test
    void whenGetAllContactsWithoutPageSize_shouldReturnAllContacts() {
        when(labContactsClient.getAllContacts(anyInt(), any())).thenReturn(Optional.of(expectedLabContactsResponse));

        List<ContactDTO> response = contactsService.getAllContacts();

        Assertions.assertEquals(expectedContactDTO, response);
    }

    @Test
    void whenGetAllContactsWitPageSize_shouldReturnAllContacts() {
        when(labContactsClient.getAllContacts(anyInt(), any())).thenReturn(Optional.of(expectedLabContactsResponse));

        List<ContactDTO> response = contactsService.getAllContacts(7);

        Assertions.assertEquals(expectedContactDTO, response);
    }

    @Test
    void whenNoContactsFound_thenThrowNoContactsFoundException(){
        when(labContactsClient.getAllContacts(anyInt(), any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoContactsFoundException.class, () -> contactsService.getAllContacts());
        Assertions.assertThrows(NoContactsFoundException.class, () -> contactsService.getAllContacts(5));
    }

    @Test
    void whenLabContactThrowsAnException_thenThrowNoContactsFoundException(){
        when(labContactsClient.getAllContacts(anyInt(), any())).thenThrow(new LabContactsClientException("Testing errors"));

        Assertions.assertThrows(LabContactsClientException.class, () -> contactsService.getAllContacts());
        Assertions.assertThrows(LabContactsClientException.class, () -> contactsService.getAllContacts(5));
    }
}