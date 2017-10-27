package com.nickolls.sc04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	private ClientConsole clientConsole;
	private Socket clientSocket;
	
	public Client(ClientConsole console)
	{
		this.clientConsole = console;
	}
	
	public boolean joinServer(String alias, String host, int port)
	{
		try {
			clientSocket = new Socket(host, port);
			byte[] nameBytes = alias.getBytes();
			writeData(nameBytes);
			listen();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void leaveServer(Server server)
	{
		
	}
	
	public void sendMessage(String msg)
	{
		try {
			writeData(msg.getBytes());
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts a thread that listens to messages coming from the server
	 */
	public void listen()
	{
		Thread listenThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try 
				{
					DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
					while(true)
					{
						byte[] message = new byte[1024];
						int readBytes = reader.read(message);
						clientConsole.getConsoleController().addMessage(new String(message, 0, readBytes));
					}
				} 
				catch(IOException e)
				{
					//e.printStackTrace();
					
					// if there is an error here then the client has lost connection to the server
					clientConsole.getConsoleController().addMessage("'-fx-fill:red'#Lost_connection_to_server.#");
					clientConsole.getConsoleController().reset();
				}
			}
		});
		listenThread.setDaemon(true);
		listenThread.start();
	}
	
	private void writeData(byte[] data) throws IOException
	{
		DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
		writer.write(data);
		//writer.close();
	}
}
