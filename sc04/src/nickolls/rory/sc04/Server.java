package nickolls.rory.sc04;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	public static final int MAX_NAME_LENGTH = 8;
	
	private ArrayList<ServerClient> connectedClients;
	
	private ServerSocket serverSocket;
	
	public Server()
	{
		connectedClients = new ArrayList<ServerClient>();
		
		try {
			serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptConnections()
	{
		Thread connectionThread = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while(true)
				{
					try {
						System.out.println("Waiting for next client...");
						Socket clientSocket = serverSocket.accept();
						DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
						byte[] nameBytes = new byte[MAX_NAME_LENGTH];
						reader.read(nameBytes);
						ServerClient newClient = new ServerClient(new String(nameBytes), clientSocket.getInetAddress().getHostAddress());
						connectedClients.add(newClient);
						System.out.println("Client " + newClient.getClientName() + " connected from " + newClient.getClientAddress());
					} 
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		connectionThread.start();
	}
	
	public static void main(String[] args)
	{
		Server server = new Server();
		server.acceptConnections();
	}
}
