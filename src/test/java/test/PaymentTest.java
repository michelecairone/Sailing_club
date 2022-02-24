package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Payment;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private static final String CF = "VTTCML99A31F205B";
    private static final String NAME_BOAT = "Alla deriva";
    private static final String STRING = "2022-02-19";
    private static final int ID_RACE = 17;
    private static final int ID_BOAT = 11;
    private static final float STORAGE_QUOTE = 19.99F;
    private static final LocalDate DATE_STORAGE = LocalDate.parse("2022-02-02");
    private static final LocalDate DATE_RACE = LocalDate.parse("2022-02-23");
    private static final LocalDate DATE_FEE = LocalDate.parse("2022-02-23");

    @DisplayName("Standard Payment constructor")
    @Test
    public void standardConstructorTest() {
        Payment p = new Payment(CF, ID_BOAT, STORAGE_QUOTE, DATE_STORAGE);

        assertAll( () -> assertEquals(CF, p.getCf()),
                () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(STORAGE_QUOTE, p.getStorageQuote()),
                () -> assertEquals(DATE_STORAGE, p.getStorageDate()));

    }

    @DisplayName("Payment constructor2")
    @Test
    public void Constructor2Test() {
        Payment p = new Payment(ID_BOAT, NAME_BOAT, STORAGE_QUOTE, DATE_STORAGE);

        assertAll( () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(NAME_BOAT, p.getBoatName()),
                () -> assertEquals(STORAGE_QUOTE, p.getStorageQuote()),
                () -> assertEquals(DATE_STORAGE, p.getStorageDate()));

    }

    @DisplayName("Payment constructor3")
    @Test
    public void Constructor3Test() {
        Payment p = new Payment(ID_BOAT, NAME_BOAT, STORAGE_QUOTE, STRING);

        assertAll( () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(NAME_BOAT, p.getBoatName()),
                () -> assertEquals(STORAGE_QUOTE, p.getStorageQuote()),
                () -> assertEquals(STRING, p.getString()));

    }

    @DisplayName("Payment constructor4")
    @Test
    public void Constructor4Test() {
        Payment p = new Payment(CF,DATE_FEE);

        assertAll( () -> assertEquals(CF, p.getCf()),
                () -> assertEquals(DATE_FEE, p.getFeeDate()));

    }

    @DisplayName("Payment constructor5")
    @Test
    public void Constructor5Test() {
        Payment p = new Payment(CF, ID_BOAT, ID_RACE, DATE_RACE);

        assertAll( () -> assertEquals(CF, p.getCf()),
                () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(ID_RACE, p.getIdRace()),
                () -> assertEquals(DATE_RACE, p.getRaceDate()));

    }

    @DisplayName("Standard Setter Payment constructor")
    @Test
    public void standardPaymentSetterConstructorTest() {

        Payment p = new Payment();
        p.setString(STRING);
        p.setBoatName(NAME_BOAT);
        p.setCf(CF);
        p.setIdRace(ID_RACE);
        p.setIdBoat(ID_BOAT);
        p.setStorageQuote(STORAGE_QUOTE);
        p.setStorageDate(DATE_STORAGE);
        p.setRaceDate(DATE_RACE);
        p.setFeeDate(DATE_FEE);

        assertAll( () -> assertEquals(STRING, p.getString()),
                () -> assertEquals(NAME_BOAT, p.getBoatName()),
                () -> assertEquals(CF, p.getCf()),
                () -> assertEquals(ID_RACE, p.getIdRace()),
                () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(STORAGE_QUOTE, p.getStorageQuote()),
                () -> assertEquals(DATE_STORAGE, p.getStorageDate()),
                () -> assertEquals(DATE_RACE, p.getRaceDate()),
                () -> assertEquals(DATE_FEE, p.getFeeDate()));
    }

}