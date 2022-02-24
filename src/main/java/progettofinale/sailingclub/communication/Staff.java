package progettofinale.sailingclub.communication;

/**
 * The class {@code Staff } inherit from Person
 * is used to represent Staff entity.
 */
public class Staff extends Person{

    static final long serialVersionUID = 1L;

    /**
     * Class constructor. It is used by Admin.
     *
     * @param name     It is the name of the staff.
     * @param surname  It is the surname of the staff.
     * @param username It is the username of the staff.
     * @param password It is the password of the staff.
     */
    public Staff(final String name, final String surname, final String username, final String password) {

        super(name, surname, username, password);

    }

    /**
     * Class constructor.
     * To instantiate new Staff objects without parameters.
     */
    public Staff() {}

}
