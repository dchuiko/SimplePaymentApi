package me.dchuiko.spa.json;

import me.dchuiko.spa.json.handler.AccountHandler;
import me.dchuiko.spa.json.handler.GenericHandler;
import me.dchuiko.spa.json.handler.Handler;
import me.dchuiko.spa.json.handler.TransactionHandler;
import me.dchuiko.spa.json.handler.UserHandler;
import me.dchuiko.spa.json.http.MimeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class JsonType {
    private static final List<JsonType> all = new ArrayList<>();

    private final String name;
    private final boolean create;
    private final boolean update;
    private final boolean delete;
    private final boolean read;
    private final Handler handler;
    private final MimeType mimeType = MimeType.application_json;

    private JsonType(String name, boolean create, boolean update, boolean delete, boolean read, Handler handler) {
        all.add(this);
        this.name = requireNonNull(name);
        this.create = create;
        this.update = update;
        this.delete = delete;
        this.read = read;
        this.handler = requireNonNull(handler);
    }

    public static final JsonType User        =  new JsonType("user", true, true, false, true, new UserHandler());
    public static final JsonType Account     =  new JsonType("account", true, true, false, true, new AccountHandler());
    public static final JsonType Transaction =  new JsonType("transaction", true, false, false, true, new TransactionHandler());

    public static Optional<JsonType> find(String name) {
        return all.stream().filter(jt -> jt.name.equals(name)).findFirst();
    }

    public static List<JsonType> jsonTypes() {
        return Collections.unmodifiableList(all);
    }

    public String name() {
        return name;
    }

    public String namePlural() {
        return name + "s";
    }

    public Handler handler() {
        return handler;
    }

    public boolean canCreate() {
        return create;
    }

    public boolean canUpdate() {
        return update;
    }

    public boolean canDelete() {
        return delete;
    }

    public boolean canRead() {
        return read;
    }

    public MimeType produces() {
        return mimeType;
    }

    public MimeType consumes() {
        return mimeType;
    }



}
