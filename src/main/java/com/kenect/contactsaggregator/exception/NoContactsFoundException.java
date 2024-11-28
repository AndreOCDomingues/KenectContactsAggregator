package com.kenect.contactsaggregator.exception;

public class NoContactsFoundException extends RuntimeException {
    public NoContactsFoundException(String message) {
        super(message);
    }
}
