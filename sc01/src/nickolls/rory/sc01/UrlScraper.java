package nickolls.rory.sc01;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class UrlScraper implements Runnable {
	
	private String name;
	private ScraperCallback callback;
	
	public UrlScraper(String name, ScraperCallback callback)
	{
		this.name = name;
		this.callback = callback;
	}
	
	public void run()
	{
		String url = "COULD NOT FIND URL";
		
		String query = "https://www.google.co.uk/search?q=";
		try 
		{
			query += URLEncoder.encode(name, "UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		try 
		{
			System.out.println("Attempting connection to " + query);
			HtmlPage page = client.getPage(query);
			System.out.println("Connection successful");
			
			// find anchor containing first search result
			HtmlAnchor anchor = (HtmlAnchor)page.getFirstByXPath("//div[@id='search']/div[@id='ires']/ol/div[@class='g']/h3[@class='r']/a");
			//System.out.println(page.asXml());
			
			// click anchor to enter page, then take its url
			url = anchor.click().getUrl().toString();
			
			callback.finish(url);
		}
		catch(Exception e)
		{
			callback.finish("ERROR");
			e.printStackTrace();
		}
		
		client.close();	
	}

}
