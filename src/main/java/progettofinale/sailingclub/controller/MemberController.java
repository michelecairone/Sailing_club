package progettofinale.sailingclub.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import progettofinale.sailingclub.communication.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The class {@code MemberController} represent
 * the controller where we can control all the buttons and functionality of the Member fxml file.
 * This class inherit from Controller class.
 */
public class MemberController extends Controller implements Initializable {

    private List<Participant> listP;
    private List<Boat> boats;

    @FXML private TableView tableBoatM;
    @FXML private TableView tableStorage;
    @FXML private TableView tableRace1;
    @FXML private TableView tableInscript;
    @FXML private TextField boatName;
    @FXML private TextField boatLength;
    @FXML private TextField idBoatM;
    @FXML private TextField raceId;
    @FXML private TextField boatID;
    @FXML private ComboBox choiceBoat;
    @FXML private Label priceMembership;
    @FXML private Label dateExpire;



    /**
     * This method is used initialize a controller after its root element has been fully processed.
     *
     * @param url            It is the location used to resolve relative paths for the root object or null if the location is unknown.
     * @param resourceBundle It is the resources used to locate the root object or null if the root object has not been located.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boats = (List<Boat>) viewBoats(tableBoatM, member.getUsername());
        readBoat();
        viewRace(tableRace1);
        readInscription();
        viewBoatQuote();
        priceMembership.setText(String.valueOf(viewMembershipFee()));
        dateExpire.setText(viewExpire());
        checkPayment();

    }

    /**
     * This method is used to make a request to the server and receive the membership fee expiration date
     * or a string if the date has already expired.
     *
     * @return returns the expiration date or a string that says "expired"
     */
    protected String viewExpire() {

        Request req = new Request(member.getUsername(), RequestType.VERIFY_EXPIRE_DATE);
        Object o = connection(req);

        if (o instanceof String) {
            return (String) o;

        }

        return "expired";
    }


    /**
     * This method is used to add a boat type object to the server database and then the added boat is displayed in the GUI table.
     */
    public void handleAddBoat() {

        addBoat(member.getUsername(), this.boatName.getText(), this.boatLength.getText(), tableBoatM);
        this.boatName.clear();
        this.boatLength.clear();
    }

    /**
     * This method is used to remove a boat type object from the server database and consequently from the respective GUI table.
     */
    public void handleRmvBoat() {

        rmvBoat(member.getUsername(), this.idBoatM.getText(), tableBoatM);
        this.idBoatM.clear();
    }

    /**
     * This method requests the server for the list of boats
     * of a specific member and then adds the list of boat names in the combobox named choiceBoat.
     */
    @FXML
    public void readBoat() {

        Request requestBoats = new Request(member.getUsername(), RequestType.LIST_BOATS);
        Object o = connection(requestBoats);

        choiceBoat.getItems().clear();

        for (Boat us : (List<Boat>) o) {
            choiceBoat.getItems().addAll(us.getName());
        }
    }

    /**
     * This method requests the server for the list of participation in the races of a specific member
     * and then displays the list in the GUI thanks to the printListFX method.
     */
    @FXML
    public void readInscription() {

        Request requestInscription = new Request(member.getUsername(), RequestType.LIST_INSCRIPTION);
        Object o = connection(requestInscription);

        listP = (List<Participant>) o;
        printListFX(tableInscript, o);
    }

