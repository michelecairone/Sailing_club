package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import progettofinale.sailingclub.communication.Participant;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    private static final String NAME_BOAT = "Alla deriva";
    private static final int ID_BOAT = 20;
    private static final int ID_RACE = 10;
    private static final Date DATE = Date.valueOf("2021-05-12");
    private static final String NAME = "Carmelo";
    private static final String SURNAME = "Vittoria";
    private static final String CF = "VTTCML99A31F205B";


    @DisplayName("Standard Participant constructor")
    @Test
    public void standardConstructorTest() {
        Participant p = new Participant(ID_RACE, ID_BOAT, DATE);

        assertAll( () -> assertEquals(ID_RACE, p.getIdRace()),
                () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(DATE, p.getDate()));

    }

    @DisplayName("Participant constructor2")
    @Test
    public void ConstructorTest2() {
        Participant p = new Participant(NAME, SURNAME, CF,ID_BOAT);

        assertAll( () -> assertEquals(NAME, p.getName()),
                   () -> assertEquals(SURNAME, p.getSurname()),
                   () -> assertEquals(CF, p.getCf()),
                   () -> assertEquals(ID_BOAT, p.getIdBoat()));

    }

    @DisplayName("Standard Setter Participant constructor")
    @Test
    public void standardMemberSetterConstructorTest() {

        Participant p = new Participant();

        p.setNameBoat(NAME_BOAT);
        p.setIdRace(ID_RACE);
        p.setIdBoat(ID_BOAT);
        p.setDate(DATE);

        assertAll( () -> assertEquals(NAME_BOAT, p.getNameBoat()),
                () -> assertEquals(ID_RACE, p.getIdRace()),
                () -> assertEquals(ID_BOAT, p.getIdBoat()),
                () -> assertEquals(DATE, p.getDate()));
    }

}