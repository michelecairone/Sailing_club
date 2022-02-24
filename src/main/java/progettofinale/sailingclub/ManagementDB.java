package progettofinale.sailingclub;

import progettofinale.sailingclub.communication.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code ManagementDB} contains all the methods used for manipulating data in the database.
 *
 */
public class ManagementDB  {

    /**
     * Class fields.
     *
     * DB - It is the string to connect to the database.
     *
     */
    private final String DB = "jdbc:mysql://localhost:3306/sailing_club?user=root&password=root&serverTimezone=Europe/Rome";

    /**
     * This method checks if there is a user in the DB.
     *
     * @param person is the user to search for.
     * @return if it finds the user it returns it otherwise it returns null.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized Object queryLogin (Person person) throws SQLException {

        String query = "SELECT * "+
                "FROM person " +
                "WHERE username='"+person.getUsername()+"' AND password='"+person.getPassword()+"'";

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            if (rset.getString("category").equals("M")){

                return new Member( rset.getString("name"),
                        rset.getString("surname"),
                        rset.getString("username"),
                        rset.getString("password"),
                        rset.getString("address"),
                        rset.getString("cf"));
            }

            if (rset.getString("category").equals("S")){

                return new Staff( rset.getString("name"),
                        rset.getString("surname"),
                        rset.getString("username"),
                        rset.getString("password"));
            }
        }

        return null;
    }

    /**
     * This method is used to run queries.
     *
     * @param query is the query to run.
     * @return the ResultSet of the query executed.
     */
    public synchronized ResultSet connectionDB (String query) {
        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            return myStmt.executeQuery(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is used to check for the presence of a username.
     *
     * @param username is the username to search for.
     * @return ResponseType.ALREADY_EXISTING if the username exist else ResponseType.NOT_FOUND.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType verifyUsername (String username) throws SQLException {

        String query= "SELECT * "+
                "FROM person " +
                "WHERE username='" + username + "'";

        ResultSet rset = connectionDB(query);

        if (rset.next()) return ResponseType.ALREADY_EXISTING;

        return ResponseType.NOT_FOUND;
    }

    /**
     * This method recovers boats owned by an owner.
     *
     * @param owner is the owner of the boats to be recovered.
     * @return the list of boats.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Boat> queryBoat (String owner) throws SQLException {

        List<Payment> storageList = queryQuoteBoat1(owner);
        float quoteStorage = queryStorageFee();

        String query= "SELECT * "+
                      "FROM boat " +
                      "WHERE owner='"+owner+"'";

        List<Boat> boatList = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {
            int i = 0;
            for (Payment p : storageList) {
                if (rset.getInt("id") == p.getIdBoat()) {
                    int result = dateCompare(p.getStorageDate());
                    if (result == 1) {
                        boatList.add(new Boat(rset.getInt("id"), rset.getString("name"), rset.getInt("length"), quoteStorage * rset.getInt("length"), String.valueOf(p.getStorageDate().plusYears(1))));
                        i = 1;
                        break;
                    }
                }

            }

            if (i == 0){
                boatList.add(new Boat(rset.getInt("id"), rset.getString("name"), rset.getInt("length"), quoteStorage * rset.getInt("length"), "expired"));

            }

        }

        return boatList;
    }


    /**
     * This method adds a new boat.
     *
     * @param owner is the owner of the boat.
     * @param nameBoat is the name of the boat.
     * @param length is the length of the boat.
     * @return ResponseType.ADD_BOAT_SUCCESS if the addition was successful else ResponseType.ERROR_ADD_BOAT.
     */
    protected synchronized ResponseType queryAddBoat (String owner, String nameBoat, int length) {

        String query= "INSERT INTO boat VALUES ('" + 0 + "','" + nameBoat + "','" + length + "','" + owner + "')";

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            myStmt.executeUpdate(query);

            return ResponseType.ADD_BOAT_SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseType.ERROR_ADD_BOAT;
    }

    /**
     * This method removes a boat
     *
     * @param owner is the owner of the boat.
     * @param id is the id of the boat.
     * @return ResponseType.RMV_BOAT_SUCCESS if the removal was successful else ResponseType.NOT_FOUND.
     */
    protected synchronized ResponseType queryRmvBoat (String owner, int id) {

        String query= "SELECT id " +
                      "FROM boat " +
                      "WHERE id ='"+ id + "' AND owner='" + owner + "'";

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            ResultSet rset = myStmt.executeQuery(query);

            if (rset.next()) {

                String queryRmv = "DELETE FROM boat WHERE id ='" + id + "'";

                try {

                    myStmt.executeUpdate(queryRmv);

                    return ResponseType.RMV_BOAT_SUCCESS;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseType.NOT_FOUND;
    }

    /**
     * This method retrieves all members.
     *
     * @return the list of their usernames.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<String> querySearchMember () throws SQLException {

        String query= "SELECT username "+
                      "FROM person " +
                      "WHERE category = 'M'";

        List<String> username = new ArrayList<>();
        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            username.add(rset.getString("username"));
        }
        return username;
    }

    /**
     * This method retrieves all members.
     *
     * @return the list of members.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Member> queryMember () throws SQLException {

        String query= "SELECT *"+
                      "FROM person " +
                      "WHERE category = 'M'";

        List<Member> member = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            member.add(new Member(rset.getString("name"),
                    rset.getString("surname"),
                    rset.getString("username"),
                    rset.getString("password"),
                    rset.getString("address"),
                    rset.getString("cf")));
        }
        return member;
    }


    /**
     * This method adds a new member.
     *
     * @param member is the member to add.
     * @return ResponseType.ADD_MEMBER_SUCCESS if the addition was successful else ResponseType.ERROR_ADD_MEMBER.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType queryAddMember (Member member) throws SQLException {

        ResponseType result = verifyUsername(member.getUsername());
        String verifyCF = queryVerifyCF(member.getCf());

        if (result.equals(ResponseType.NOT_FOUND) && verifyCF == null) {

            String query = "INSERT INTO person VALUES ('" + member.getCf() + "','" + member.getName() + "','" + member.getSurname() + "','" + member.getAddress() + "','" + member.getUsername() + "','" + member.getPassword() + "','M')";

            try {

                Connection myConn = DriverManager.getConnection(DB);
                Statement myStmt = myConn.createStatement();
                myStmt.executeUpdate(query);

                return ResponseType.ADD_MEMBER_SUCCESS;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseType.ERROR_ADD_MEMBER;
    }

    /**
     * This method removes a member.
     *
     * @param username is the username of the member to be removed.
     * @return ResponseType.RMV_MEMBER_SUCCESS if the removal was successful else ResponseType.NOT_FOUND.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType queryRmvMember (String username) throws SQLException {

        if (verifyUsername(username).equals(ResponseType.ALREADY_EXISTING)) {

            String queryRmv= "DELETE FROM person WHERE username ='" + username + "'";

            try {
                Connection myConn = DriverManager.getConnection(DB);
                Statement myStmt = myConn.createStatement();

                myStmt.executeUpdate(queryRmv);

                return ResponseType.RMV_MEMBER_SUCCESS;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseType.NOT_FOUND;
    }

    /**
     * This method gets all races.
     *
     * @return the list of races.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Race> queryRace () throws SQLException {

        String query= "SELECT * "+
                      "FROM race ";

        List<Race> raceList = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            raceList.add(new Race(rset.getInt("id"),rset.getString("name"),rset.getDate("date").toLocalDate(),rset.getInt("quote")));
        }
        return raceList;
    }

    /**
     * This method selects all race entries of a particular member.
     *
     * @return the list of entries.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Participant> queryParticipant (String owner) throws SQLException {

        String query= "SELECT *" +
                      "FROM boat,participation,race " +
                      "WHERE participation.id_race = race.id AND participation.id_boat = boat.id AND boat.owner ='" + owner + "'";

        List<Participant> inscription = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            inscription.add(new Participant(rset.getInt("id_race"),
                    rset.getInt("id_boat"),
                    rset.getDate("date")));
        }
        return inscription;
    }

    /**
     * This method selects the list of participants in a race.
     *
     * @param nameRace is the name of race.
     * @return the list of entries.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Participant> queryParticipantS (String nameRace) throws SQLException {

        String query= "SELECT *" +
                      "FROM boat,participation,race,person " +
                      "WHERE participation.id_race = race.id AND participation.id_boat = boat.id AND boat.owner = person.username AND race.name ='" + nameRace + "'";

        List<Participant> inscription = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            inscription.add(new Participant(rset.getString("person.name"),
                    rset.getString("surname"),
                    rset.getString("cf"),
                    rset.getInt("id_boat")));
        }
        return inscription;
    }


    /**
     * This method adds a new race.
     *
     * @param race is the race to add.
     * @return ResponseType.ADD_RACE_SUCCESS if the addition was successful else ResponseType.ERROR_ADD_RACE
     */
    protected synchronized ResponseType queryAddRace (Race race) {

        String query = "INSERT INTO race (id,name,date,quote) VALUES ('" + 0 + "','" + race.getName() + "','" + race.getDate() + "','" + race.getQuote() + "')";

            try {
                Connection myConn = DriverManager.getConnection(DB);
                Statement myStmt = myConn.createStatement();
                myStmt.executeUpdate(query);

                return ResponseType.ADD_RACE_SUCCESS;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        return ResponseType.ERROR_ADD_RACE;
    }

    /**
     * This method removes a Race.
     *
     * @param id is the id of the race to be removed
     * @return ResponseType.RMV_RACE_SUCCESS if the removal was successful else ResponseType.NOT_RACE
     */
    protected synchronized ResponseType queryRmvRace (int id) {

        String query= "SELECT id " +
                      "FROM race " +
                      "WHERE id ='" + id + "'";

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            ResultSet rset = myStmt.executeQuery(query);

            if(rset.next()){
                String queryRmv= "DELETE FROM race WHERE id ='" + id + "'";

                try {

                    myStmt.executeUpdate(queryRmv);

                    return ResponseType.RMV_RACE_SUCCESS;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseType.NOT_RACE;
    }

    /**
     * This method checks for the existence of a race and if so, returns its entry fee.
     *
     * @param id is the id of the race to be checked
     * @return the registration fee returns or null if the race does not exist.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized Float queryVerifyRace (int id) throws SQLException {

        Float quote = null;

        String query= "SELECT * " +
                      "FROM race " +
                      "WHERE id ='" + id + "'";

        ResultSet rset = connectionDB(query);

        if (rset.next()) {

            quote = rset.getFloat("quote");

        }
        return quote;
    }


    /**
     * This method enrolls a boat for a race.
     *
     * @param idRace is the id of the race.
     * @param idBoat is the id of the boat to add.
     * @return ResponseType.ADD_PARTICIPATION_SUCCESS if the inscription was successful else ResponseType.ERROR_ADD_PARTICIPATION.
     */
   protected synchronized ResponseType queryAddParticipation (int idRace, int idBoat) {

       Date dateCurrent = date();

       String query = "INSERT INTO participation  VALUES ('" + idRace + "','" + idBoat + "','" + dateCurrent + "')";

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();
            myStmt.executeUpdate(query);

            return ResponseType.ADD_PARTICIPATION_SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseType.ERROR_ADD_PARTICIPATION;
   }


    /**
     * This method selects a boat id.
     *
     * @param participant is the object containing the name of the boat.
     * @param username is the owner's username.
     * @return the boat id or 0 if it can't find the boat.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
   protected synchronized int getIdBoat (Participant participant, String username) throws SQLException {

        String query = "SELECT *" +
                      "FROM boat " +
                      "WHERE name ='" + participant.getNameBoat() + "' AND owner ='" + username + "'";

       int idBoat = 0;

       ResultSet rset = connectionDB(query);

       while (rset.next()) {

           idBoat = rset.getInt("id");
       }

       return idBoat;
   }

    /**
     * This method checks for the presence of a boat.
     *
     * @param owner is the owner's username.
     * @param id is the id of the boat.
     * @return ResponseType.OK if it finds the boat else ResponseType.NOT_FOUND.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
   protected synchronized ResponseType queryVerifyBoat (String owner, int id) throws SQLException {

        String query = "SELECT id "+
                      "FROM boat " +
                      "WHERE id ='" + id + "' AND owner='" + owner + "'";

        ResultSet rset = connectionDB(query);

        if(rset.next()) return ResponseType.OK;

        return ResponseType.NOT_FOUND;
   }

    /**
     * This method recovers the boats owned by an owner and their storage quote.
     *
     * @param owner is the owner's username.
     * @return the list of boats.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Boat> queryQuoteBoat (String owner) throws SQLException {

        List<Boat> boatList = new ArrayList<>();

        String query= "SELECT * " +
                      "FROM boat " +
                      "WHERE owner='" + owner + "'";

        float quote = queryStorageFee();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            boatList.add(new Boat(rset.getInt("id"), rset.getString("name"), rset.getInt("length"), rset.getInt("length")*quote));
        }

        return boatList;
    }


    /**
     * This method recovers the payments owned by an owner, their storage quote and the expiration date of storage.
     *
     * @param owner is the owner's username.
     * @return the list of boats with relative payment.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Payment> queryQuoteBoat1 (String owner) throws SQLException {


        String queryS = "SELECT boat.id, boat.name, boat.length, max(storage_fee.payment_date) " +
                       "FROM storage_fee, boat  " +
                       "WHERE storage_fee.id_boat = boat.id AND boat.owner ='" + owner + "'" +
                       "GROUP BY boat.id";



        List<Payment> storageList = new ArrayList<>();
        float quote = queryStorageFee();

        ResultSet rset = connectionDB(queryS);

        while (rset.next()) {

            storageList.add(new Payment(rset.getInt("id"), rset.getString("name"), (rset.getInt("length")*quote), rset.getDate("MAX(storage_fee.payment_date)").toLocalDate()));

        }

        return storageList;
    }


    /**
     * This method selects the share of the storage.
     *
     * @return the storage fee.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized float queryStorageFee () throws SQLException {

        String query= "SELECT *" +
                      "FROM payment_fee " +
                      "WHERE pay_type = 'storage'";

        ResultSet rset = connectionDB(query);

        float quoteFee = 0.0F;
        while (rset.next()) {

            quoteFee = rset.getFloat("quote");
        }

        return quoteFee;
    }


    /**
     * This method selects the membership fee.
     *
     * @return the membership fee.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized float queryMemberShipFee () throws SQLException {

        String query= "SELECT *" +
                      "FROM payment_fee " +
                      "WHERE pay_type = 'membership'";

        float quoteFee= 0.0F;
        ResultSet rset =connectionDB(query);

        while (rset.next()) {

            quoteFee = rset.getFloat("quote");
        }
        return quoteFee;
    }

    /**
     * This method saves you from paying a storage fee.
     *
     * @param idBoat is the id of the boat to which the tax has been paid.
     * @param owner is the owner's username.
     * @return ResponseType.PAY_STORAGE_SUCCESS if the save was successful else ResponseType.ERROR.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType queryPayStorage (int idBoat, String owner) throws SQLException {

        float quote = 0;
        Date dateCurrent = date();

        List<Boat> boats = queryQuoteBoat(owner);
        for (Boat b : boats) {
            if (b.getId() == idBoat) {
                quote = b.getStorage();
            }
        }

        String query = "INSERT INTO storage_fee  VALUES ('" + idBoat + "','" + dateCurrent + "','" + quote + "')";

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            int result = myStmt.executeUpdate(query);
            if (result == 1) return ResponseType.PAY_STORAGE_SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseType.ERROR;
    }

    /**
     * This method verifies the payment of storage fees.
     *
     * @param idBoat is the id of the boat.
     * @return ResponseType.EXPIRED if the fee has expired,
     *         ResponseType.NOT_EXPIRED if the fee has not expired
     *         else ResponseType.NOT_FOUND if there is no payment.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType queryVerifyStorage (int idBoat) throws SQLException {

        String query= "SELECT MAX(payment_date)" +
                      "FROM storage_fee " +
                      "WHERE id_boat ='" + idBoat + "'";

        int count = count("FROM storage_fee " +
                                "WHERE id_boat ='" + idBoat + "'");
        if (count > 0) {

            String date;
            ResultSet rset = connectionDB(query);

            if (rset.next()) {

                date = String.valueOf(rset.getDate("MAX(payment_date)"));

                int result = dateCompare(LocalDate.parse(date));

                if (result == 0) return ResponseType.EXPIRED;
                else return ResponseType.NOT_EXPIRED;
            }

        }

        return ResponseType.NOT_FOUND;
    }

    /**
     * This method checks the expiration date of the membership fee.
     *
     * @param username is the username of the member.
     * @return the expiration date or ResponseType.EXPIRED if it has never been paid.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized Object queryVerifyDateExp (String username) throws SQLException {

        String query= "SELECT MAX(payment_date)"+
                      "FROM membership_fee " +
                      "WHERE payer_id ='" + username + "'";

        int resultCount = count("FROM membership_fee " +
                                "WHERE payer_id ='" + username + "'");

        if (resultCount > 0) {
            String date;

            ResultSet rset = connectionDB(query);

            while (rset.next()) {

                date = String.valueOf(rset.getDate("MAX(payment_date)"));

                int result = dateCompare(LocalDate.parse((date)));

                if (result == 1) return LocalDate.parse(date).plusYears(1).format(DateTimeFormatter.ISO_DATE);

            }
        }

        return ResponseType.EXPIRED;
    }

    /**
     * This method inserts a payment of the membership fee.
     *
     * @param username is the username of the member.
     * @return ResponseType.PAY_MEMBERSHIP_SUCCESS if the entry was successful else ResponseType.ERROR.
     */
    protected synchronized ResponseType queryPayMembership (String username) {

        Date dateCurrent = date();

        String query = "INSERT INTO membership_fee  VALUES ('" + username + "','" + dateCurrent + "')";

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            int result = myStmt.executeUpdate(query);
            if (result == 1) return ResponseType.PAY_MEMBERSHIP_SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseType.ERROR;
    }


    /**
     * This method selects all of the storage payments made.
     *
     * @return all of the storage payments made.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected List<Payment> queryViewStorage () throws SQLException {

        String query= "SELECT * "+
                      "FROM storage_fee, person, boat  " +
                      "WHERE storage_fee.id_boat = boat.id AND person.username = boat.owner";

        return getPayments(query);
    }

    private List<Payment> getPayments(String query) throws SQLException {
        List<Payment> storageList = new ArrayList<>();
        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            storageList.add(new Payment(rset.getString("person.cf"), rset.getInt("storage_fee.id_boat"), rset.getFloat("storage_fee.quote"), rset.getDate("storage_fee.payment_date").toLocalDate()));
        }
        return storageList;
    }

    /**
     * This method searches for storage payments by tax code or boat id.
     *
     * @param cf is the owner's tax code.
     * @param idBoat the id of boat.
     * @return the list of payments.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected List<Payment> querySearchStorage (String cf, Integer idBoat) throws SQLException {

        String query = "SELECT * " +
                "FROM storage_fee, person, boat  " +
                "WHERE storage_fee.id_boat = boat.id AND person.username = boat.owner ";

        String where = "";
        if (!cf.equals("")) where += "AND person.cf = '" + cf + "' ";
        if (idBoat != null) where += "AND boat.id = '" + idBoat + "' ";

        query += where;

        return getPayments(query);
    }

    /**
     * This method selects all members who have paid the membership fee.
     *
     * @return the list of all members who have paid the membership fee.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected List<Payment> queryViewMembership () throws SQLException {

        String query= "SELECT * "+
                      "FROM membership_fee, person  " +
                      "WHERE membership_fee.payer_id = person.username";

        return queryListMember(query);
    }

    /**
     * This method changes the membership fee.
     *
     * @param quote is the membership fee.
     * @return ResponseType.OK if the operation was successful else ResponseType.ERROR.
     */
    protected ResponseType queryUpdateMembership (Float quote) {

        String query = "UPDATE payment_fee SET quote = '" + quote + "' WHERE pay_type = 'membership'";

        if (connResultDB(query)) return ResponseType.OK;

        return ResponseType.ERROR;
    }

    /**
     * This method connects to the DB and queries it.
     *
     * @param query is the query to run.
     * @return true if the operation was successful else false.
     */
    private boolean connResultDB(String query) {
        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            int result = myStmt.executeUpdate(query);
            if (result == 1) return true;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method searches by fiscal code among the payments of the membership fee.
     *
     * @param cf is the owner's tax code.
     * @return the list of the payments of the membership fee.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected List<Payment> querySearchMemberFee (String cf) throws SQLException {

        String query = "SELECT * " +
                       "FROM membership_fee, person  " +
                       "WHERE membership_fee.payer_id = person.username AND person.cf = '" + cf + "'";

        return queryListMember(query);
    }

    private List<Payment> queryListMember (String query) throws SQLException {
        List<Payment> member = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            member.add(new Payment(rset.getString("person.cf"), rset.getDate("membership_fee.payment_date").toLocalDate()));
        }

        return member;
    }



    /**
     * This method selects all payments to race registrations.
     *
     * @return the list of payments to race registrations.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized List<Payment> queryRaceInscription () throws SQLException {

        List<Payment> inscripRace = new ArrayList<>();

        String query= "SELECT *"+
                      "FROM boat,participation,person " +
                      "WHERE participation.id_boat = boat.id AND boat.owner = person.username ";

        ResultSet rset = connectionDB(query);

        while (rset.next()){

            inscripRace.add(new Payment(rset.getString("person.cf"),
                    rset.getInt("id_boat"),
                    rset.getInt("id_race"),
                    rset.getDate("pay_date").toLocalDate()));
        }

        return inscripRace;
    }


    /**
     * This method searches through the tender payments by fiscal code, race id or boat id.
     *
     * @param cf is the tax code.
     * @param idRace is the id of race.
     * @param idBoat is the id of boat.
     * @return the filtered list.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected List<Payment> querySearchRaceParticipant (String cf, Integer idRace, Integer idBoat) throws SQLException {

        String query = "SELECT * " +
                "FROM boat,participation,person " +
                "WHERE participation.id_boat = boat.id AND boat.owner = person.username ";

        String where = "";
        if (!cf.equals("")) where += "AND person.cf = '" + cf + "' ";
        if (idBoat != null) where += "AND boat.id = '" + idBoat + "' ";
        if (idRace != null) where += "AND participation.id_race = '" + idRace + "' ";

        query += where;

        List<Payment> partecipantList = new ArrayList<>();

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            partecipantList.add(new Payment(rset.getString("person.cf"), rset.getInt("participation.id_boat"), rset.getInt("participation.id_race"), rset.getDate("participation.pay_date").toLocalDate()));
        }

        return partecipantList;
    }

    /**
     * This method inserts a notification
     *
     * @param cf is the tax code of the person to be notified.
     * @param noticeType is the notice type.
     * @return ResponseType.OK the operation was successful else ResponseType.ERROR.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType queryAddNotice (String cf, String noticeType) throws SQLException {

        String username = queryVerifyCF(cf);
        if (username != null) {
            String query = "INSERT INTO notice VALUES ('" + username + "','" + noticeType + "')";

            if (connResultDB(query)) return ResponseType.OK;
        }

        return ResponseType.ERROR;

    }

    /**
     * This method checks if a member has any notifications to display.
     *
     * @param username is the member's username.
     * @return ResponseType.N_STORAGE for storage notification, ResponseType.N_MEMBERSHIP for membership fee notification,
     * ResponseType.NOTICE_ALL for membership fee and storage notification, ResponseType.ERROR for no notification.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized ResponseType queryVerifyNotice (String username) throws SQLException {

        String query= "SELECT *"+
                      "FROM person, notice " +
                      "WHERE person.username = notice.member AND person.username ='" + username + "'";

        int resultCount = count("FROM person, notice " +
                                      "WHERE person.username = notice.member AND person.username ='" + username + "'");


        ResultSet rset = connectionDB(query);

        if (resultCount >= 2) return ResponseType.NOTICE_ALL;

        while (rset.next()) {
            String type = rset.getString("type_notice");
            if (type.equals("storage")) return ResponseType.N_STORAGE;
            if (type.equals("membership")) return ResponseType.N_MEMBERSHIP;
        }


        return ResponseType.ERROR;
    }

    /**
     * This method checks if a tax code is already registered.
     *
     * @param cf is the tax code to search for.
     * @return the username if the tax code is already registered otherwise null.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized String queryVerifyCF (String cf) throws SQLException {

        String query = "SELECT *" +
                       "FROM person " +
                       "WHERE person.cf = '" + cf + "'";

        String username = null;

        ResultSet rset = connectionDB(query);

        while (rset.next()) {

            username = rset.getString("username");

        }

        return username;
    }

    /**
     * This method verifies payments and clears notifications if so.
     *
     * @param username is the owner's username.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    protected synchronized void queryVerifyPayment (String username) throws SQLException {

        String queryS = "DELETE FROM notice WHERE type_notice = 'storage' AND member ='" + username + "'";
        String queryM = "DELETE FROM notice WHERE type_notice = 'membership' AND member ='" + username + "'";

        Object dateFee = queryVerifyDateExp(username);
        List<Boat> boatL = queryBoat(username);

        Connection myConn = DriverManager.getConnection(DB);
        Statement myStmt = myConn.createStatement();
        int counter = 0;


        for (Boat b : boatL) {

            ResponseType rs = queryVerifyStorage(b.getId());

            if (rs.equals(ResponseType.EXPIRED) || rs.equals(ResponseType.NOT_FOUND) ){
                counter++;

            }

        }

        if(counter == 0) {

            try {
                myStmt.executeUpdate(queryS);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (dateFee != ResponseType.EXPIRED){

            try {
                   myStmt.executeUpdate(queryM);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
        }

    }


    /**
     * This method counts the number of rows that a query returns.
     *
     * @param query is the query to be analyzed.
     * @return the number of tuples.
     */
    protected synchronized int count (String query) {

        String queryCount = "SELECT COUNT(*)" + query;
        ResultSet rcount;
        int rcount2 = 0;

        try {

            Connection myConn = DriverManager.getConnection(DB);
            Statement myStmt = myConn.createStatement();

            rcount = myStmt.executeQuery(queryCount);

            if (rcount.next()) rcount2 = rcount.getInt(1);

            myStmt.close();
            myConn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rcount2;
    }

    /**
     * This method compares a date with the current one.
     *
     * @param date is the date to be compared.
     * @return 0 if a year has passed since the date entered else 1.
     */
    protected int dateCompare (LocalDate date) {

        LocalDate ex = date.plusYears(1);
        LocalDate now = LocalDate.now();

        int result = ex.compareTo(now);
        if(result < 0) return 0;

        else return 1;
    }

    private Date date() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        return Date.valueOf(dtf.format(now));
    }

}
