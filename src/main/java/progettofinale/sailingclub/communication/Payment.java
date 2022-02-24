package progettofinale.sailingclub.communication;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * The class {@code Payment } is used to represent Payment entity.
 */
public class Payment implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * cf - It is the fiscal code of the payer.
     * boatName - It is the name of the boat.
     * string - It is the string.
     * idBoat - It is the identification code of the boat.
     * storageQuote - It is the boat storage fee.
     * storageDate - It is the date of payment of the boat storage fee.
     * idRace - It is the identification code of the race.
     * raceDate - It is the  date of the race.
     * feeDate - It is the date of payment of the member's membership fee
     */
    private String cf;
    private String boatName;
    private String string;
    private int idBoat;
    private float storageQuote;
    private LocalDate storageDate;
    private int idRace;
    private LocalDate raceDate;
    private LocalDate feeDate;

    /**
     * Class constructor.
     * To instantiate new Participant objects without parameters.
     */
    public Payment (){}

    /**
     * Class constructor.
     *
     * @param cf It is the fiscal code of the payer (member).
     * @param idBoat It is the identification code of the boat of the payer (member).
     * @param storageQuote It is the boat storage fee.
     * @param storageDate It is the date of payment of the boat storage fee.
     */
    public Payment (String cf, int idBoat, float storageQuote, LocalDate storageDate ){

        this.setCf(cf);
        this.setIdBoat(idBoat);
        this.setStorageQuote(storageQuote);
        this.setStorageDate(storageDate);

    }

    /**
     * Class constructor.
     *
     * @param idBoat It is the identification code of the boat of the payer (member).
     * @param boatName It is the name of the boat of the payer (member).
     * @param storageQuote It is the boat storage fee.
     * @param storageDate It is the date of payment of the boat storage fee.
     */
    public Payment (int idBoat,String boatName, float storageQuote, LocalDate storageDate ){

        this.setIdBoat(idBoat);
        this.setBoatName(boatName);
        this.setStorageQuote(storageQuote);
        this.setStorageDate(storageDate);

    }
    /**
     * Class constructor.
     *
     * @param idBoat It is the identification code of the boat of the payer (member).
     * @param boatName It is the name of the boat of the payer (member).
     * @param storageQuote It is the boat storage fee.
     * @param string It is the date of payment.
     */
    public Payment (int idBoat,String boatName, float storageQuote, String string){

        this.setIdBoat(idBoat);
        this.setBoatName(boatName);
        this.setStorageQuote(storageQuote);
        this.setString(string);

    }

    /**
     * Class constructor.
     *
     * @param cf It is the fiscal code of the payer (member).
     * @param feeDate It is the membership fee.
     */
    public Payment (String cf, LocalDate feeDate ){

        this.setCf(cf);
        this.setFeeDate(feeDate );

    }

    /**
     * Class constructor.
     *
     * @param cf It is the fiscal code of the payer (member).
     * @param idBoat It is the identification code of the boat of the payer (member).
     * @param idRace - It is the identification code of the race.
     * @param raceDate - It is the date of payment the race.
     */
    public Payment (String cf, int idBoat, int idRace, LocalDate raceDate){
        this.setCf(cf);
        this.setIdBoat(idBoat);
        this.setIdRace(idRace);
        this.setRaceDate(raceDate);
    }

    /**
     * This method is used to set the string (date).
     *
     * @param string It is the date.
     */
    public void setString(String string) {this.string = string;}

    /**
     * This method is used to set the fiscal code of the payer (member).
     *
     * @param cf It is the fiscal code of the payer (member).
     */
    public void setCf(String cf) {
        this.cf = cf;
    }

    /**
     * This method is used to set the name of the boat.
     *
     * @param boatName It is the name of the boat.
     */
    public void setBoatName(String boatName)  {this.boatName = boatName ;}

    /**
     * This method is used to set the identification code of the boat.
     *
     * @param idBoat It is the identification code of the boat.
     */
    public void setIdBoat(int idBoat) {
        this.idBoat = idBoat;
    }

    /**
     * This method is used to set the boat storage fee.
     *
     * @param storageQuote It is the boat storage fee.
     */
    public void setStorageQuote(float storageQuote) {
        this.storageQuote = storageQuote;
    }

    /**
     * This method is used to set the date of payment of the boat storage fee.
     *
     * @param storageDate It is the date of payment of the boat storage fee.
     */
    public void setStorageDate(LocalDate storageDate) {
        this.storageDate = storageDate;
    }

    /**
     * This method is used to set the date of payment of the member's membership fee.
     *
     * @param feeDate It is the date of payment of the member's membership fee
     */
    public void setFeeDate(LocalDate feeDate) {
        this.feeDate = feeDate;
    }

    /**
     * This method is used to set the identification code of the race.
     *
     * @param idRace It is the identification code of the race.
     */
    public void setIdRace(int idRace) {this.idRace = idRace;}

    /**
     * This method is used to set the date of the race.
     *
     * @param raceDate It is the date of the race.
     */
    public void setRaceDate(LocalDate raceDate) { this.raceDate = raceDate;}

    /**
     * This method is used to get the date (string).
     *
     * @return It returns the date (string).
     */
    public String getString() { return string; }

    /**
     * This method is used to get the date of payment of the boat storage fee.
     *
     * @return It returns the date of payment of the boat storage fee.
     */
    public LocalDate getStorageDate() {
        return storageDate;
    }

    /**
     * This method is used to get the identification code of the boat.
     *
     * @return It is the identification code of the boat.
     */
    public int getIdBoat() {
        return idBoat;
    }

    /**
     * This method is used to get the fiscal code of the payer (member).
     *
     * @return It is the fiscal code of the payer (member).
     */
    public String getCf() { return cf;}

    /**
     * This method is used to get the name of the boat.
     *
     * @return It is the name of the boat.
     */
    public String getBoatName() {return boatName;}

    /**
     * This method is used to get the identification code of the race.
     *
     * @return It is the identification code of the race.
     */
    public int getIdRace() { return idRace; }

    /**
     * This method is used to get the date of the race.
     *
     * @return It is the date of the race.
     */
    public LocalDate getRaceDate() { return raceDate;}

    /**
     * This method is used to get the date of payment of the member's membership fee.
     *
     * @return It is the date of payment of the member's membership fee.
     */
    public LocalDate getFeeDate() { return feeDate; }

    /**
     * This method is used to get the boat storage fee.
     *
     * @return It is the boat storage fee.
     */
    public float getStorageQuote() { return storageQuote; }

}
