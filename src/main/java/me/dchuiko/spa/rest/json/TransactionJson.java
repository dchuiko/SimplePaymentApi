package me.dchuiko.spa.rest.json;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TransactionJson {
    private final UUID id;
    private final Instant moment;
    private final UUID senderId;
    private final UUID receiverId;
    private final BigDecimal amount;

    public TransactionJson(UUID id, Instant moment, UUID senderId, UUID receiverId, BigDecimal amount) {
        this.id = id;
        this.moment = moment;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }
}
