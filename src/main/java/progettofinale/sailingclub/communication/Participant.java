package progettofinale.sailingclub.communication;

import java.io.Serializable;
import java.sql.Date;

/**
 * The class {@code Participant } inherit from Member,
 * is used to represent Participant entity.
 */
public class Participant extends Member implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * nameBoat - It is the name of the boat of the participant.
     * idBoat - It is the identification code of the boat of the participant.
     * idRace - It is the identification code of the race in which the participant's boat is participating.
     * date - It is the date of the race.
     */
    private String nameBoat;
    private int idBoat;
    private int idRace;
    private Date date;

    /**
     * Class constructor.
     * To instantiate new Participant objects without parameters.
     */
    public Participant () {}

    /**
     * Class constructor.
     *
     * @param idRace It is the identification code of the race in which the participant's boat is participating.
     * @param idBoat It is the identification code of the boat participating in the race.
     * @param date   It is the date of the race.
     */
    public Participant(final int idRace, final int idBoat, final Date date) {

        this.setIdRace(idRace);
        this.setIdBoat(idBoat);
        this.setDate(date);
    }

    /**
     * Class constructor.
     *
     * @param name     It is the name of the participant.
     * @param surname  It is the surname of the participant.
     * @param cf       It is the fiscal code of the participant.
     * @param idBoat   It is the identification code of the boat of the participant.
     */
    public Participant(final String name, final String surname, final String cf, final int idBoat) {

        this.setName(name);
        this.setSurname(surname);
        this.setCf(cf);
        this.setIdBoat(idBoat);

    }

    /**
     * This method is used to obtain the name of the participant's boat.
     *
     * @return It returns the name of the participant's boat.
     */
    public String getNameBoat() { return nameBoat; }

    /**
     * This method is used to obtain the participant's race identification code.
     *
     * @return It returns the participant's race identification code.
     */
    public int getIdRace() { return idRace; }

    /**
     * This method is used to obtain the identification code of the participant's boat.
     *
     * @return It returns the identification code of the participant's boat.
     */
    public int getIdBoat() { return idBoat; }

    /**
     * This method is used to obtain the date of the race in which the participant participates.
     *
     * @return It returns the date of the race.
     */
    public Date getDate() { return date; }

    /**
     * This method is used to set the name of the participant's boat.
     *
     * @param nameBoat is the name of the participant's boat.
     */
    public void setNameBoat(String nameBoat) { this.nameBoat = nameBoat; }

    /**
     * This method is used to set the participant's race identification code.
     *
     * @param idRace is the participant's race identification code.
     */
    public void setIdRace(int idRace) { this.idRace = idRace; }

    /**
     * This method is used to set the identification code of the participant's boat.
     *
     * @param idBoat is the identification code of the participant's boat.
     */
    public void setIdBoat(int idBoat) { this.idBoat = idBoat; }

    /**
     * This method is used to set the date of the race in which the participant is participating.
     *
     * @param date is the date of the race in which the participant is participating.
     */
    public void setDate(Date date) { this.date = date; }

}
