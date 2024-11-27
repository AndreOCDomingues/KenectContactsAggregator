package com.kenect.contactsaggregator.service;


import com.kenect.contactsaggregator.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class LabContacts {

    public static final String KENECT_LABS = "KENECT_LABS";

    @Value("${lab.contacts.url}")
    private String url;

    @Value("${lab.contacts.token}")
    private String token;

    private final String pageNumber;
    private final String pageSize;

    private Integer currentPage = 1;
    private Integer pageItems = 1;
    private Integer totalCount = 1;
    private Integer totalPages = 1;

    private final RestTemplate restTemplate;

    public LabContacts(@Value("${lab.contacts.pageNumber}") String pageNumber, @Value("${lab.contacts.pageSize}") String pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.restTemplate = new RestTemplate();
    }


    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ParameterizedTypeReference<List<Contact>> typeReference = new ParameterizedTypeReference<>() {};

            boolean hasNextPage = true;
            while (hasNextPage) {
                ResponseEntity<List<Contact>> response = restTemplate.exchange(
                        getFormatedUrl(),
                        HttpMethod.GET,
                        entity,
                        typeReference
                );

                List<Contact> contactsResponse = Objects.requireNonNull(response.getBody());
                contacts.addAll(addSource(contactsResponse));

                verifyHeadersPagination(response.getHeaders());

                if(currentPage >= totalPages)
                    hasNextPage = false;

                currentPage++;
            }

        } catch (Exception e) {
            log.error("Exception during LabContacts API call. Message: {}", e.getMessage(), e);
        }
        return contacts;
    }

    private Collection<? extends Contact> addSource(List<Contact> contacts) {
        contacts.forEach(c -> c.setSource(KENECT_LABS));
        return contacts;
    }

    private void verifyHeadersPagination(HttpHeaders responseHeaders){
        responseHeaders.forEach((key, value) -> {
            String valueStr = value.stream().findFirst().orElse("0");

            switch (key) {
                case "current-page":
                    currentPage = Integer.parseInt(valueStr);
                    break;
                case "page-items":
                    pageItems = Integer.parseInt(valueStr);
                    break;
                case "total-count":
                    totalCount = Integer.parseInt(valueStr);
                    break;
                case "total-pages":
                    totalPages = Integer.parseInt(valueStr);
                    break;
                default:
                    break;
            }
        });
    }

    public String getFormatedUrl(){
        return String.format("%s?%s&%s",
                url,
                pageNumber.replace("{pageNumber}", currentPage.toString()),
                pageSize.replace("{pageSize}", "5")
        );
    }

}
