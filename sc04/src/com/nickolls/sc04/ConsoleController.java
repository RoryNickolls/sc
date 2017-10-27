package com.nickolls.sc04;

import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ConsoleController {
	
	@FXML
	TextFlow txtFlow_console;
	
	/**
	 * Adds a message to the text area and handles its formatting
	 * @param msg Message with embedded formatting to add
	 */
	public void addMessage(String msg)
	{
		Platform.runLater(new Runnable() {
			@Override
			public void run()
			{
				List<MessageFormat> formats = MessageFormatter.InterpretFormatting(msg);
				
				if(!formats.isEmpty())
				{
					for(int i = 0; i < formats.size(); i++)
					{
						Text styledMsg = new Text(formats.get(i).getMessage() + " ");
						styledMsg.setStyle(formats.get(i).getFormat());
						txtFlow_console.getChildren().add(styledMsg);
					}
				}
				else
				{
					txtFlow_console.getChildren().add(new Text(msg));
				}
				
				txtFlow_console.getChildren().add(new Text("\n"));
				
			}
		});
	}
}
