package me.dchuiko.spa.rest;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final List<Ref> refs = new ArrayList<>();

    private JsonType(String name, boolean create, boolean update, boolean delete, boolean read, List<Ref> refs) {
        all.add(this);
        this.name = requireNonNull(name);
        this.create = create;
        this.update = update;
        this.delete = delete;
        this.read = read;
        this.refs.addAll(refs);
    }

    public static final JsonType Account     =  new JsonType("account", true, true, false, true, Collections.emptyList());
    public static final JsonType Transaction =  new JsonType("transaction", true, false, false, true, Collections.emptyList());
    public static final JsonType User        =  new JsonType("user", true, true, false, true,
                                                             Arrays.asList(new Ref("accounts", Account, false, false, false, true),
                                                                           new Ref("transactions", Transaction, false, false, false, true)));

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

    public List<Ref> getRefs() {
        return Collections.unmodifiableList(refs);
    }

    private static class Ref {
        private final String name;
        private final JsonType type;
        private final boolean create;
        private final boolean update;
        private final boolean delete;
        private final boolean read;

        private Ref(String name, JsonType type, boolean create, boolean update, boolean delete, boolean read) {
            this.name = name;
            this.type = type;
            this.create = create;
            this.update = update;
            this.delete = delete;
            this.read = read;
        }

        public String name() {
                return name;
            }

        public JsonType type() { return type; }

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
    }

}
