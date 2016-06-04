package me.dchuiko.spa.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Transaction implements Identifiable, Copiable<Transaction> {
    private final UUID id;
    private final Instant moment;
    private final UUID senderId;
    private final UUID senderAccountId;
    private final UUID receiverId;
    private final UUID receiverAccountId;
    private final BigDecimal amount;

    public Transaction(UUID id, Instant moment,
                       UUID senderId, UUID senderAccountId,
                       UUID receiverId, UUID receiverAccountId,
                       BigDecimal amount) {
        this.id = requireNonNull(id);
        this.moment = requireNonNull(moment);
        this.senderId = requireNonNull(senderId);
        this.senderAccountId = requireNonNull(senderAccountId);
        this.receiverId = requireNonNull(receiverId);
        this.receiverAccountId = requireNonNull(receiverAccountId);
        this.amount = requireNonNull(amount);
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public Transaction copy() {
        return new Transaction(id, moment, senderId, senderAccountId, receiverId, receiverAccountId, amount);
    }
}
