package progettofinale.sailingclub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.xml.sax.SAXException;
import progettofinale.sailingclub.communication.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The class {@code RegisterController} represent
 * the controller where we can control all the buttons and functionality of the Register fxml file.
 * This class inherit from Controller class.
 */
public class RegisterController extends Controller {

    @FXML private TextField newCf;
    @FXML private TextField newName;
    @FXML private TextField newSurname;
    @FXML private TextField newAddress;
    @FXML private TextField newUsername;
    @FXML private TextField newPass;

    /**
     * This method is used to sign up a new user, it sends you an alert message if username or password are exists.
     *
     * @param event The event.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void handleSignIn(ActionEvent event) throws IOException {

        String cf = this.newCf.getText();
        String name = this.newName.getText();
        String surname = this.newSurname.getText();
        String address = this.newAddress.getText();
        String username = this.newUsername.getText();
        String pass = this.newPass.getText();

        if (name.isBlank() || surname.isBlank() || address.isBlank() || pass.isBlank() || username.isBlank() || cf.isBlank()) {

            popUp(Alert.AlertType.ERROR, "Blank fields!", "Registered failed");
            return;
        }

        Request requestRegister = new Request(new Member(name, surname, username, pass, address, cf), RequestType.REGISTER);
        Object o = connection(requestRegister);

        if (o.equals(ResponseType.ALREADY_EXISTING)) {

            popUp(Alert.AlertType.ERROR, "Username already exist!", "Error username!");
            this.newUsername.clear();
            return;
        }

        if (o.equals(ResponseType.NOT_AUTHORIZED)) {

            popUp(Alert.AlertType.ERROR, "Tax code already exist!", "Error tax code!");
            this.newCf.clear();
            return;
        }

        if (o.equals(ResponseType.ADD_MEMBER_SUCCESS)) {

            popUp(Alert.AlertType.INFORMATION, "Registered!", "Client registered");
            handleHomeButton(event);
            return;
        }

        if (o.equals(ResponseType.ERROR_ADD_MEMBER)) popUp(Alert.AlertType.ERROR, "Error Registration", "Please try in a few moment");

    }

}
