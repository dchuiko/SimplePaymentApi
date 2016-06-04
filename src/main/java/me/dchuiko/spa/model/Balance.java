package me.dchuiko.spa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Balance {
    private final UUID id;
    private final UUID accountId;
    private final List<Transaction> transactions = new ArrayList<>();

    public Balance(UUID id, UUID accountId) {
        this.id = id;
        this.accountId = accountId;
    }
}
