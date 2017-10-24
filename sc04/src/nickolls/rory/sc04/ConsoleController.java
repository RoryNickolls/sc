package nickolls.rory.sc04;

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
		String text = txtArea_console.getText() + "\n";
		text += msg;
		txtArea_console.setText(text);
	}
}
