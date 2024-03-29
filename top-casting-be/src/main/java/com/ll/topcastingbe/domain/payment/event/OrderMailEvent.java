package com.ll.topcastingbe.domain.payment.event;

import java.util.UUID;

public record OrderMailEvent(UUID orderId) {
}
