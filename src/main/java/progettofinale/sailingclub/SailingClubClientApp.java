package progettofinale.sailingclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The class {@code SailingClubClientApp } represent client
 * that trie to connect to the server.
 *
 * @author Michele Cairone (284972) - Leonardo Minaudo (297792)
 * @version 1.0
 */

public class SailingClubClientApp extends Application {

    /** {@inheritDoc} **/
    @Override
    public void start(Stage stage) {

        try {


            System.out.println("CLIENT APPLICATION");

            System.out.println("Authors: Michele Cairone (284972) - Leonardo Minaudo (297792)\n");

            FXMLLoader fxmlLoader = new FXMLLoader(SailingClubClientApp.class.getResource("/progettofinale/sailingclub/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.setTitle("Sailing Club");
            stage.setScene(scene);
            stage.show();

        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to execute the client application.
     *
     * @param args the method does not require arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}



