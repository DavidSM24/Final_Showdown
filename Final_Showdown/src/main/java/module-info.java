module Final_Showdown {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	requires javafx.graphics;
	requires java.xml.bind;
	requires javafx.media;
	requires java.sql;
	requires java.xml;
	requires mail;
	requires jdk.crypto.ec;
	requires java.base;
	requires google.api.services.drive.v3.rev197;

    opens Final_Showdown;
    opens models.P_Character to java.xml.bind,java.xml,java.xml.crypto;
    opens utils to java.xml.bind,java.xml.crypto;
    exports Final_Showdown;
}