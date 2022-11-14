package Model.Repo;

import Model.Person;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private final List<Person> users = new ArrayList<>();

    public List<Person> getUsers() {
        return users;
    }

    public void addUser(Person user){
        users.add(user);
    }

    public void removeUser(Person user){
        users.remove(user);
    }
}
