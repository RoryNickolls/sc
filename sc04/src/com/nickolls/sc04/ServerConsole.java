package com.nickolls.sc04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerConsole extends Application {
	
	private ServerConsoleController consoleController;

	/**
	 * Initialise the server console window
	 */
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerConsole.fxml"));
		Parent root = loader.load();
		consoleController = (ServerConsoleController) loader.getController();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		Server server = new Server(this);
		server.acceptConnections();
	}
	
	public ServerConsoleController getConsoleController()
	{
		return this.consoleController;
	}
	
	public static void main(String[] args)
	{
		ServerConsole.launch();
	}
}
