package me.dchuiko.spa.persistence;

import me.dchuiko.spa.model.Copiable;
import me.dchuiko.spa.model.Identifiable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityTable<T extends Identifiable & Copiable<T>> {
    private final Map<UUID, T> data = new ConcurrentHashMap<>();

    public Optional<T> put(UUID key, T value) {
        return Optional.ofNullable(data.put(key, value.copy()));
    }

    public boolean replace(UUID key, T oldValue, T value) {
        return data.replace(key, oldValue, value);
    }

    public Optional<T> find(UUID key) {
        T value = data.get(key);
        if (value != null) {
            value = value.copy();
        }
        return Optional.ofNullable(value);
    }

    public List<T> find(Predicate<? super T> selector) {
        return data.values().stream().filter(selector).map(t -> t.copy()).collect(Collectors.toList());
    }

    public Optional<T> delete(UUID key) {
        return Optional.ofNullable(data.remove(key));
    }

    public int size() {
        return data.size();
    }
}
