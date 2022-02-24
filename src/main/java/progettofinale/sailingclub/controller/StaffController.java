package progettofinale.sailingclub.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import progettofinale.sailingclub.communication.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import static java.lang.Float.parseFloat;


/**
 * The class {@code StaffController} represent
 * the controller where we can control all the buttons and functionality of the Staff fxml file.
 * This class inherit from Controller class.
 */
public class StaffController extends Controller implements Initializable {

    @FXML private TableView boatTableS;
    @FXML private TableView tableS;
    @FXML private TableView tableMember;
    @FXML private TableView raceTable;
    @FXML private TableView tableStorage;
    @FXML private TableView tableFee;
    @FXML private TableView tableRaceP;

    @FXML private ComboBox choiceMember;
    @FXML private ComboBox choiceRace;

    @FXML private Label priceFee;

    @FXML private TextField memberCF;
    @FXML private TextField boatName;
    @FXML private TextField boatLength;
    @FXML private TextField boatIdM;
    @FXML private TextField newName;
    @FXML private TextField newSurname;
    @FXML private TextField newAddress;
    @FXML private TextField newCF;
    @FXML private TextField newUsername;
    @FXML private TextField newPassword;
    @FXML private TextField raceName1;
    @FXML private TextField cfMemberS;
    @FXML private TextField idBoatS;
    @FXML private TextField newPriceFee;
    @FXML private TextField raceQuote1;

    @FXML private TextField memberCfR;
    @FXML private TextField idBoatR;
    @FXML private TextField idRaceR;
    @FXML private TextField raceID1;
    @FXML private TextField sCF;
    @FXML private TextField mCF;

    @FXML private DatePicker raceDate1;


    /**
     * This method is used initialize a controller after its root element has been fully processed.
     *
     * @param url            It is the location used to resolve relative paths for the root object or null if the location is unknown.
     * @param resourceBundle It is the resources used to locate the root object or null if the root object has not been located.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        readUsername();
        viewMembers();
        viewRace(raceTable);
        readRace();
        viewStorage();
        viewMembership();
        viewRaceInscription();
        priceFee.setText(String.valueOf(viewMembershipFee()));


    }

    /**
     * This method is used to display
     * in a table called "boatTableS" in the GUI, all boats that have a member chosen from the combobox called "choiceMember".
     */
    @FXML
    public void handleChoiceMember() {

        viewBoats(boatTableS, String.valueOf(choiceMember.getValue()));
    }

    /**
     * This method is used to add a boat type object to the server database
     * for a specific member and then the added boat is displayed in the GUI table.
     */
    @FXML
    public void handleAddBoatM(){

        addBoat(String.valueOf(choiceMember.getValue()), this.boatName.getText(), this.boatLength.getText(), boatTableS);
        this.boatName.clear();
        this.boatLength.clear();
    }

    /**
     * This method is used to remove a boat type object from the server database and consequently from the respective GUI table.
     */
    @FXML
    public void handleRmvBoatM(){

        rmvBoat(String.valueOf(choiceMember.getValue()), this.boatIdM.getText(), boatTableS);
        this.boatIdM.clear();
    }

    /**
     * This method is used to display all storage fee payments made by members in the GUI table.
     */
    @FXML
    public void handleViewAllStorage(){

        viewStorage();
    }

    /**
     * This method is used to display all membership fee payments made by members in the GUI table.
     */
    @FXML
    public void handleViewAllMemberFee(){

        viewMembership();
    }


    /**
     * This method is used to display,
     * in a combobox named "choiceRace" in the GUI, all the races present in the system.
     */
    @FXML
    public void handleChoiceRace(){

        String nameRace= (String) choiceRace.getValue();

        Request requestP = new Request(nameRace, RequestType.LIST_INSCRIPTION_S);
        Object o = connection(requestP);
        printListFX(tableS, o);
    }

