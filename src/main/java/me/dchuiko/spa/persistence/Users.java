package me.dchuiko.spa.persistence;

import me.dchuiko.spa.rest.exception.ApplicationException;
import me.dchuiko.spa.rest.json.UserJson;
import me.dchuiko.spa.model.User;

import java.util.List;
import java.util.UUID;

import static me.dchuiko.spa.rest.http.Status.preconditionFailed;

public class Users {
    private static final EntityTable<User> users = new EntityTable<>();

    private final IdGenerator idGenerator;

    public Users(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public User id(UUID id) {
        return users.find(id).orElseThrow(() -> new ApplicationException("User id='" + id + "' not found", preconditionFailed));
    }

    public User update(UUID id, UserJson userJson) {
        User current = id(id);
        User user = new User(id, userJson.getName());
        if (!users.replace(id, current, user)) {
            throw new ApplicationException("Optimistic lock exception, try again");
        }
        return id(id);
    }

    public List<User> list() {
        return users.find(user -> true);
    }

    public User create(UserJson userJson) {
        UUID id = idGenerator.id();
        User user = new User(id, userJson.getName());
        users.put(id, user);
        return user;
    }


}
