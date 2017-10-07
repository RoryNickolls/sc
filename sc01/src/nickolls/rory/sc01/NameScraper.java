package nickolls.rory.sc01;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class NameScraper implements Runnable {
	private String email;
	private ScraperCallback callback;
	
	public NameScraper(String email, ScraperCallback callback)
	{
		this.email = email;
		this.callback = callback;
	}
	
	public void run()
	{
		String name = "USER NOT FOUND";
		
		// find the first portion of the email address
		String query = email.split("@")[0];
		
		String url = "http://www.ecs.soton.ac.uk/people/" + query;
		
		// set up a new WebClient
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		
		try
		{
			// retrieve the page
			System.out.println("Attempting connection to " + url);
			HtmlPage page = client.getPage(url);
			System.out.println("Connection successful.");
			
			// use XPath to navigate to the first header on the page with the given class
			HtmlElement firstHeader = page.getFirstByXPath("//h1[@class='uos-page-title uos-main-title uos-page-title-compressed']");
			if(firstHeader != null)
			{
				// activate the callback and set name
				name = firstHeader.asText();
			}
		} 
		catch(Exception e)
		{
			callback.finish("ERROR");
			e.printStackTrace();
		}
		callback.finish(name);
		client.close();
	}
}
