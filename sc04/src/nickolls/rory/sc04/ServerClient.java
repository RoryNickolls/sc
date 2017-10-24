package nickolls.rory.sc04;

import java.net.Socket;

public class ServerClient {
	
	private Socket clientSocket;
	private String clientName;
	private String clientAddress;
	
	public ServerClient(Socket socket, String name, String address)
	{
		this.clientSocket = socket;
		this.clientName = name;
		this.clientAddress = address;
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

}
