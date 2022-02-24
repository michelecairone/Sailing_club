package progettofinale.sailingclub.communication;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The class {@code Race } is used to represent Race entity.
 */
public class Race implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * id - It is the identification code of the race.
     * name - It is the name of the race.
     * date - It is the date of the race.
     * quote - It is the race participation fee.
     */
    private int id;
    private String name;
    private LocalDate date;
    private Float quote;

    /**
     * Class constructor.
     *
     * @param id - It is the identification code of the race.
     * @param name - It is the name of the race.
     * @param date - It is the date of the race.
     * @param quote - This is the race participation fee.
     */
    public Race(int id, String name, LocalDate date,  float quote) {

        this.setId(id);
        this.setName(name);
        this.setDate(date);
        this.setQuote(quote);
    }

    /**
     * Class constructor.
     *
     * @param name - It is the name of the race.
     * @param date - It is the date of the race.
     * @param quote - This is the race participation fee.
     */
    public Race( String name, LocalDate date,  float quote) {

        this.setName(name);
        this.setDate(date);
        this.setQuote(quote);
    }

    /**
     * This method is used to get the race identification code.
     *
     * @return It returns the race identification code.
     */
    public int getId() {
        return id;
    }

    /**
     * This method is used to get the race name.
     *
     * @return It returns the race name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to get the race date.
     *
     * @return It returns the race date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * This method is used to get the race participation fee.
     *
     * @return It returns the race participation fee.
     */
    public Float getQuote() {
        return quote;
    }

    /**
     * This method is used to set the identification code of the race.
     *
     * @param id It is the identification code of the race.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method is used to set the name of the race.
     *
     * @param name It is the name of the race.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is used to set the date of the race.
     *
     * @param date It is the date of the race.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * This method is used to set the race participation fee.
     *
     * @param quote It is the race participation fee.
     */
    public void setQuote(float quote) {
        this.quote = quote;
    }

}
