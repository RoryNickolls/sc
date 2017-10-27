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
		
		String[] words = msg.split("\\s+");
		for(String s : words)
		{
			s = s.replace("_", " ");
			
			int firstHash = s.indexOf('#'), lastHash = s.lastIndexOf('#');
			int firstApostrophe = s.indexOf("'"), lastApostrophe = s.lastIndexOf("'");
			
			MessageFormat format;
			if(firstHash != -1 && firstApostrophe != -1 && lastHash != -1 && lastApostrophe != -1)
			{
				format = new MessageFormat(
						s.substring(firstHash + 1, lastHash),
						s.substring(firstApostrophe + 1, lastApostrophe));
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