    /**
     * This method is used to display,
     * in a combobox called "choiceMember" in the GUI, all the usernames of the members registered in the system.
     */
    @FXML
    public void readUsername(){

        Request requestUsername = new Request( RequestType.LIST_USERNAME);
        Object o = connection(requestUsername);

        if (!o.equals(ResponseType.NOT_FOUND)){

            choiceMember.getItems().clear();
            for(String us : (List<String>)o) choiceMember.getItems().addAll(us);
        }
    }

    /**
     * This method is used to display,
     * in a table called "tableMember" in the GUI, the list of all personal data of the members enrolled in the system.
     */
    @FXML
    public void viewMembers() {

        Request requestMembers = new Request(RequestType.VIEW_MEMBERS);
        Object o = connection(requestMembers);

        if (!o.equals(ResponseType.NOT_FOUND)) printListFX(tableMember,o);

    }

    /**
     * This method is used to add an object of type Member to the server database
     * and then the added member is displayed in the GUI table.
     */
    @FXML
    public void handleAddMember() {

        String name = this.newName.getText();
        String surname = this.newSurname.getText();
        String address = this.newAddress.getText();
        String username = this.newUsername.getText();
        String pass = this.newPassword.getText();
        String cf = this.newCF.getText();

        Member member = new Member(name, surname, username, pass, address, cf);

        Request requestAddMember = new Request(member, RequestType.ADD_MEMBER);
        Object o = connection(requestAddMember);

        if (o.equals(ResponseType.ADD_MEMBER_SUCCESS)) {

            popUp(Alert.AlertType.INFORMATION, "Add Member success", "");
            viewMembers();
            readUsername();
            this.newName.clear();
            this.newSurname.clear();
            this.newUsername.clear();
            this.newPassword.clear();
            this.newAddress.clear();
            this.newCF.clear();
        }

        else popUp(Alert.AlertType.INFORMATION, "check username or tax code you entered", "Add Member failed");

    }


    /**
     * This method is used to remove a Member type object from the server database and consequently from the respective GUI table.
     */
    @FXML
    public void handleRmvMember() {

        String username = this.newUsername.getText();

        Request requestRmvBoat = new Request(username, RequestType.RMV_MEMBER);
        Object o = connection(requestRmvBoat);

        if (o.equals(ResponseType.RMV_MEMBER_SUCCESS)) {

            popUp(Alert.AlertType.INFORMATION, "Remove member success", "");
            viewMembers();
            readUsername();
            this.newName.clear();
            this.newSurname.clear();
            this.newUsername.clear();
            this.newPassword.clear();
            this.newAddress.clear();
            this.newCF.clear();

        } else popUp(Alert.AlertType.ERROR, "Instruction fields!", "MEMBER: NOT FOUND!\n");

    }

    /**
     * This method is used to add an object of type Race to the server database
     * and then the added race is displayed in the GUI table.
     */
    @FXML
    public void handleAddRace() {

        String name = this.raceName1.getText();
        LocalDate date = this.raceDate1.getValue();
        String payQuote = this.raceQuote1.getText();

        if (name.isBlank() || date == null || payQuote.isBlank()) {

            popUp(Alert.AlertType.ERROR, "Blank fields!", "Add Race failed");
            return;
        }

        try {

            float quote = parseFloat(this.raceQuote1.getText());
            Race race = new Race(name, date, quote);

            Request requestAddRace = new Request(race, RequestType.ADD_RACE);
            Object o = connection(requestAddRace); // client waits for a server response

            if (o.equals(ResponseType.ADD_RACE_SUCCESS)){

                popUp(Alert.AlertType.INFORMATION, "Add Race success", "");
                viewRace(raceTable);

                this.raceName1.clear();
                this.raceQuote1.clear();
            }
            else popUp(Alert.AlertType.INFORMATION, "Add Race failed", "");
        }
        catch (Exception e){
            popUp(Alert.AlertType.ERROR, "Quote: Float", "");
            this.raceQuote1.clear();
        }


    }


