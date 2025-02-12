package ru.hofftech.deliveryservice.billing.exception;

public class FetchingBillingAuditException extends RuntimeException {
    public FetchingBillingAuditException(String message) {
        super(message);
    }
}
