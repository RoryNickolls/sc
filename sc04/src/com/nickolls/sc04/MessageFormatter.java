package com.nickolls.sc04;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {
	
	/**
	 * Separates a message into sub-messages and their respective formatting
	 * @param msg Raw message to interpret
	 * @return A List of all messages and formatting in this sentence
	 */
	public static List<MessageFormat> InterpretFormatting(String msg)
	{
		List<MessageFormat> formatting = new ArrayList<MessageFormat>();
		
		// get all the individual words by splitting with spaces 
		String[] words = msg.split("\\s+");
		for(String s : words)
		{
			// replace underscores with spaces+
			s = s.replace("_", " ");
			
			// find the indices for when each section ends
			int firstBeta = s.indexOf('ß'), lastBeta = s.lastIndexOf('ß');
			int firstMew = s.indexOf("µ"), lastMew = s.lastIndexOf("µ");
			
			MessageFormat format;
			
			// if any of these indices were -1 then there is no real section, so create an empty format, otherwise
			// execute the following code picking out the format and message
			if(firstBeta != -1 && firstMew != -1 && lastBeta != -1 && lastMew != -1)
			{
				format = new MessageFormat(
						s.substring(firstBeta + 1, lastBeta),
						s.substring(firstMew + 1, lastMew));
			}
			else
			{
				format = new MessageFormat(s, "");
			}
			
			formatting.add(format);
		}
		
		return formatting;
	}
}


