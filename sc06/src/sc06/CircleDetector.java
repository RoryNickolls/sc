package sc06;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CircleDetector {
	
	public BufferedImage houghTransform(BufferedImage image, int minRadius, int maxRadius, int maxCircles)
	{
		List<Circle> circles = new ArrayList<Circle>();
		
		System.out.println("Scanning image.");
		// loop through each column of pixels
		for(int x = 0; x < image.getWidth(); x++)
		{
			// loop through each row of pixels
			for(int y = 0; y < image.getHeight(); y++)
			{
				// loop through each radii
				for(int r = minRadius; r < maxRadius; r++)
				{
					int curVote = 0;
					// loop through a whole circle
					for(int angle = 0; angle < 360; angle++)
					{
						// find all the points on the circle of current radius around current pixel
						int a = (int)(x - r * Math.cos(angle * Math.PI / 180));
						int b = (int)(y - r * Math.sin(angle * Math.PI / 180));

						// ensure that the current point is within the bounds of the image
						if(a >= 0 && b >= 0 && a < image.getWidth() && b < image.getHeight())
						{
							Color curColor = new Color(image.getRGB(a, b));
							if(curColor.getRed() == 255)
							{
								// increase the vote for this circle as it passes through a colored pixel
								curVote++;
							}
						}
					}
					circles.add(new Circle(x, y, r, curVote));
				}
			}
		}
		System.out.println("Scan complete.");
		System.out.println("Sorting circles...");
		// sort the circles list based on their vote (circles with more votes are closer to front of list)
		circles.sort(new Comparator<Circle>() 
		{

			@Override
			public int compare(Circle arg0, Circle arg1) {
				// TODO Auto-generated method stub
				if(arg0.getVoteCount() > arg1.getVoteCount())
				{
					return -1;
				}
				else if(arg0.getVoteCount() < arg1.getVoteCount())
				{
					return 1;
				}
				
				return 0;
			}
			
		});
		System.out.println("Sort complete.");
		
		System.out.println("Colouring circles...");
		
		// create new image to draw circles on
		//BufferedImage circlesImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		
		Color circleColor = Color.red;
		// loop through circles until limit to number found is reached
		for(int i = 0; i < maxCircles; i++)
		{
			Circle curCircle = circles.get(i);
			// loop through all the points on this circle and color them
			for(int angle = 0; angle < 360; angle++)
			{
				int a = (int)(curCircle.getX() - curCircle.getRadius() * Math.cos(angle * Math.PI / 180));
				int b = (int)(curCircle.getY() - curCircle.getRadius() * Math.sin(angle * Math.PI / 180));
				
				if(a >= 0 && b >= 0 && a < image.getWidth() && b < image.getHeight())
					image.setRGB(a, b, circleColor.getRGB());
			}
		}
		
		System.out.println("Complete.");
		return image;
	}

}
