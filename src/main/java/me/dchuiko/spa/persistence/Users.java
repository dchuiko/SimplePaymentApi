package me.dchuiko.spa.persistence;

import me.dchuiko.spa.model.User;
import me.dchuiko.spa.rest.exception.ApplicationException;
import me.dchuiko.spa.rest.json.UserJson;

import java.util.List;
import java.util.UUID;

public class Users extends Dao<User> {
    private static final EntityTable<User> users = new EntityTable<>();

    public Users(IdGenerator idGenerator) {
        super(idGenerator, users);
    }

    public User id(UUID id) {
        return id(User.class, id);
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
        return doList();
    }

    public User create(UserJson userJson) {
        UUID id = idGenerator().id();
        User user = new User(id, userJson.getName());
        users.put(id, user);
        return user;
    }


}
