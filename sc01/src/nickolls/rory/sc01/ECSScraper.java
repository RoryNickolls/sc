package nickolls.rory.sc01;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ECSScraper {

	public String findName(String email)
	{
		String name = "USER NOT FOUND";
		
		String query = email.split("@")[0];
		
		String url = "http://www.ecs.soton.ac.uk/people/" + query;
		
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		
		try
		{
			System.out.println("Attempting connection to " + url);
			HtmlPage page = client.getPage(url);
			System.out.println("Connection successful.");
			
			HtmlElement firstHeader = page.getFirstByXPath("//h1[@class='uos-page-title uos-main-title uos-page-title-compressed']");
			if(firstHeader != null)
			{
				name = firstHeader.asText();
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		client.close();
		
		return name;
		
	}
}
