package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Member;


import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private static final String NAME = "Carmelo";
    private static final String SURNAME = "Vittoria";
    private static final String USERNAME = "Car";
    private static final String PASSWORD = "123456789";
    private static final String CF = "VTTCML99A31F205B";
    private static final String ADDRESS = "Milano, via navigli 34";

    @DisplayName("Standard Person constructor")
    @Test
    public void standardConstructorTest() {
        Member m = new Member(NAME, SURNAME, USERNAME, PASSWORD, ADDRESS, CF);

        assertAll( () -> assertEquals(NAME, m.getName()),
                () -> assertEquals(SURNAME, m.getSurname()),
                () -> assertEquals(USERNAME, m.getUsername()),
                () -> assertEquals(PASSWORD, m.getPassword()),
                () -> assertEquals(ADDRESS, m.getAddress()),
                () -> assertEquals(CF, m.getCf()));

    }

    @DisplayName("Standard Setter Member constructor")
    @Test
    public void standardMemberSetterConstructorTest() {

        Member m = new Member();
        m.setName(NAME);
        m.setSurname(SURNAME);
        m.setUsername(USERNAME);
        m.setPassword(PASSWORD);
        m.setCf(CF);
        m.setAddress(ADDRESS);

        assertAll( () -> assertEquals(NAME, m.getName()),
                   () -> assertEquals(SURNAME, m.getSurname()),
                   () -> assertEquals(USERNAME, m.getUsername()),
                   () -> assertEquals(PASSWORD, m.getPassword()),
                   () -> assertEquals(ADDRESS, m.getAddress()),
                   () -> assertEquals(CF, m.getCf()));
    }

}