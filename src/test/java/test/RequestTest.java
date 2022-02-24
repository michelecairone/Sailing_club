package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.*;
import java.sql.Date;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private static final Member MEMBER = new Member("giuseppe","lo groi","gg","pass123","via Bonfiglio 13, Catania","VTTCML99A31F205B");
    private static final Participant PARTICIPANT = new Participant(1,2,Date.valueOf("2022-02-10"));
    private static final Person PERSON = new Person("pippo","pluto","pp","1234");
    private static final Boat BOAT = new Boat(2,"Cost concordia",14,120F,"2022-02-15");
    private static final Race RACE = new Race(1,"uu", LocalDate.parse("2020-10-12"), 0.0F);
    private static final RequestType REQUEST_TYPE = RequestType.LOGIN;
    private static final int ID = 123;
    private static final Integer ID_BOAT = 145;
    private static final Integer ID_RACE = 32;
    private static final Float QUOTE = 15.99F;
    private static final String USERNAME = "pippo";
    private static final String CF = "VTTCML99A31F205B";

    @DisplayName("Standard Request constructor")
    @Test
    public void standardConstructorTest() {

        Request r = new Request(REQUEST_TYPE);

        assertAll(() -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor2")
    @Test
    public void Constructor2Test() {

        Request r = new Request(RACE, REQUEST_TYPE);

        assertAll(() -> assertEquals(RACE, r.getRace()),
                  () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor3")
    @Test
    public void Constructor3Test() {

        Request r = new Request(USERNAME, REQUEST_TYPE);

        assertAll(() -> assertEquals(USERNAME, r.getUsername()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor4")
    @Test
    public void Constructor4Test() {

        Request r = new Request(QUOTE, REQUEST_TYPE);

        assertAll(() -> assertEquals(QUOTE, r.getQuote()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor5")
    @Test
    public void Constructor5Test() {

        Request r = new Request(PARTICIPANT, USERNAME, REQUEST_TYPE);

        assertAll(() -> assertEquals(PARTICIPANT, r.getParticipant()),
                () -> assertEquals(USERNAME, r.getUsername()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor6")
    @Test
    public void Constructor6Test() {

        Request r = new Request(PERSON, REQUEST_TYPE);

        assertAll(() -> assertEquals(PERSON, r.getPerson()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor7")
    @Test
    public void Constructor7Test() {

        Request r = new Request(MEMBER, REQUEST_TYPE);

        assertAll(() -> assertEquals(MEMBER, r.getMember()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor8")
    @Test
    public void Constructor8Test() {

        Request r = new Request(BOAT, REQUEST_TYPE);

        assertAll(() -> assertEquals(BOAT, r.getBoat()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor9")
    @Test
    public void Constructor9Test() {

        Request r = new Request(ID, REQUEST_TYPE);

        assertAll(() -> assertEquals(ID, r.getId()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor10")
    @Test
    public void Constructor10Test() {

        Request r = new Request(CF, ID_BOAT, REQUEST_TYPE);

        assertAll(() -> assertEquals(CF, r.getUsername()),
                () -> assertEquals(ID_BOAT, r.getIdBoat()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor11")
    @Test
    public void Constructor11Test() {

        Request r = new Request(USERNAME, ID, REQUEST_TYPE);

        assertAll(() -> assertEquals(USERNAME, r.getUsername()),
                () -> assertEquals(ID, r.getId()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Request constructor12")
    @Test
    public void Constructor12Test() {

        Request r = new Request(CF, ID_RACE, ID_BOAT, REQUEST_TYPE);

        assertAll(() -> assertEquals(CF, r.getUsername()),
                () -> assertEquals(ID_RACE, r.getIdRace()),
                () -> assertEquals(ID_BOAT, r.getIdBoat()),
                () -> assertEquals(REQUEST_TYPE, r.getType()));

    }

    @DisplayName("Standard Setter Request")
    @Test
    public void standardRequestSetterTest() {

        Request r = new Request();
        Request r2 = new Request(RACE, REQUEST_TYPE);
        Request r3 = new Request(USERNAME, REQUEST_TYPE);
        Request r4 = new Request(QUOTE, REQUEST_TYPE);
        Request r5 = new Request(PARTICIPANT,USERNAME, REQUEST_TYPE);
        Request r6 = new Request(PERSON, REQUEST_TYPE);
        Request r7 = new Request(MEMBER, REQUEST_TYPE);
        Request r8 = new Request(BOAT, REQUEST_TYPE);
        Request r9 = new Request(CF, ID_BOAT, REQUEST_TYPE);
        Request r10 = new Request(USERNAME, ID, REQUEST_TYPE);
        Request r11 = new Request(CF, ID_RACE, ID_BOAT, REQUEST_TYPE);

        r.setType(REQUEST_TYPE);
        r.setId(ID);

        assertAll(() -> assertEquals(ID, r.getId()),
                () -> assertEquals(REQUEST_TYPE, r.getType()),
                () -> assertEquals(RACE, r2.getRace()),
                () -> assertEquals(USERNAME, r3.getUsername()),
                () -> assertEquals(QUOTE, r4.getQuote()),
                () -> assertEquals(PARTICIPANT, r5.getParticipant()),
                () -> assertEquals(PERSON, r6.getPerson()),
                () -> assertEquals(MEMBER, r7.getMember()),
                () -> assertEquals(BOAT, r8.getBoat()),
                () -> assertEquals(CF, r9.getUsername()),
                () -> assertEquals(ID_BOAT, r9.getIdBoat()),
                () -> assertEquals(USERNAME, r10.getUsername()),
                () -> assertEquals(ID, r10.getId()),
                () -> assertEquals(CF, r11.getUsername()),
                () -> assertEquals(ID_RACE, r11.getIdRace()),
                () -> assertEquals(ID_BOAT, r11.getIdBoat()));
    }

}