    /**
     * This method is used to register a specific boat in a specific race,
     * first you need to make the registration payment managed by the Payment.fxml and PaymentController.java file.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void handleSubscribe(ActionEvent event) throws IOException {

        if ((this.raceId.getText().isBlank() || this.choiceBoat.getValue() == null)) {

            popUp(Alert.AlertType.INFORMATION, "Not choice boat!\n or \n Not choice race!", "");
            return;
        }

        int idRace;

        try {
            idRace = Integer.parseInt(this.raceId.getText());

        } catch (Exception e) {
            popUp(Alert.AlertType.INFORMATION, "ID RACE: Int", "");
            return;
        }

        String nameBoat = (String) this.choiceBoat.getValue();
        int id = 0;
        for (Boat b : boats) {

            if (b.getName().equals(nameBoat)) id = b.getId();

        }

        if (listP != null) {
            for (Participant p : listP) {
                if ((p.getIdRace() == idRace) && (p.getIdBoat() == id)) {
                    popUp(Alert.AlertType.INFORMATION, "Already registered", "");
                    return;
                }
            }
        }

        Request verifyRace = new Request(idRace, RequestType.VERIFY_ID_RACE);
        Object o = connection(verifyRace);

        if (o.equals(ResponseType.NOT_RACE)) {
            popUp(Alert.AlertType.ERROR, "ID RACE NOT FOUND", "");

        }
        else {

            Float quote = (Float) o;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Proceed to pay the registration fee.\n\n" +
                    "Participation fee: " + quote + "€\n\n" +
                    "DO YOU WANT TO PROCEED?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                participant.setNameBoat((String) this.choiceBoat.getValue());
                participant.setIdRace(idRace);
                req.setType(RequestType.PAY_SUBSCRIBE);

                changeScene(event, "Payment.fxml", "Payment");
            }

        }

    }

    /**
     * This method is used to display the list of boats
     * with the following parameters id, name, storage fee and date of payment of the fee
     * of a particular member in the GUI, thanks to the printListFX method.
     */
    @FXML
    public void viewBoatQuote() {
        Request requestBoats = new Request(member.getUsername(), RequestType.LIST_BOAT_Q);
        Object o = connection(requestBoats);

        printListFX(tableStorage, o);
    }

    /**
     * This method is used by the button of the Member.fxml file called "Pay Storage"
     * to pay the storage fee for each boat identified with its id,
     * the payment is managed by the PaymentController and Payment.fxml file
     *
     * @param event The event that causes the scene change.
     */
    @FXML
    public void handlePayStorage(ActionEvent event) {

        if ((this.boatID.getText().isBlank())) {

            popUp(Alert.AlertType.INFORMATION, "Not choice boat!\n", "");
            return;
        }

        try {
            int idBoat = Integer.parseInt(this.boatID.getText());
            Request verifyBoat = new Request(member.getUsername(), idBoat, RequestType.VERIFY_ID_BOAT);
            Object o = connection(verifyBoat);

            if (o.equals(ResponseType.NOT_FOUND)) {

                popUp(Alert.AlertType.ERROR, "ID BOAT NOT FOUND", "");
                return;
            }

            Request verifyStorage = new Request(idBoat, RequestType.VERIFY_STORAGE);
            Object j = connection(verifyStorage);

            if (j.equals(ResponseType.EXPIRED) || j.equals(ResponseType.NOT_FOUND)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setHeaderText("Proceed to pay the storage fee.\n\n" +
                        "DO YOU WANT TO PROCEED?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    req.setType(RequestType.PAY_STORAGE);
                    boat.setId(idBoat);
                    changeScene(event, "Payment.fxml", "Payment");
                }

            }
            else popUp(Alert.AlertType.INFORMATION, "you have already paid for the storage of this boat", "");

        }
        catch (Exception e) {
            popUp(Alert.AlertType.ERROR, "ID BOAT: Int", "");
        }

    }

    /**
     * This method is used by the button of the Member.fxml file called "Pay Fee"
     * to pay the membership fee,
     * the payment is managed by the PaymentController and Payment.fxml file
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void handlePayMembership(ActionEvent event) throws IOException {

        if (dateExpire.getText().equals("expired")) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setHeaderText("Proceed to pay the membership fee.\n\n" +
                    "DO YOU WANT TO PROCEED?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                req.setType(RequestType.PAY_MEMBERSHIP);

                changeScene(event, "Payment.fxml", "Payment");

            }
            return;
        }

        popUp(Alert.AlertType.ERROR, "The membership fee is still valid!", "");
    }

    /**
     * This method requests the server to verify the payments made by the member
     * and if the member has made all the required payments the server removes the non-payment notifications.
     */
    @FXML
    public void checkPayment() {

        Request requestP = new Request(member.getUsername(), RequestType.CHECK_PAY);
        connection(requestP);

    }

}
