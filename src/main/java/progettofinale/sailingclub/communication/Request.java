package progettofinale.sailingclub.communication;

import java.io.Serializable;

/**
 *
 * The class {@code Request} represents a request made by a client to the server.
 *
 */
public class Request implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * member - It is a new object of member type.
     * participant - It is a new object of participant type.
     * person - It is a new object of person type.
     * boat - It is a new object of boat type.
     * race - It is a new object of race type.
     * id - It is identification code.
     * idBoat - It is the identification code of the boat.
     * idRace - It is the identification code of the race.
     * quote - It is the fee.
     * username - It is the username of the member.
     * type - It is the type of request.
     */
    private Member member;
    private Participant participant;
    private Person person ;
    private Boat boat;
    private Race race;
    private int id;
    private Integer idBoat;
    private Integer idRace;
    private Float quote;
    private String username;
    private RequestType type;


    /**
     * Class constructor.
     * to instantiate new Person objects without parameters.
     */
    public Request () {}

    /**
     * Class constructor.
     *
     * @param type It is the request type.
     */
    public Request (RequestType type){
        this.type = type;

    }
    /**
     * Class constructor.
     *
     * @param race It is a new object of race type.
     * @param type It is the request type.
     */
    public Request (Race race,RequestType type){
        this.race = race;
        this.type = type;

    }
    /**
     * Class constructor.
     *
     * @param username It is the username of the member.
     * @param type It is the request type.
     */
    public Request (String username,RequestType type){
        this.username = username;
        this.type = type;

    }
    /**
     * Class constructor.
     *
     * @param quote It is the fee.
     * @param type It is the request type.
     */
    public Request (Float quote,RequestType type){
        this.quote = quote;
        this.type = type;

    }

    /**
     * Class constructor.
     *
     * @param participant It is a new object of participant type.
     * @param type It is the request type.
     */
    public Request (Participant participant,String username,RequestType type){

        this.username = username;
        this.participant = participant;
        this.type = type;
    }

    /**
     * Class constructor.
     *
     * @param person It is a new object of person type.
     * @param type It is the request type.
     */
    public Request (Person person,RequestType type){
        this.person = person;
        this.type = type;

    }

    /**
     * Class constructor.
     *
     * @param member It is a new object of member type.
     * @param type It is the request type.
     */
    public Request (Member member,RequestType type){
        this.member = member;
        this.type = type;

    }

    /**
     * Class constructor.
     *
     * @param boat It is a new object of person type.
     * @param type It is the request type.
     */
    public Request (Boat boat, RequestType type){
        this.boat = boat;
        this.type = type;
    }

    /**
     * Class constructor.
     *
     * @param id It is identification code.
     * @param type It is the request type.
     */
    public Request (int id, RequestType type){
         this.id = id;
         this.type = type;
    }


    /**
     * Class constructor.
     *
     * @param cf It is the fiscal Code of the member.
     * @param idBoat It is the identification code of the boat.
     * @param type It is the request type.
     */
    public Request (String cf,Integer idBoat, RequestType type){
        this.username = cf;
        this.idBoat = idBoat;
        this.type = type;
    }

    /**
     * Class constructor.
     *
     * @param owner It is the owner of the boat.
     * @param id It is the identification code of the boat.
     * @param type It is the request type.
     */
    public Request (String owner,int id, RequestType type){
        this.username = owner;
        this.id = id;
        this.type = type;
    }

    /**
     * Class constructor.
     *
     * @param cf It is the fiscal Code of the member.
     * @param idRace It is the identification code of the race.
     * @param idBoat It is the identification code of the boat.
     * @param type It is the request type.
     */
    public Request (String cf,Integer idRace,Integer idBoat, RequestType type){

        this.username = cf;
        this.idRace = idRace;
        this.idBoat = idBoat;
        this.type = type;
    }

    /**
     * This method is used to get the new object of participant type.
     *
     * @return It returns the new object of participant type.
     */
    public Participant getParticipant() { return participant; }

    /**
     * This method is used to get the new object of race type.
     *
     * @return It returns the new object of race type.
     */
    public Race getRace() {
        return race;
    }

    /**
     * This method is used to get the new object of boat type.
     *
     * @return It returns the new object of boat type.
     */
    public Boat getBoat() {
        return boat;
    }

    /**
     * This method is used to get the request type.
     *
     * @return It returns the request type.
     */
    public RequestType getType() {
        return type;
    }

    /**
     * This method is used to get the new object of person type.
     *
     * @return It returns the new object of person type.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * This method is used to get the new object of member type.
     *
     * @return It returns the new object of member type.
     */
    public Member getMember() {
        return member;
    }

    /**
     * This method is used to get the username of the member.
     *
     * @return It returns the username of the member.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method is used to get the identification code.
     *
     * @return It returns the identification code.
     */
    public int getId() { return id; }

    /**
     * This method is used to get the identification code of the boat.
     *
     * @return It returns the identification code of the boat.
     */
    public Integer getIdBoat() { return idBoat; }

    /**
     * This method is used to get the identification code of the race.
     *
     * @return It returns the identification code of the race.
     */
    public Integer getIdRace() {
        return idRace;
    }

    /**
     * This method is used to get It is the fee.
     *
     * @return It returns It is the fee.
     */
    public Float getQuote() {
        return quote;
    }

    /**
     * This method is used to set the identification code.
     *
     * @param id It is the identification code.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method is used to set the request type.
     *
     * @param type It is the request type.
     */
    public void setType(RequestType type) {
        this.type = type;
    }

}
