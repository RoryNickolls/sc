package com.nickolls.sc04;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ConsoleController {
	
	@FXML
	TextArea txtArea_console;
	
	@FXML
	public void initialize()
	{
		
	}
	
	public void addMessage(String msg)
	{
		txtArea_console.setText(txtArea_console.getText() + msg + "\n");
	}
}
