package com.nickolls.sc04;

import java.net.Socket;

public class ServerClient {
	
	private Socket clientSocket;
	private String clientName;
	private String clientAddress;
	private String clientColor;
	
	public ServerClient(Socket socket, String name, String address, String color)
	{
		this.clientSocket = socket;
		this.clientName = name;
		this.clientAddress = address;
		this.clientColor = color;
	}
	
	public Socket getClientSocket()
	{
		return this.clientSocket;
	}
	
	public String getClientName()
	{
		return this.clientName;
	}
	
	public String getClientAddress()
	{
		return this.clientAddress;
	}
	
	public String getClientColor()
	{
		return this.clientColor;
	}

}
