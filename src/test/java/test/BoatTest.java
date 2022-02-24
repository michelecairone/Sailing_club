package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Boat;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {

    private static final int ID = 11;
    private static final String NAME = "Cost concordia";
    private static final String OWNER = "Michele";
    private static final int LENGTH = 12;
    private static final float STORAGE = 19.99F;
    private static final String DATE_STORAGE = "2022-02-02";

    @DisplayName("Standard Boat constructor")
    @Test
    public void standardConstructorTest() {
        Boat b = new Boat(ID, NAME, LENGTH, STORAGE, DATE_STORAGE);

        assertAll(() -> assertEquals(ID, b.getId()),
                () -> assertEquals(NAME, b.getName()),
                () -> assertEquals(LENGTH, b.getLength()),
                () -> assertEquals(STORAGE, b.getStorage()),
                () -> assertEquals(DATE_STORAGE, b.getDateStorage()));

    }

    @DisplayName("Boat constructor2")
    @Test
    public void ConstructorTest2() {
        Boat b = new Boat(ID, NAME, LENGTH, STORAGE);

        assertAll(() -> assertEquals(ID, b.getId()),
                () -> assertEquals(NAME, b.getName()),
                () -> assertEquals(LENGTH, b.getLength()),
                () -> assertEquals(STORAGE, b.getStorage()));

    }

    @DisplayName("Boat constructor3")
    @Test
    public void ConstructorTest3() {
        Boat b = new Boat(ID, NAME, LENGTH);

        assertAll(() -> assertEquals(ID, b.getId()),
                () -> assertEquals(NAME, b.getName()),
                () -> assertEquals(LENGTH, b.getLength()));

    }

    @DisplayName("Boat constructor4")
    @Test
    public void ConstructorTest4() {
        Boat b = new Boat(ID, NAME, LENGTH, OWNER);

        assertAll(() -> assertEquals(ID, b.getId()),
                () -> assertEquals(NAME, b.getName()),
                () -> assertEquals(LENGTH, b.getLength()),
                () -> assertEquals(OWNER, b.getOwner()));

    }

    @DisplayName("Boat constructor5")
    @Test
    public void ConstructorTest5() {
        Boat b = new Boat(NAME, LENGTH, OWNER);

        assertAll(() ->  assertEquals(NAME, b.getName()),
                () -> assertEquals(LENGTH, b.getLength()),
                () -> assertEquals(OWNER, b.getOwner()));

    }

    @DisplayName("Standard Setter Boat constructor")
    @Test
    public void standardBoatSetterConstructorTest() {

        Boat b = new Boat();
        b.setId(ID);
        b.setName(NAME);
        b.setLength(LENGTH);
        b.setOwner(OWNER);
        b.setStorage(STORAGE);
        b.setDateStorage(DATE_STORAGE);

        assertAll(() -> assertEquals(ID, b.getId()),
                () -> assertEquals(NAME, b.getName()),
                () -> assertEquals(LENGTH, b.getLength()),
                () -> assertEquals(OWNER, b.getOwner()),
                () -> assertEquals(STORAGE, b.getStorage()),
                () -> assertEquals(DATE_STORAGE, b.getDateStorage()));
    }

}