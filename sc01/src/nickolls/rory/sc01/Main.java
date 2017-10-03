package nickolls.rory.sc01;

public class Main {
	public static void main(String[] args)
	{
		ECSScraper scraper = new ECSScraper();
		String name = scraper.findName("ks14g13@ecs.soton.ac.uk");
		System.out.println(name);
	}
}
