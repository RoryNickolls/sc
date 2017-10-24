package nickolls.rory.sc04;

public class ServerClient {
	
	private String clientName;
	private String clientAddress;
	
	public ServerClient(String name, String address)
	{
		this.clientName = name;
		this.clientAddress = address;
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
