module progettofinale.sailingclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;


    opens progettofinale.sailingclub to javafx.fxml;
    exports progettofinale.sailingclub;
    exports progettofinale.sailingclub.controller;
    opens progettofinale.sailingclub.controller to javafx.fxml;
    exports progettofinale.sailingclub.communication;
    opens progettofinale.sailingclub.communication to javafx.fxml;

}

