package org.katrin.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    IN_PROCESSING("In processing"),
    AWAITING_PAYMENT("Awaiting payment"),
    AWAITING_SHIPMENT("Awaiting shipment"),
    SENT("Sent"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled"),
    AWAITING_RETURN("Awaiting return"),
    RETURNED("Returned");

    private final String message;
}
