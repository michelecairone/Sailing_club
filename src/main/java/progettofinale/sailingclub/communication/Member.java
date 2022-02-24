package progettofinale.sailingclub.communication;

import java.io.Serializable;

/**
 * The class {@code Member } inherit from Person
 * is used to represent Member entity.
 */
public class Member extends Person implements Serializable {

    //serialVersionUID - It is the versionUID
    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * address - It is the address of the member.
     * cf - It is the fiscal Code of the member.
     * boats - It is
     */
    private String address;
    private String cf;


    /**
     * Class constructor. It is used by staff.
     *
     * @param name     It is the name of the member.
     * @param surname  It is the surname of the member.
     * @param username It is the username of the member.
     * @param password It is the password of the member.
     * @param address It is the address of the member.
     * @param cf It is the fiscal Code of the member.
     */
    public Member(final String name, final String surname, final String username, final String password, final String address, final String cf) {

        super(name, surname, username, password);
        this.setAddress(address);
        this.setCf(cf);
    }

    /**
     * Class constructor.
     * To instantiate new Member objects without parameters.
     */
    public Member() {}

    /**
     * This method is used to get the member's address.
     *
     * @return It returns the member's address.
     */
    public String getAddress() { return this.address;}

    /**
     * This method is used to get the member's fiscal code.
     *
     * @return It returns the member's fiscal code.
     */
    public String getCf() { return this.cf; }

    /**
     * This method is used to set the member's address.
     *
     * @param address is the member's address.
     */
    public void setAddress(final String address) { this.address = address; }

    /**
     * This method is used to set the member's fiscal code.
     *
     * @param cf is the member's fiscal code .
     */
    public void setCf(final String cf) { this.cf= cf; }

}
