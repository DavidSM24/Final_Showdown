package Final_Showdown;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Image_View {
	
	@FXML
	private ImageView image_view;
	
	@FXML
	public void setImage(String dir) {

		Image image= new Image(dir);
		image_view.setImage(image);
	
	}
}
