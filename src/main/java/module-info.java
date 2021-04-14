module mainTER {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires hsqldb;
    requires java.sql;
    requires java.desktop;
    exports mainTER;
    opens mainTER.Menu;
}