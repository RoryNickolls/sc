package nickolls.rory.sc01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScraperUI extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("ScraperUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "152.78.128.51");
		System.getProperties().put("proxyPort", 3128);
		launch(args);
	}
}
