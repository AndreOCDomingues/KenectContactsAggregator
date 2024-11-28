package com.kenect.contactsaggregator.model;

import lombok.Data;

import java.util.List;

@Data
public class LabContactsResponse {

    private Integer currentPage;
    private Integer pageItems;
    private Integer totalCount;
    private Integer totalPages;
    private List<Contact> contactList;

}
