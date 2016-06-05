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
    private final EntityTable<T> entityTable;

    protected Dao(IdGenerator idGenerator, EntityTable<T> entityTable) {
        this.idGenerator = idGenerator;
        this.entityTable = entityTable;
    }

    protected IdGenerator idGenerator() {
        return idGenerator;
    }

    protected T id(Class<T> clazz, UUID id) {
        return entityTable.find(id).orElseThrow(() -> new ApplicationException(clazz.getSimpleName() + " id='" + id + "' not found", preconditionFailed));
    }

    protected List<T> doList() {
        return doList(t -> true);
    }

    protected List<T> doList(Predicate<? super T> selector) {
        return entityTable.find(selector);
    }

    public void lock(UUID id) {
        entityTable.lock(id);
    }

    public void unlock(UUID id) {
        entityTable.unlock(id);
    }

    public void clear() {
        entityTable.clear();
    }
}
