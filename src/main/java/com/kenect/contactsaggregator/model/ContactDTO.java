package com.kenect.contactsaggregator.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactDTO {

    private Integer id;
    private String name;
    private String email;
    private String source;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
