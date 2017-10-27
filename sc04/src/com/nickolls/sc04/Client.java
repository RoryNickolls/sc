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
	
	/**
	 * Opens a connection to a server and begins listening for data
	 * @param alias Alias to send to the server on connection
	 * @param host Host address to connect to
	 * @param port Port on which the server is listening
	 * @return Whether the connection was made successfully
	 */
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
	
	/**
	 * Sends a message to the server
	 * @param msg Message to send
	 */
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
	
	/**
	 * Writes raw byte data to the server
	 * @param data Array of bytes to write
	 * @throws IOException
	 */
	private void writeData(byte[] data) throws IOException
	{
		DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
		writer.write(data);
		//writer.close();
	}
}
