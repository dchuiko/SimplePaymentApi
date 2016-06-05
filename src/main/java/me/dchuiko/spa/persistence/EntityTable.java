package me.dchuiko.spa.persistence;

import io.vertx.core.impl.ConcurrentHashSet;
import me.dchuiko.spa.model.Copiable;
import me.dchuiko.spa.model.Identifiable;
import me.dchuiko.spa.rest.exception.ApplicationException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityTable<T extends Identifiable & Copiable<T>> {
    private final Map<UUID, T> data = new ConcurrentSkipListMap<>();
    private final ConcurrentHashSet<UUID> locks = new ConcurrentHashSet<>();

    public Optional<T> put(UUID key, T value) {
        if (isLocked(key)) {
            throw new ApplicationException("Optimistic lock exception: key '" + key + "' is locked");
        }

        return Optional.ofNullable(data.put(key, value.copy()));
    }

    public boolean replace(UUID key, T oldValue, T value) {
        if (isLocked(key)) {
            throw new ApplicationException("Optimistic lock exception: key '" + key + "' is locked");
        }

        return data.replace(key, oldValue, value);
    }

    public Optional<T> find(UUID key) {
        T value = data.get(key);
        if (value != null) {
            value = value.copy();
        }
        return Optional.ofNullable(value);
    }

    public void lock(UUID key) {
        if (!locks.add(key)) {
            throw new ApplicationException("Optimistic lock exception: key '" + key + "' is already locked");
        }
    }

    public void unlock(UUID key) {
        locks.remove(key);
    }

    public List<T> find(Predicate<? super T> selector) {
        return data.values().stream().filter(selector).map(t -> t.copy()).collect(Collectors.toList());
    }

    public Optional<T> delete(UUID key) {
        if (isLocked(key)) {
            throw new ApplicationException("Optimistic lock exception: key '" + key + "' is locked");
        }

        return Optional.ofNullable(data.remove(key));
    }

    public int size() {
        return data.size();
    }

    public boolean isLocked(UUID key) {
        return locks.contains(key);
    }
}