    /**
     * This method is used to remove a Race type object from the server database and consequently from the respective GUI table.
     */
    @FXML
    public void handleRmvRace() {

        try {

            int idRace = Integer.parseInt(this.raceID1.getText());

            Request requestRmvRace = new Request(idRace, RequestType.RMV_RACE);
            Object o = connection(requestRmvRace);

            if (o.equals(ResponseType.RMV_RACE_SUCCESS)) {

                popUp(Alert.AlertType.INFORMATION, "Remove race success", "");
                viewRace(raceTable);
                this.raceID1.clear();


            }
            else popUp(Alert.AlertType.ERROR, "RACE: NOT FOUND!\n","" );
        }
        catch (Exception e) {
            popUp(Alert.AlertType.ERROR, "IDRACE: Int", "");
        }

    }

    /**
     * This method is used to display,
     * in a combobox called "choiceRace" in the GUI, all the names of the race registered in the system.
     */
    @FXML
    public void readRace(){

        Request requestRace = new Request(RequestType.LIST_RACE);
        Object o = connection(requestRace);

        choiceRace.getItems().clear();
        for(Race r : (List<Race>)o){
            choiceRace.getItems().addAll(r.getName());
        }

    }

    /**
     * This method is used to display in a table called "tableStorage" in the GUI,
     * the list of all payments of the storage fee of each boat with the tax code of the owner, the id of the boat, the fee paid and the date of payment.
     */
    @FXML
    public void viewStorage() {

        Request requestStorage = new Request(RequestType.VIEW_STORAGE);
        Object o = connection(requestStorage);

        printListFX(tableStorage, o);
    }

    /**
     * This method is used to search in a table called "tableStorage" in the GUI,
     * the storage fee payments of a boat by specifying the id of the boat or the tax code of the owner.
     */
    @FXML
    protected void handleSearchStorage(){
        String cf = this.cfMemberS.getText();
        String idBoat = this.idBoatS.getText();

        Integer idB;

        if (cf.isBlank() && idBoat.isBlank()) {

            popUp(Alert.AlertType.ERROR, "to carry out a search, specify a field or all", "Blank field!");
            return;
        }

        if (!idBoat.isBlank()) {
            try {
                idB = Integer.valueOf(idBoat);
            }
            catch (Exception e) {
                popUp(Alert.AlertType.ERROR, "IDBOAT: Int", "");
                return;
            }

        }
        else idB = null;


        Request requestSearchStorage = new Request(cf, idB, RequestType.SEARCH_STORAGE);
        Object o = connection(requestSearchStorage);

        if (!o.equals(ResponseType.NOT_FOUND)) {

            printListFX(tableStorage,o);
            this.cfMemberS.clear();
            this.idBoatS.clear();
        }

    }

    /**
     * This method is used to display in a table called "tableFee" in the GUI,
     * the list of all payments of the membership fee of each Member with the tax code of the member and the date of payment.
     */
    @FXML
    public void viewMembership() {

        Request requestFee = new Request(RequestType.VIEW_FEE);
        Object o = connection(requestFee);

        printListFX(tableFee,o);
    }

    /**
     * This method is used to update the membership fee that each member enrolled in the system must pay.
     */
    @FXML
    public void handleUpdateFee(){

        String newPriceFee = this.newPriceFee.getText();

        if (newPriceFee.isBlank()) {

            popUp(Alert.AlertType.ERROR, "insert new price", "Blank field!");
            return;
        }

        try {
            Request requestUpdateFee = new Request(parseFloat(newPriceFee), RequestType.UPDATE_FEE);
            Object o = connection(requestUpdateFee);

            if (o.equals(ResponseType.OK)) {

                popUp(Alert.AlertType.INFORMATION, "Update membership fee success", "");
                priceFee.setText(newPriceFee);
                this.newPriceFee.clear();

            } else {
                popUp(Alert.AlertType.ERROR, "","UPDATE FAILED!\n" );
            }
        }
        catch (Exception e) {
            popUp(Alert.AlertType.ERROR, "PriceFee: Float", "");
        }

    }

