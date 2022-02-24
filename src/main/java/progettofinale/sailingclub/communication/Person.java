package progettofinale.sailingclub.communication;


import java.io.Serializable;

/**
 * The class {@code Person} is used to represent member/staff entity.
 * It contains property and methods used to manage members and staffs.
 */
public class Person implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * name - It is the name of the member or staff.
     * surname - It is the surname of the member or staff.
     * username - It is the username of the member or staff.
     * password - It is the password of the member or staff.
     */
    private String name;
    private String surname;
    private String username;
    private String password;

    /**
     * Class constructor. It is used by Member/Staff.
     *
     * @param name     It is the name of the member/staff.
     * @param surname  It is the surname of the member/staff.
     * @param username It is the username of the member/staff.
     * @param password It is the password of the member/staff.
     */
    public Person(final String name, final String surname, final String username, final String password) {

        this.setName(name);
        this.setSurname(surname);
        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * Class constructor. It is used by Member/Staff.
     *
     * @param username It is the username of the member/staff.
     * @param password It is the password of the member/staff.
     */
    public Person( final String username, final String password) {

        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * Class constructor.
     * to instantiate new Person objects without parameters.
     */
    public Person() { }

    /**
     * This method is used to get the Person's name.
     *
     * @return It returns the Person's name.
     */
    public String getName() { return this.name; }

    /**
     * This method is used to get the Person's surname.
     *
     * @return It returns the Person's surname.
     */
    public String getSurname() { return this.surname; }

    /**
     * This method is used to get the Person's username.
     *
     * @return It returns the Person's username.
     */
    public String getUsername() { return this.username; }

    /**
     * This method is used to get the Person's password.
     *
     * @return It returns the Person's password.
     */
    public String getPassword() { return this.password; }

    /**
     * This method is used to set the Person's name.
     *
     * @param name is the Person's name.
     */
    public void setName(final String name) { this.name = name; }

    /**
     * This method is used to set the Person's surname.
     *
     * @param surname is the Person's surname.
     */
    public void setSurname(final String surname) { this.surname = surname; }

    /**
     * This method is used to set the Person's username.
     *
     * @param username is the Person's username.
     */
    public void setUsername(final String username) { this.username = username; }

    /**
     * This method is used to set the Person's password.
     *
     * @param password is the Person's password.
     */
    public void setPassword(final String password) { this.password = password; }
}
