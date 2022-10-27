public abstract class Person implements Register{
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

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
