package progettofinale.sailingclub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import progettofinale.sailingclub.communication.Request;
import progettofinale.sailingclub.communication.RequestType;
import progettofinale.sailingclub.communication.ResponseType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The class {@code PaymentController} represent
 * the controller where we can control all the buttons and functionality of the Payment fxml file.
 * This class inherit from Controller class.
 */
public class PaymentController extends Controller implements Initializable {


    @FXML private RadioButton radioCard;
    @FXML private RadioButton radioWTR;
    @FXML private TextField cardHolder;
    @FXML private TextField cardNum;
    @FXML private TextField cardMonth;
    @FXML private TextField cardYear;
    @FXML private TextField cardCVV;
    @FXML private Button buttonPayCrd;
    @FXML private TextField codeBT;
    @FXML private Button buttonPayWTR;
    @FXML private Label slash;


    private final ToggleGroup group = new ToggleGroup();
    private static final String VALID_CARDNUM = "[0-9]{16}";
    private static final String VALID_CARDMM = "[0-9]{2}";
    private static final String VALID_CARDYY = "[0-9]{2}";
    private static final String VALID_CARDCVV = "[0-9]{3}";
    private static final String VALID_CODEBT = "[0-9 a-z]{20}";



    /**
     * This method is used initialize a controller after its root element has been fully processed.
     *
     * @param url            It is the location used to resolve relative paths for the root object or null if the location is unknown.
     * @param resourceBundle It is the resources used to locate the root object or null if the root object has not been located.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.radioCard.setToggleGroup(group);
        this.radioCard.setSelected(true);
        this.radioWTR.setToggleGroup(group);

        setTextCard(true);
        setTextWTR(false);

    }

    /**
     * This method is used by the button called "Return" to change scene and return to the member dashboard.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void handleReturn(ActionEvent event) throws IOException {

        changeScene(event, "Member.fxml", "Sailing club");

    }

    /**
     * This method is used in the GUI, specifically by the button called "PAY NOW"
     * in the payment.fxml file to get the Cardholder, Card Number, month, year and CVV do some checks and then call the payment method.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    protected void handlePayCard(ActionEvent event) throws IOException {

        String numberCard = this.cardNum.getText();
        String holderCard = this.cardHolder.getText();
        String month = this.cardMonth.getText();
        String year = this.cardYear.getText();
        String cvv = this.cardCVV.getText();

        if (numberCard.isBlank() || holderCard.isBlank() || month.isBlank() || year.isBlank() || cvv.isBlank()) {
            popUp(Alert.AlertType.ERROR, "Card Holder or Number Card or Month/Year/CVV is blank!", "Blank field");
        }
        if ( (validateCardNum(numberCard)) && (validateCardMM(month)) && (validateCardYY(year)) && (validateCardCVV(cvv))) {

            payment(event);

        }
        else {
            popUp(Alert.AlertType.ERROR, " Please try again", "Payment error");
        }

    }

    /**
     * This method is used in the GUI, specifically by the button called "SEND NOW"
     * in the payment.fxml file to get the Bank Transfer Identification Code do some checks and then call the payment method.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    protected void handlePayWTR(ActionEvent event) throws IOException {

        String codeBt = this.codeBT.getText();
        if (codeBt.isBlank() || !(validateCodeBT(codeBt)) ) {
            popUp(Alert.AlertType.ERROR,"Code is blank or incorrect!","Payment error");
        }
        else {
            payment(event);

        }
    }

    /**
     * This method is used to make all payment requests (storage fee, membership fee and subscribe fee)
     * to the server and finally manage the responses with GUI elements alerts or scene changes.
     *
     * @param event The event that causes the scene change.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void payment(ActionEvent event) throws IOException {


        if (req.getType().equals(RequestType.PAY_SUBSCRIBE)) {

            Request requestAddParticipant = new Request(participant, member.getUsername(), RequestType.ADD_INSCRIPTION);
            Object o = connection(requestAddParticipant);

            if (o.equals(ResponseType.ADD_PARTICIPATION_SUCCESS)) {

                popUp(Alert.AlertType.INFORMATION, "thank you,\n" + "payment was successful.", "Registration completed");
                changeScene(event, "Member.fxml", "Member");

            } else {

                popUp(Alert.AlertType.ERROR, " Please try again", "Payment error");
            }
        }
        else if (req.getType().equals(RequestType.PAY_STORAGE)) {

            Request requestPayStorage = new Request(member.getUsername(), boat.getId(), RequestType.PAY_STORAGE);
            Object o = connection(requestPayStorage);

            if (o.equals(ResponseType.PAY_STORAGE_SUCCESS)) {

                popUp(Alert.AlertType.INFORMATION, "thank you,\n" + "payment was successful.", "Pay storage completed");
                changeScene(event, "Member.fxml", "Member");

            } else {

                popUp(Alert.AlertType.ERROR, " Please try again", "Payment error");
            }

        }
        else if (req.getType().equals(RequestType.PAY_MEMBERSHIP)) {

            Request requestPayMembership = new Request(member.getUsername(), RequestType.PAY_MEMBERSHIP);
            Object o = connection(requestPayMembership);

            if (o.equals(ResponseType.PAY_MEMBERSHIP_SUCCESS)) {

                popUp(Alert.AlertType.INFORMATION, "thank you,\n" + "payment was successful.", "Pay membership completed");
                changeScene(event, "Member.fxml", "Member");
            } else {

                popUp(Alert.AlertType.ERROR, " Please try again", "Payment error");
            }

        }

    }


    private boolean validateCodeBT(String s){

        return s.matches(VALID_CODEBT);
    }



    private boolean validateCardNum(String s){

        return s.matches(VALID_CARDNUM);
    }


    private boolean validateCardMM(String s){

        return s.matches(VALID_CARDMM);
    }


    private boolean validateCardYY(String s){

        return s.matches(VALID_CARDYY);
    }


    private boolean validateCardCVV(String s){

        return s.matches(VALID_CARDCVV);
    }


    private void setTextCard(boolean value) {

        this.cardNum.setVisible(value);
        this.cardHolder.setVisible(value);
        this.cardMonth.setVisible(value);
        this.cardYear.setVisible(value);
        this.cardCVV.setVisible(value);
        this.buttonPayCrd.setVisible(value);
        this.slash.setVisible(value);
    }

    private void setTextWTR(boolean value){
        this.buttonPayWTR.setVisible(value);
        this.codeBT.setVisible(value);
    }


    @FXML
    private void getGroup() {

        if(radioCard.isSelected()) {
            setTextCard(true);
            setTextWTR(false);
        }
        else if(radioWTR.isSelected()) {

            setTextCard(false);
            setTextWTR(true);
        }
    }


}
