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
		if(msg.contains("[img]"))
		{
			
			// this message is an image!!
			int imgIndex = msg.indexOf("[img]");
			String link = msg.substring(imgIndex + "[img]".length());
			
			ImageView imgView = new ImageView();
			try 
			{
				// open url to download image
				Image image = new Image(new URL(link).openStream());
				imgView.setImage(image);
				
				String remainingMessage = msg.substring(0, imgIndex);
				addMessage(remainingMessage);	
			} 
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			// this runnable must be at the end of this method so recursion works and messages appear in the correct order
			Platform.runLater(new Runnable() {
				@Override
				public void run()
				{
					txtFlow_console.getChildren().add(imgView);
					txtFlow_console.getChildren().add(new Text("\n"));
				}
			});
			
		}
		else
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
}
