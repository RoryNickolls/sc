package com.nickolls.sc04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
	
	// maximum number of characters an alias can have
	public static final int MAX_NAME_LENGTH = 8;
	// maximum length of a message
	public static final int MAX_MESSAGE_LENGTH = 1024;
	
	
	private ArrayList<ServerClient> connectedClients;
	
	private ServerConsole serverConsole;
	
	private ServerSocket serverSocket;
	
	/**
	 * Initalise and open server socket
	 * @param console
	 */
	public Server(ServerConsole console)
	{
		this.serverConsole = console;
		
		connectedClients = new ArrayList<ServerClient>();
		
		try {
			serverSocket = new ServerSocket(8888);
			serverConsole.getConsoleController().addMessage("µ-fx-font-style:italicµßServer_initialised.ß");
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
						
						// attempt to open a connection
						Socket clientSocket = serverSocket.accept();
						DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
						
						// the client will send their alias first, so receive that
						byte[] nameBytes = new byte[512];
						int numChars = reader.read(nameBytes);
						if(numChars > MAX_NAME_LENGTH) numChars = MAX_NAME_LENGTH;
						
						//assign a random color for this new client
						Random rand = new Random();
						String color = "rgb(" + rand.nextInt(255) + "," + rand.nextInt(255) + "," + rand.nextInt(255) + ")";
						
						// create a client object and begin listening for data
						ServerClient newClient = new ServerClient(clientSocket, new String(nameBytes, 0, numChars), clientSocket.getInetAddress().getHostAddress(), color);
						connectedClients.add(newClient);
						listen(newClient);
						
						// broadcast a join message to all clients except the new one
						String joinMsg = "µ-fx-font-style:italicµßClient_" + newClient.getClientName() + "_connected_from_" + newClient.getClientAddress() + "ß";
						List<ServerClient> exclusion = new ArrayList<ServerClient>();
						exclusion.add(newClient);
						broadcastMessageExcluding(joinMsg, true, exclusion);
					} 
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		// ensure that JVM will entirely exit regardless of this thread's state
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
						int readBytes = reader.read(message);
						if(readBytes != -1)
						{
							String messageStr = new String(message, 0, readBytes);
							
							// this is a command!
							if(messageStr.charAt(0) == '/')
							{
								if(messageStr.startsWith("/msg"))
								{
									String recipientName = "";
									try {
										recipientName = messageStr.split(" ")[1];
										messageStr = messageStr.substring("/msg ".length() + recipientName.length());
									} 
									catch(ArrayIndexOutOfBoundsException e)
									{
										System.out.println("Unspecified user");
									}
									System.out.println(recipientName);
									
									ServerClient recipient = findClient(recipientName);
									// check whether the intended recipient exists
									if(recipient != null)
									{
										String broadcastMsg = "µ-fx-font-weight:bold;-fx-fill:magentaµß" 
															+ client.getClientName() + "-->" 
															+ recipient.getClientName() 
															+ "ß" 
															+ messageStr;
										broadcastMessageSpecific(broadcastMsg, client);
										broadcastMessageSpecific(broadcastMsg, recipient);
									}
									else
									{
										String broadcastMsg = "µ-fx-fill:redµßCould_not_find_client_with_that_name.ß";
										broadcastMessageSpecific(broadcastMsg, client);
									}
								}
							}
							else
							{
								// broadcast the message to all clients and the server, with the client's name prefixed
								String broadcastMsg = "µ-fx-font-weight:bold;-fx-fill:" + client.getClientColor() + "µß" + client.getClientName() + "ß: " + new String(message, 0, readBytes);
								broadcastMessageAll(broadcastMsg);
							}
						}
					}
				} 
				catch(IOException e)
				{
					// socket has closed on the other side, client must have disconnected
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
	public synchronized void broadcastMessage(byte[] msg, boolean includeServer, List<ServerClient> excludedClients)
	{
		try {
			DataOutputStream writer;
			
			// go through each connected client
			for(ServerClient client : connectedClients)
			{
				Socket socket = client.getClientSocket();
				
				// if the client is not excluded then write data to it
				if(socket.isConnected() && (excludedClients == null || !excludedClients.contains(client)))
				{
					writer = new DataOutputStream(socket.getOutputStream());
					writer.write(msg);
				}
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
	
	/**
	 * Broadcasts a message to all connected clients and the server console
	 * @param msg
	 */
	public void broadcastMessageAll(String msg)
	{
		broadcastMessage(msg.getBytes(), true, null);
	}
	
	/**
	 * Broadcasts a message to specified clients
	 * @param msg
	 * @param server Whether the server should receive this message
	 * @param excludedClients Clients to exclude from this message
	 */
	public void broadcastMessageExcluding(String msg, boolean server, List<ServerClient> excludedClients)
	{
		broadcastMessage(msg.getBytes(), server, excludedClients);
	}
	
	public void broadcastMessageSpecific(String msg, ServerClient client)
	{
		List<ServerClient> excluded = new ArrayList<ServerClient>();
		for(ServerClient sc : connectedClients)
		{
			if(sc != client)
			{
				excluded.add(sc);
			}
		}
		broadcastMessage(msg.getBytes(), false, excluded);
	}
	
	/**
	 * Disconnects a particular client from the server
	 * @param client
	 */
	private void disconnectClient(ServerClient client)
	{
		connectedClients.remove(client);
		broadcastMessageAll("µ-fx-font-style:italicµß" + client.getClientName() + "_has_disconnected.ß");
		
		try {
			if(!client.getClientSocket().isClosed())
			{
				client.getClientSocket().close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds a client by name
	 * @param alias Name of the client to find
	 * @return Null if the client is not connected
	 */
	private ServerClient findClient(String alias)
	{
		for(ServerClient client : connectedClients)
		{
			if(client.getClientName().equals(alias))
			{
				return client;
			}
		}
		
		return null;
	}
}
