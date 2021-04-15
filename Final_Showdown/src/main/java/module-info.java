module Final_Showdown {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires javafx.media;
	requires javafx.base;
	requires javafx.graphics;
	requires java.xml.bind;

	
    opens Final_Showdown to javafx.fxml;
    opens models.P_Character to java.xml.bind,java.xml,java.xml.crypto;
    exports Final_Showdown;
}