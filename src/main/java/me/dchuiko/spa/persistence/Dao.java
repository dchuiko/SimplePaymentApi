package me.dchuiko.spa.persistence;

import me.dchuiko.spa.model.Copiable;
import me.dchuiko.spa.model.Identifiable;
import me.dchuiko.spa.rest.exception.ApplicationException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static me.dchuiko.spa.rest.http.Status.preconditionFailed;

public abstract class Dao<T extends Identifiable & Copiable<T>> {
    private final IdGenerator idGenerator;

    protected Dao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    protected IdGenerator idGenerator() {
        return idGenerator;
    }

    protected T id(EntityTable<T> entities, Class<T> clazz, UUID id) {
        return entities.find(id).orElseThrow(() -> new ApplicationException(clazz.getSimpleName() + " id='" + id + "' not found", preconditionFailed));
    }

    protected List<T> list(EntityTable<T> entities) {
        return list(entities, t -> true);
    }

    protected List<T> list(EntityTable<T> entities, Predicate<? super T> selector) {
        return entities.find(selector);
    }

    protected void lock(EntityTable<T> entities, Class<T> clazz, UUID id) {
        entities.lock(id);
    }

    protected void unlock(EntityTable<T> entities, Class<T> clazz, UUID id) {
        entities.unlock(id);
    }
}
