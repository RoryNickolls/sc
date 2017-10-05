package nickolls.rory.sc01;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ScraperUIController {

	@FXML
	private Button btn_findName;

	@FXML
	private TextField txtFld_email;

	@FXML
	private Label lbl_name;

	@FXML
	private void initialize() {
		btn_findName.setOnAction((event) -> {

			lbl_name.setText("Connecting...");

			// scraper MUST be run in separate thread
			// else UI will lock up
			Thread scraper = new Thread(new NameScraper(txtFld_email.getText(), (result) -> {
				// UI elements can only change state on the JavaFX thread
				Platform.runLater(new Runnable() {
					public void run()
					{
						lbl_name.setText((String) result);
					}
				});
				
			}));
			scraper.start();

		});
	}
}
