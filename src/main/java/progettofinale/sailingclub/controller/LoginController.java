package progettofinale.sailingclub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.xml.sax.SAXException;
import progettofinale.sailingclub.communication.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * The class {@code LoginController} represent
 * the controller where we can control all the buttons and functionality of the Login fxml file.
 * This class inherit from Controller class.
 */
public class LoginController extends Controller {

    @FXML private TextField userName;
    @FXML private TextField pass;

    /**
     * This method is used to handle the contact us button.
     */
    @FXML
    public void handleContactUsLink() {

        popUp(Alert.AlertType.INFORMATION, "Nome - Michele Cairone\nMatricola - 284972\nNome - Leonardo Minaudo\nMatricola - 297792", "Contact Us");
    }

    /**
     * This method is used to handle the press of "Enter" to make login.
     *
     * @param event The event.
     * @throws IOException                  Signals that an I/O exception has occurred.
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws SAXException                 Encapsulate a general SAX error or warning.
     */
    @FXML
    public void onEnter(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {

        handleLoginButton(event);
    }

    /**
     * This method is used to handle the Register Now button.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void onRegister(ActionEvent event) throws IOException {

        changeScene(event, "Register.fxml", "Registration");
    }

    /**
     * This method is used to handle the login button.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void handleLoginButton(ActionEvent event) throws IOException {

        String userName = this.userName.getText();
        String pass = this.pass.getText();
        member.setUsername(userName);

        if (userName.isBlank() || pass.isBlank()) popUp(Alert.AlertType.ERROR, "Blank fields!", "Login failed");

        else {

            Request requestLogin = new Request(new Person(userName,pass), RequestType.LOGIN);
            Object o = connection(requestLogin);

            if (o instanceof Staff) {
                changeScene(event, "Staff.fxml", "Staff");

            }
            else if (o instanceof Member) {
                changeScene(event, "Member.fxml", "Member");
                checkNotice(member.getUsername());
            }
            else if (o.equals(ResponseType.NOT_FOUND)) {
                popUp(Alert.AlertType.WARNING, "TRY AGAIN", "Username/password is incorrect or you are not registered");
            }

        }

    }

}
