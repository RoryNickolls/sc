package com.nickolls.sc04;

/**
 * A structure that represents a #<message># and its '<format>'
 * @author Rory
 *
 */
public class MessageFormat
{
	private String message;
	private String format;
	
	public MessageFormat(String msg, String format)
	{
		this.message = msg;
		this.format = format;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public String getFormat()
	{
		return this.format;
	}
}