    /**
     * This method is used to search in a table called "tableFee" in the GUI,
     * the membership fee payments of a Member by specifying the tax code of the member.
     */
    @FXML
    protected void handleSearchMemberFee(){
        String cf = this.memberCF.getText();

        if (cf.isBlank()) {
            popUp(Alert.AlertType.ERROR, "", "Blank field!");
            return;
        }

        Request requestSearchMember = new Request(cf, RequestType.SEARCH_MEMBER_FEE);
        Object o = connection(requestSearchMember);

        printListFX(tableFee,o);
        this.memberCF.clear();
    }

    /**
     * This method is used to display in a table called "tableRaceP" in the GUI,
     * the list of all race entry fee payments made by members to enter their boats.
     */
    @FXML
    public void viewRaceInscription() {

        Request requestRace = new Request(RequestType.VIEW_PARTICIPATION_RACE);
        Object o = connection(requestRace);

        printListFX(tableRaceP,o);
    }

    /**
     * This method is used to display all participants of all races in the GUI table.
     */
    @FXML
    public void handleViewAllInscription(){

        viewRaceInscription();
    }

    /**
     * This method is used to search a table called "tableFee" in the GUI,
     * for boat entry fee payments to participate in a race by specifying the tax ID of the boat owner or the race id or the boat id.
     */
    @FXML
    protected void handleSearchParticipation(){
        String cf = this.memberCfR.getText();
        String idBoat = this.idBoatR.getText();
        String idRace = this.idRaceR.getText();

        if (cf.isBlank() && idBoat.isBlank() && idRace.isBlank()){

            popUp(Alert.AlertType.ERROR, "to carry out a search, specify a field or all", "Blank field!");
            return;
        }

        try {

            Request requestSearchParticipation = new Request(cf,(idRace.isBlank())? null: Integer.valueOf(idRace), (idBoat.isBlank())? null: Integer.valueOf(idBoat), RequestType.SEARCH_PARTICIPATION);
            Object o = connection(requestSearchParticipation);

            printListFX(tableRaceP, o);
            this.memberCfR.clear();
            this.idBoatR.clear();
            this.idRaceR.clear();
        }
        catch (Exception e) {
            popUp(Alert.AlertType.ERROR, "ID BOAT and ID RACE: Int", "");
        }

    }

    /**
     * This method is used to send a notification of non-payment for the storage fee of a member's boats,
     * specifying the tax code of the member you want to warn.
     */
    @FXML
    protected void handleSendNoticeS(){

        String cf = this.sCF.getText();

        if (cf.isBlank()) {

            popUp(Alert.AlertType.ERROR, "", "Blank field!");
            return;
        }

        Request requestSendStorageN = new Request(cf, RequestType.NOTICE_STORAGE);
        Object o = connection(requestSendStorageN);

        if (o.equals(ResponseType.OK)) popUp(Alert.AlertType.INFORMATION, "Notification sent successfully!\n","" );

        else popUp(Alert.AlertType.ERROR, " probably the tax code is wrong\n","Notification not sent!\n" );

    }

    /**
     * This method is used to send a notification of non-payment for a member's membership fee
     * by specifying the tax ID of the member you want to warn.
     */
    @FXML
    protected void handleSendNoticeM(){

        String cf = this.mCF.getText();

        if (cf.isBlank()) {

            popUp(Alert.AlertType.ERROR, "", "Blank field!");
            return;
        }

        Request requestSendStorageN = new Request(cf, RequestType.NOTICE_MEMBERSHIP);
        Object o = connection(requestSendStorageN);

        if (o.equals(ResponseType.OK)) popUp(Alert.AlertType.INFORMATION, "Notification sent successfully!\n","" );

        else popUp(Alert.AlertType.ERROR, " probably the tax code is wrong\n","Notification not sent!\n" );

    }

}
