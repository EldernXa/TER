module mainTER {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires hsqldb;
    requires java.sql;
    requires java.desktop;
    requires javafx.media;
	requires javafx.graphics;
    exports mainTER;
    opens mainTER.Menu;
    opens mainTER.Tools;
}