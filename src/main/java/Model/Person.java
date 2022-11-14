package Model;
import Interface.*;

public abstract class Person{
    private String user;
    private String passwd;
    private String firstName;
    private String lastName;

    public Person(String user, String passwd, String firstName, String lastName) {
        this.user = user;
        this.passwd = passwd;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUser() {
        return user;
    }

    public String getPasswd() {
        return passwd;
    }

}
