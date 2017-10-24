package nickolls.rory.sc04;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Client {
	
	private String clientName = "John Smith";
	
	private Socket clientSocket;
	
	public Client(String name)
	{
		this.clientName = name;
	}
	
	public String getClientName()
	{
		return this.clientName;
	}
	
	public void joinServer(String host, int port)
	{
		try {
			clientSocket = new Socket(host, port);
			byte[] nameBytes = getClientName().getBytes();
			writeData(nameBytes);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void leaveServer(Server server)
	{
		
	}
	
	private void writeData(byte[] data) throws IOException
	{
		DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
		writer.write(data);
		writer.close();
	}
	
	public static void main(String[] args)
	{
		Random rand = new Random();
		byte[] nameBytes = new byte[8];
		rand.nextBytes(nameBytes);
		String name = new String(nameBytes);
		Client client = new Client(name);
		client.joinServer("localhost", 8888);
	}
}
