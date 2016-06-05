package me.dchuiko.spa.persistence;

import me.dchuiko.spa.model.Account;
import me.dchuiko.spa.model.AccountWithBalance;
import me.dchuiko.spa.model.Transaction;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.rest.json.AccountJson;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Accounts extends Dao<Account> {
    private static final EntityTable<Account> accounts = new EntityTable<>();

    private final Users users;

    public Accounts(IdGenerator idGenerator) {
        super(idGenerator);
        this.users = new Users(idGenerator);
    }

    public AccountWithBalance id(UUID id) {
        Account account = id(accounts, Account.class, id);
        return new AccountWithBalance(account, joinTransactions(account.id()));
    }

    public void lock(UUID id) {
        lock(accounts, Account.class, id);
    }

    public void unlock(UUID id) {
        unlock(accounts, Account.class, id);
    }

    public List<AccountWithBalance> list() {
        return list(accounts).stream().map(a -> new AccountWithBalance(a, joinTransactions(a.id()))).collect(Collectors.toList());
    }

    public List<AccountWithBalance> list(Predicate<? super Account> selector) {
        return list(accounts, selector).stream().map(a -> new AccountWithBalance(a, joinTransactions(a.id()))).collect(Collectors.toList());
    }

    public List<AccountWithBalance> userAccounts(UUID userId) {
        return list(a -> {
            return userId.equals(a.userId());
        });
    }

    private List<Transaction> joinTransactions(UUID accountId) {
        Transactions transactions = new Transactions(idGenerator());
        return transactions.list(t -> t.senderAccountId().equals(accountId) || t.receiverAccountId().equals(accountId));
    }

    public AccountWithBalance create(AccountJson accountJson) {
        UUID id = idGenerator().id();

        User user = users.id(accountJson.getUserId());

        Account account = new Account(id, user.id(), accountJson.getNumber(), accountJson.getInitialBalance(), accountJson.getAlias());
        accounts.put(id, account);
        return new AccountWithBalance(account, Collections.emptyList());
    }

//    public Account update(UUID id, AccountJson userJson) {
//        User current = id(id);
//        User user = new User(id, userJson.getName());
//        if (!users.replace(id, current, user)) {
//            throw new ApplicationException("Optimistic lock exception, try again");
//        }
//        return id(id);
//    }
}
