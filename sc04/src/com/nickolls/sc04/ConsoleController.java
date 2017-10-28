package com.nickolls.sc04;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
				if(msg.startsWith("[img]"))
				{
					
					// this message is an image!!
					String link = msg.substring("[img]".length());
					ImageView imgView = new ImageView();
					try 
					{
						imgView.setImage(new Image(new URL(link).openStream()));
						imgView.resize(256, 256);
						txtFlow_console.getChildren().add(imgView);
					} 
					catch(IOException e)
					{
						e.printStackTrace();
					}
					
					txtFlow_console.getChildren().add(new Text("\n"));
				}
				else
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
			}
		});
	}
}
