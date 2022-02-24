package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Staff;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest {

    public static final String NAME = "Carmelo";
    public static final String SURNAME = "Vittoria";
    public static final String USERNAME = "Car";
    public static final String PASSWORD = "1234";

    @DisplayName("Standard Staff constructor")
    @Test
    public void standardConstructorTest() {
        Staff s = new Staff(NAME, SURNAME, USERNAME, PASSWORD);
        assertAll(() -> assertEquals(NAME, s.getName()),
                () -> assertEquals(SURNAME, s.getSurname()),
                () -> assertEquals(USERNAME, s.getUsername()),
                () -> assertEquals(PASSWORD, s.getPassword()));

    }

    @DisplayName("Standard Setter Staff constructor")
    @Test
    public void standardPersonSetterConstructorTest() {

        Staff s = new Staff();
        s.setName(NAME);
        s.setSurname(SURNAME);
        s.setUsername(USERNAME);
        s.setPassword(PASSWORD);

        assertAll( () -> assertEquals(NAME, s.getName()),
                () -> assertEquals(SURNAME, s.getSurname()),
                () -> assertEquals(USERNAME, s.getUsername()),
                () -> assertEquals(PASSWORD, s.getPassword()));
    }

}