package me.dchuiko.spa.persistence;

import me.dchuiko.spa.model.AccountWithBalance;
import me.dchuiko.spa.model.Transaction;
import me.dchuiko.spa.rest.exception.ValidationException;
import me.dchuiko.spa.rest.json.TransactionJson;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class Transactions extends Dao<Transaction> {
    private static final EntityTable<Transaction> transactions = new EntityTable<>();

    private final Accounts accounts;
    private final Users users;

    public Transactions(IdGenerator idGenerator) {
        super(idGenerator, transactions);
        this.accounts = DaoFactory.accounts;
        this.users = DaoFactory.users;
    }

    public Transaction id(UUID id) {
        return id(Transaction.class, id);
    }

    public List<Transaction> list() {
        return doList();
    }

    public List<Transaction> list(Predicate<? super Transaction> selector) {
        return doList(selector);
    }

    public List<Transaction> userTransactions(UUID userId) {
        return list(a -> userId.equals(a.receiverId()) || userId.equals(a.senderAccountId()));
    }

    public Transaction create(TransactionJson transactionJson) {
        if (transactionJson.getAmount() <= 0) {
            throw new ValidationException("Transaction amount should be more then zero");
        }

        UUID id = idGenerator().id();

        AccountWithBalance senderAccount = accounts.id(transactionJson.getSenderAccount().getId());
        AccountWithBalance receiverAccount = accounts.id(transactionJson.getReceiverAccount().getId());

        Set<UUID> takenLocks = new HashSet<>();
        try {
            accounts.lock(senderAccount.id());
            takenLocks.add(senderAccount.id());
            accounts.lock(receiverAccount.id());
            takenLocks.add(receiverAccount.id());

            if (senderAccount.balance() < transactionJson.getAmount()) {
                throw new ValidationException("Insufficient funds on account '" + senderAccount.number() + "'");
            }

            final LocalDateTime moment = transactionJson.getMoment();
            Transaction transaction = new Transaction(id, moment != null ? moment : LocalDateTime.now(),
                                                      senderAccount.userId(), senderAccount.id(),
                                                      receiverAccount.userId(), receiverAccount.id(),
                                                      transactionJson.getAmount());
            transactions.put(id, transaction);
            return transaction;

        } finally {
            takenLocks.forEach(accounts::unlock);
        }

    }

}
