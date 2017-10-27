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
			serverConsole.getConsoleController().addMessage("'-fx-font-style:italic'#Server_initialised.#");
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
						byte[] nameBytes = new byte[512];
						reader.read(nameBytes);
						
						Random rand = new Random();
						String color = "rgb(" + rand.nextInt(255) + "," + rand.nextInt(255) + "," + rand.nextInt(255) + ")";
						ServerClient newClient = new ServerClient(clientSocket, new String(nameBytes, 0, MAX_NAME_LENGTH), clientSocket.getInetAddress().getHostAddress(), color);
						connectedClients.add(newClient);
						listen(newClient);
						
						String joinMsg = "'-fx-font-style:italic'#Client_" + newClient.getClientName() + "_connected_from_" + newClient.getClientAddress() + "#";
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
							String broadcastMsg = "'-fx-font-weight:bold;-fx-fill:" + client.getClientColor() + "'#" + client.getClientName() + "#: " + new String(message, 0, readBytes);
							broadcastMessageAll(broadcastMsg);
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
			for(ServerClient client : connectedClients)
			{
				Socket socket = client.getClientSocket();
				
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
	
	public void broadcastMessageAll(String msg)
	{
		broadcastMessage(msg.getBytes(), true, null);
	}
	
	public void broadcastMessageExcluding(String msg, boolean server, List<ServerClient> excludedClients)
	{
		broadcastMessage(msg.getBytes(), server, excludedClients);
	}
	
	private void disconnectClient(ServerClient client)
	{
		connectedClients.remove(client);
		broadcastMessageAll("'-fx-font-style:italic'#" + client.getClientName() + "_has_disconnected.#");
	}
}
