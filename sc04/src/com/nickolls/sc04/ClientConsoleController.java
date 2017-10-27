package com.nickolls.sc04;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientConsoleController extends ConsoleController {
	
	// References to the GUI controls
	
	@FXML
	private TextField txtFld_alias;
	
	@FXML
	private TextField txtFld_address;
	
	@FXML
	private TextField txtFld_port;
	
	@FXML
	private TextField txtFld_message;
	
	@FXML
	private Button btn_send;
	
	@FXML
	private Button btn_joinServer;
	
	private Client client;
	
	@FXML
	public void initialize()
	{
		txtFld_message.setDisable(true);
		txtFlow_console.setDisable(true);
		btn_send.setDisable(true);
	}
	
	/**
	 * Runs when the send message button is pressed
	 */
	@FXML
	public void handleSendMessage()
	{
		// send a message!
		client.sendMessage(txtFld_message.getText());
		txtFld_message.setText("");
	}
	
	/**
	 * Runs when the join server button is pressed. Attempts to connect to the specified server.
	 */
	@FXML
	public void handleJoinServer()
	{
		txtFlow_console.getChildren().clear();
		if(client.joinServer(txtFld_alias.getText(), txtFld_address.getText(), Integer.parseInt(txtFld_port.getText())))
		{
			txtFld_message.setDisable(false);
			txtFlow_console.setDisable(false);
			btn_send.setDisable(false);
			txtFld_alias.setDisable(true);
			txtFld_address.setDisable(true);
			txtFld_port.setDisable(true);
			btn_joinServer.setDisable(true);
			addMessage("µ-fx-fill:greenµßConnection_successful.ß");
		}
		else
		{
			addMessage("µ-fx-fill:redµßCould_not_connect.ß");
		}
	}
	
	/**
	 * Sets the client that this console is handling
	 * @param client
	 */
	public void setClient(Client client)
	{
		this.client = client;
	}
	
	/**
	 * Resets the enabled states of all fields to default. Used when connection is lost to a server.
	 */
	public void reset()
	{
		txtFld_alias.setDisable(false);
		txtFld_address.setDisable(false);
		txtFld_port.setDisable(false);
		btn_joinServer.setDisable(false);
		
		txtFld_message.setDisable(true);
		txtFlow_console.setDisable(true);
		btn_send.setDisable(true);
	}

}
