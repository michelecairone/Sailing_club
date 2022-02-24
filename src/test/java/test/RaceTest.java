package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Race;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class RaceTest {

    private static final int ID = 11;
    private static final String NAME = "Cost concordia";
    private static final float QUOTE = 29.99F;
    private static final LocalDate DATE = LocalDate.parse("2022-02-25");

    @DisplayName("Standard Race constructor")
    @Test
    public void standardConstructorTest() {

        Race r = new Race(ID, NAME, DATE, QUOTE);

        assertAll(() -> assertEquals(ID, r.getId()),
                () -> assertEquals(NAME, r.getName()),
                () -> assertEquals(DATE, r.getDate()),
                () -> assertEquals(QUOTE, r.getQuote()));

    }

    @DisplayName("Race constructor2")
    @Test
    public void Constructor2Test() {

        Race r = new Race(NAME, DATE, QUOTE);

        assertAll(() -> assertEquals(NAME, r.getName()),
                () -> assertEquals(DATE, r.getDate()),
                () -> assertEquals(QUOTE, r.getQuote()));

    }

    @DisplayName("Standard Setter Race constructor")
    @Test
    public void standardBoatSetterConstructorTest() {

        Race r = new Race("uu",LocalDate.parse("2020-10-12"), 0.0F);
        r.setId(ID);
        r.setName(NAME);
        r.setDate(DATE);
        r.setQuote(QUOTE);

        assertAll(() -> assertEquals(ID, r.getId()),
                () -> assertEquals(NAME, r.getName()),
                () -> assertEquals(DATE, r.getDate()),
                () -> assertEquals(QUOTE, r.getQuote()));
    }

}