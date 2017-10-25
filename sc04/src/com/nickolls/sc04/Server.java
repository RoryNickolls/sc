package com.nickolls.sc04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	public static final int MAX_NAME_LENGTH = 8;
	public static final int MAX_MESSAGE_LENGTH = 1024;
	
	private ArrayList<ServerClient> connectedClients;
	
	private ServerConsole serverConsole;
	
	private ServerSocket serverSocket;
	
	public Server(ServerConsole console)
	{
		this.serverConsole = console;
		
		connectedClients = new ArrayList<ServerClient>();
		
		try {
			serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts a thread that listens for clients trying to open a connection
	 */
	public void acceptConnections()
	{
		Thread connectionThread = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while(true)
				{
					try {
						Socket clientSocket = serverSocket.accept();
						DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
						byte[] nameBytes = new byte[MAX_NAME_LENGTH];
						reader.read(nameBytes);
						ServerClient newClient = new ServerClient(clientSocket, new String(nameBytes), clientSocket.getInetAddress().getHostAddress());
						connectedClients.add(newClient);
						listen(newClient);
						
						String joinMsg = "Client " + newClient.getClientName() + " connected from " + newClient.getClientAddress();
						broadcastMessage(joinMsg, true);
					} 
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		connectionThread.setDaemon(true);
		connectionThread.start();
	}
	
	/**
	 * Starts a thread that listens for messages coming from a specified  client
	 */
	public void listen(ServerClient client)
	{
		Thread listenThread = new Thread(new Runnable() {
			boolean shouldListen = true;
			@Override
			public void run()
			{
				DataInputStream reader;
				try {
					while(shouldListen)
					{
						Socket socket = client.getClientSocket();
					
						reader = new DataInputStream(socket.getInputStream());
						byte[] message = new byte[1024];
						reader.read(message);
						
						String broadcastMsg = client.getClientName() + ": " + new String(message);
						broadcastMessage(broadcastMsg, true);
					}
				} 
				catch(IOException e)
				{
					e.printStackTrace();
					shouldListen = false;
					disconnectClient(client);
				}
			}
		});
		listenThread.setDaemon(true);
		listenThread.start();		
	}
	
	/**
	 * Broadcasts a message to every connected client
	 * @param msg Byte array to broadcast
	 */
	public void broadcastMessage(byte[] msg, boolean includeServer)
	{
		try {
			DataOutputStream writer;
			for(ServerClient client : connectedClients)
			{
				Socket socket = client.getClientSocket();
			
				writer = new DataOutputStream(socket.getOutputStream());
				writer.write(msg);
			}
			
			if(includeServer)
			{
				serverConsole.getConsoleController().addMessage(new String(msg));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void broadcastMessage(String msg, boolean includeServer)
	{
		broadcastMessage(msg.getBytes(), includeServer);
	}
	
	private void disconnectClient(ServerClient client)
	{
		broadcastMessage(client.getClientName() + " has disconnected.", true);
		connectedClients.remove(client);
	}
}
