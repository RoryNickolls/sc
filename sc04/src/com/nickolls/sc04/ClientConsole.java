package com.nickolls.sc04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientConsole extends Application {
	
	private ClientConsoleController consoleController;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientConsole.fxml"));
		Parent root = loader.load();
		consoleController = (ClientConsoleController) loader.getController();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Client client = new Client(this);
		consoleController.setClient(client);
	}
	
	public ClientConsoleController getConsoleController()
	{
		return this.consoleController;
	}
	
	public static void main(String[] args)
	{
		ClientConsole.launch();
	}
}
