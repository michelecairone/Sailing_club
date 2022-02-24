package progettofinale.sailingclub.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import progettofinale.sailingclub.communication.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * The {@code Controller} class represent
 * the controller superclass where there are methods
 * and functions used by the LoginController, MemberController, PaymentController, RegisterController, StaffController subclasses.
 */
public class Controller  {

    /**
     * It is new instance of the Member object.
     */
    public static final Member member = new Member();

    /**
     * It is new instance of the Participant object.
     */
    public static final Participant participant = new Participant();

    /**
     * It is new instance of the Boat object.
     */
    public static final Boat boat = new Boat();

    /**
     * It is new instance of the Request object.
     */
    public static final Request req = new Request();

    /**
     * This method is used to create a new connection to the server
     * to send an object and then read the object that the server sends as a response with HOST = "localhost" and PORT = 4444.
     *
     * @param rq It is the object of type REQUEST that the method transfers to the server.
     * @return the object received from the server response or NULL.
     */
    public Object connection (Request rq) {

        try {

            // host on which the server resides
            String SHOST = "localhost";
            // server port
            int SPORT = 4444;
            Socket client = new Socket(SHOST, SPORT);
            ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());

            os.writeObject(rq);
            os.flush();

            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));

            return is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method is used to make a request to the server
     * and check if that member identified by his username appears in the database,
     * so that he receives a notification (Alert) if any staff has sent it for lack of payment.
     *
     * @param username It is the member username.
     */
    @FXML
    public void checkNotice(String username) {

        Request requestCheckNotice = new Request(username, RequestType.CHECK_NOTICE);
        Object o = connection(requestCheckNotice);

        if (o.equals(ResponseType.NOTICE_ALL)) {
            popUp(Alert.AlertType.INFORMATION,"the staff warns you that you have to pay:\n" +
                    "the membership fee and the boat storage fee","NOTICE OF NON-PAYMENT");
        }
        else if (o.equals(ResponseType.N_STORAGE)){
            popUp(Alert.AlertType.INFORMATION,"the staff warns you that you have to pay:\n" +
                    "the boat storage fee","NOTICE OF NON-PAYMENT");

        }
        else if (o.equals(ResponseType.N_MEMBERSHIP)){
            popUp(Alert.AlertType.INFORMATION,"the staff warns you that you have to pay:\n" +
                    "the membership fee ","NOTICE OF NON-PAYMENT");

        }

    }

    /**
     * This method is used to manage the login button in the Login.fxml file login scene.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void handleHomeButton(ActionEvent event) throws IOException {

        member.setUsername(""); // reset the username of the member.
        changeScene(event, "Login.fxml", "Sailing club");

    }


    /**
     * This method is used to make an alert (pop up) appear, different according to the needs, in the GUI.
     *
     * @param alertType It is the Alert object.
     * @param text      It is the text that appears inside the alert.
     * @param header    It is the header that appears inside the alert.
     */
    @FXML
    public void popUp(Alert.AlertType alertType, String text, String header) {

        Alert alert;

        if (alertType == Alert.AlertType.INFORMATION) alert = new Alert(alertType, text, ButtonType.OK);

        else alert = new Alert(alertType, text);

        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * This method is used to change the scene whenever it is needed by passing parameters.
     *
     * @param event The event that causes the scene change.
     * @param fxml  It is the fxml file, basically it is the destination of the scene change.
     * @param title It is the title of the scene.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void changeScene(final ActionEvent event, final String fxml, final String title) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/progettofinale/sailingclub/" + fxml));
        Parent root = loader.load();
        Scene dashboardScene = new Scene(root); // creates a Scene for a specific root Node
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow(); // replaces the current scene with another scene in the same window
        window.setScene(dashboardScene); // specify the scene to be used on this stage
        window.setTitle(title); // set the title of the window
        window.show();
    }

    /**
     * This method is used to print a list in the GUI by using the TableView class.
     *
     * @param tableView   It is the name of the TableView in the appropriate fxml file.
     * @param list   It is the list you want to print in the GUI.
     */
    @FXML
    public void printListFX(TableView tableView, Object list) {
        ObservableList<Object> oList = FXCollections.observableArrayList();

        oList.addAll((List<Object>) list);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(oList);
    }

    /**
     * This method is used to display the list of boats of a particular member in the GUI, thanks to the printListFX method.
     *
     * @return the object received from the server response, if the request is successful it returns a list of boats.
     */
    @FXML
    public Object viewBoats(TableView tableView, String username) {

        Request requestBoats = new Request(username, RequestType.LIST_BOATS);
        Object o = connection(requestBoats);

        printListFX(tableView,o);
        return o;
    }

    /**
     * This method is used to add a boat type object to the server database and then the added boat is displayed in the GUI table.
     *
     * @param owner It is the owner of the boat you are adding.
     * @param boatName It is the name of the boat you are adding.
     * @param lengthBoat It is the length of the boat you are adding.
     * @param tableView It is the GUI table where the new boat is displayed.
     */
    @FXML
    public void addBoat(String owner, String boatName, String lengthBoat, TableView tableView) {

        if (boatName.isBlank() || lengthBoat.isBlank()){

            popUp(Alert.AlertType.ERROR, "boat name or length is empty!", "Blank fields!\n");
            return;
        }

        int boatLength;

        try{
            boatLength = Integer.parseInt(lengthBoat);
            if(boatLength <= 0) {
                popUp(Alert.AlertType.ERROR, "Instruction fields!", "LENGTH: must be greater than zero!\n");
                return;
            }

        } catch(NumberFormatException ex) {
            popUp(Alert.AlertType.ERROR, "Instruction fields!", "LENGTH: int\n");
            return;
        }
        Boat boat = new Boat(boatName, boatLength, owner);

        Request requestAddBoat = new Request(boat, RequestType.ADD_BOAT);
        Object o = connection(requestAddBoat);
        if (o.equals(ResponseType.ADD_BOAT_SUCCESS)){

            popUp(Alert.AlertType.INFORMATION, "Add boat success", "");
            viewBoats(tableView, owner);
        }
        else {
            popUp(Alert.AlertType.INFORMATION, "Add boat failed", "");
        }
    }


    /**
     *
     * This method is used to remove a boat type object from the server database and consequently from the respective GUI table.
     *
     * @param owner It is the owner of the boat being removed.
     * @param idBoat It is the identification code  of the boat being removed.
     * @param tableView It is the GUI table.
     */
    @FXML
    public void rmvBoat(String owner, String idBoat, TableView tableView) {

        int id;

        try {
            id = Integer.parseInt(idBoat);

        } catch (NumberFormatException ex) {
            popUp(Alert.AlertType.ERROR, "Instruction fields!", "ID: int\n");
            return;
        }

        Request requestRmvBoat = new Request(owner, id, RequestType.RMV_BOAT);
        Object o = connection(requestRmvBoat);

        if (o.equals(ResponseType.RMV_BOAT_SUCCESS)) {

            popUp(Alert.AlertType.INFORMATION, "Remove boat success", "");
            viewBoats(tableView, owner);
            //this.idBoatM.clear();
        } else {
            popUp(Alert.AlertType.ERROR, "Instruction fields!", "ID: NOT FOUND!\n");
        }


    }

    /**
     * This method is used to make a request to the server to receive the list of races present in the server database
     * and then display them in the GUI thanks to the printListFX method.
     *
     * @param tableview It is the GUI table.
     */
    public void viewRace(TableView tableview){

        Request requestRace = new Request(RequestType.LIST_RACE);
        Object o = connection(requestRace);

        printListFX(tableview, o);
    }

    /**
     * This method is used to make a request to the server to receive the membership fee that members must pay.
     *
     * @return the Float object that represents the membership fee.
     */
    @FXML
    protected Float viewMembershipFee() {

        Request requestQuote = new Request(RequestType.GET_MEMBERSHIP_FEE);
        Object o = connection(requestQuote);

        if (o.equals(ResponseType.ERROR)) return null;
        else return (float) o;
    }

}
