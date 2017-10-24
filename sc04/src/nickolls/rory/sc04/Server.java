package nickolls.rory.sc04;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
						ServerClient newClient = new ServerClient(clientSocket, new String(nameBytes), clientSocket.getInetAddress().getHostAddress());
						connectedClients.add(newClient);
						serverConsole.getConsoleController().addMessage("Client " + newClient.getClientName() + " connected from " + newClient.getClientAddress());
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
	
	public void listen()
	{
		DataInputStream reader;
		try {
			for(ServerClient client : connectedClients)
			{
				Socket socket = client.getClientSocket();
			
				reader = new DataInputStream(socket.getInputStream());
				byte[] message = new byte[1024];
				reader.read(message);
				System.out.println(client.getClientName() + ":" + new String(message));
			}
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
