package me.dchuiko.spa.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Transaction implements Identifiable, Copiable<Transaction> {
    private final UUID id;
    private final LocalDateTime moment;
    private final UUID senderId;
    private final UUID senderAccountId;
    private final UUID receiverId;
    private final UUID receiverAccountId;
    private final int amount;

    public Transaction(UUID id, LocalDateTime moment,
                       UUID senderId, UUID senderAccountId,
                       UUID receiverId, UUID receiverAccountId,
                       int amount) {
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

    public LocalDateTime moment() {
        return moment;
    }

    public UUID senderId() {
        return senderId;
    }

    public UUID senderAccountId() {
        return senderAccountId;
    }

    public UUID receiverId() {
        return receiverId;
    }

    public UUID receiverAccountId() {
        return receiverAccountId;
    }

    public int amount() {
        return amount;
    }

    @Override
    public Transaction copy() {
        return new Transaction(id, moment, senderId, senderAccountId, receiverId, receiverAccountId, amount);
    }
}
