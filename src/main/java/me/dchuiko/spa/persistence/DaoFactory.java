package me.dchuiko.spa.persistence;

import java.util.Arrays;
import java.util.List;

public class DaoFactory {
    private final static IdGenerator idGenerator = IdGenerator.generator();
    public final static Users users = new Users(idGenerator);
    public final static Accounts accounts = new Accounts(idGenerator);
    public final static Transactions transactions = new Transactions(idGenerator);

    private final static List<? extends Dao> daos = Arrays.asList(users, accounts, transactions);

    public static void clear() {
        daos.forEach(Dao::clear);
    }
}
