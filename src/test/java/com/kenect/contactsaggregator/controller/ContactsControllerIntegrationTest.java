package com.kenect.contactsaggregator.controller;

import com.kenect.contactsaggregator.exception.LabContactsClientException;
import com.kenect.contactsaggregator.integration.LabContactsClient;
import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.LabContactsResponse;
import com.kenect.contactsaggregator.testutils.TestContactUtilFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ContactsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LabContactsClient labContactsClient;

    private LabContactsResponse labContactsResponse;

    @BeforeEach
    void setUp() {
        List<Contact> contactList = TestContactUtilFactory.createContactList();
        labContactsResponse = TestContactUtilFactory.createContactsResponse(contactList.stream().toList());
    }

    @Test
    void whenGetAllContacts_shouldReturnOK() throws Exception {
        when(labContactsClient.getAllContacts(anyInt(), any())).thenReturn(Optional.of(labContactsResponse));

        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk());
    }

    @Test
    void whenThrowNoContactsFoundException_shouldReturnNotFound() throws Exception {
        when(labContactsClient.getAllContacts(anyInt(), any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/contacts"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenThrowLabContactsClientException_shouldReturnNotFound() throws Exception {
        when(labContactsClient.getAllContacts(anyInt(), any())).thenThrow(new LabContactsClientException("Testing exceptions"));

        mockMvc.perform(get("/contacts"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenThrowAGenericException_shouldReturnInternalServerError() throws Exception {
        when(labContactsClient.getAllContacts(anyInt(), any())).thenThrow(new NullPointerException("Testing generic exception"));

        mockMvc.perform(get("/contacts"))
                .andExpect(status().is5xxServerError());
    }

}