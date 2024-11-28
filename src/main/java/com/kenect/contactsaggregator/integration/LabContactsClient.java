package com.kenect.contactsaggregator.integration;


import com.kenect.contactsaggregator.exception.LabContactsClientException;
import com.kenect.contactsaggregator.model.Contact;
import com.kenect.contactsaggregator.model.LabContactsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class LabContactsClient {


    @Value("${lab.contacts.url}")
    private String url;

    @Value("${lab.contacts.token}")
    private String token;

    private final RestTemplate restTemplate;

    public LabContactsClient() {
        this.restTemplate = new RestTemplate();
    }

    public Optional<LabContactsResponse> getAllContacts(Integer pageNumber, Integer pageSize) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ParameterizedTypeReference<List<Contact>> typeReference = new ParameterizedTypeReference<>() {};

            ResponseEntity<List<Contact>> response = restTemplate.exchange(
                    getFormatedUrl(pageNumber, pageSize),
                    HttpMethod.GET,
                    entity,
                    typeReference
            );

            List<Contact> contactsResponse = Objects.requireNonNull(response.getBody());
            LabContactsResponse labContactsResponse = new LabContactsResponse();
            labContactsResponse.setContactList(contactsResponse);

            retrieveHeadersPagination(response.getHeaders(), labContactsResponse);


            return Optional.of(labContactsResponse);
        } catch (Exception e) {
            String message = String.format("Exception during LabContacts API call. Message: %s", e.getMessage());
            log.error(message, e);
            throw new LabContactsClientException(message);
        }
    }

    private void retrieveHeadersPagination(HttpHeaders responseHeaders, LabContactsResponse labContactsResponse){
        responseHeaders.forEach((key, value) -> {
            String valueStr = value.stream().findFirst().orElse("0");
            switch (key) {
                case "current-page" -> labContactsResponse.setCurrentPage(Integer.parseInt(valueStr));
                case "page-items" -> labContactsResponse.setPageItems(Integer.parseInt(valueStr));
                case "total-count" -> labContactsResponse.setTotalCount(Integer.parseInt(valueStr));
                case "total-pages" -> labContactsResponse.setTotalPages(Integer.parseInt(valueStr));
                default -> log.trace("Unknown/no mapped key: {}", key);
            }
        });
    }

    private String getFormatedUrl(Integer pageNumber, Integer pageSize){
        return UriComponentsBuilder.fromUriString(url)
                .queryParamIfPresent("page", Optional.ofNullable(pageNumber))
                .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                .build()
                .toUriString();
    }

}
