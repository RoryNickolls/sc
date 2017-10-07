package nickolls.rory.sc01;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ScraperUIController {

	@FXML
	private Button btn_findName;
	
	@FXML
	private Button btn_findUrl;
	
	@FXML
	private TextField txtFld_email;
	
	@FXML
	private TextField txtFld_name;
	
	@FXML
	private TextField txtFld_url;

	@FXML
	private Label lbl_name;
	

	@FXML
	private void initialize() {
		// lambda expressions!
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
		
		btn_findUrl.setOnAction((event) -> {
			txtFld_url.setText("Connecting...");
			
			Thread scraper = new Thread(new UrlScraper(txtFld_name.getText(), (result) -> {
				Platform.runLater(new Runnable() {
					public void run()
					{
						txtFld_url.setText((String) result);
						
						// open a browser showing the link
						try {
							Desktop.getDesktop().browse((new URI((String)result)));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}));
			
			scraper.start();
		});
	}
}
