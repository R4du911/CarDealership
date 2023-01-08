package Model.Repo;

import Model.Person;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private final List<Person> users = new ArrayList<>();

    public List<Person> getUsers() {
        return users;
    }

    /**
     * @param user - new user to be added
     * Adds a new user to the user-repository
     */
    public void addUser(Person user){
        users.add(user);
    }
}
