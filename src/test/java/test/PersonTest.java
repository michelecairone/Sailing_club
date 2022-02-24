package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Person;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    public static final String NAME = "Carmelo";
    public static final String SURNAME = "Vittoria";
    public static final String USERNAME = "Car";
    public static final String PASSWORD = "1234";

    @DisplayName("Standard Person constructor")
    @Test
    public void standardConstructorTest() {
        Person p = new Person(NAME, SURNAME, USERNAME, PASSWORD);
        assertAll(() -> assertEquals(NAME, p.getName()),
                () -> assertEquals(SURNAME, p.getSurname()),
                () -> assertEquals(USERNAME, p.getUsername()),
                () -> assertEquals(PASSWORD, p.getPassword()));

    }

    @DisplayName("Person constructor2")
    @Test
    public void Constructor2Test() {
        Person p = new Person(USERNAME, PASSWORD);
        assertAll(() -> assertEquals(USERNAME, p.getUsername()),
                () -> assertEquals(PASSWORD, p.getPassword()));

    }

    @DisplayName("Standard Setter Person constructor")
    @Test
    public void standardPersonSetterConstructorTest() {

        Person p = new Person();
        p.setName(NAME);
        p.setSurname(SURNAME);
        p.setUsername(USERNAME);
        p.setPassword(PASSWORD);

        assertAll( () -> assertEquals(NAME, p.getName()),
                () -> assertEquals(SURNAME, p.getSurname()),
                () -> assertEquals(USERNAME, p.getUsername()),
                () -> assertEquals(PASSWORD, p.getPassword()));
    }

}