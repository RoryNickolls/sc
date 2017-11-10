package sc06;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class CircleDetector {
	
	public BufferedImage houghTransform(BufferedImage image, int minRadius, int maxRadius)
	{
		int[][][] acc = new int[image.getWidth()][image.getHeight()][maxRadius - minRadius];
		int[] highestValues = new int[maxRadius - minRadius];
		
		// loop through each column of pixels
		for(int x = 0; x < acc.length; x++)
		{
			// loop through each row of pixels
			for(int y = 0; y < acc[0].length; y++)
			{
				// only act on white pixels
				Color pixelColor = new Color(image.getRGB(x, y));
				if(pixelColor.getRed() == 255)
				{
					// loop through each radii
					for(int r = minRadius; r < maxRadius; r++)
					{
						// loop through a whole circle
						for(int angle = 0; angle < 360; angle++)
						{
							// find all the points on the circle of current radius around current pixel
							int a = (int)(x - r * Math.cos(angle * Math.PI / 180));
							int b = (int)(y - r * Math.sin(angle * Math.PI / 180));
							
							// ensure that the current point is within the bounds of the image
							if(a >= 0 && b >= 0 && a < image.getWidth() && b < image.getHeight())
							{
								// increase the vote for this circle point
								acc[a][b][r - minRadius] += 1;
								if(acc[a][b][r - minRadius] > highestValues[r - minRadius])
								{
									highestValues[r - minRadius] = acc[a][b][r - minRadius];
								}
							}
						}
					}
				}
			}
		}
		
		// loop through all pixels in the image
		for(int a = 0; a < acc.length; a++)
		{
			for(int b = 0; b < acc[0].length; b++)
			{
				// loop through all radii
				for(int r = 0; r < acc[0][0].length; r++)
				{
					// set the color of the pixel based on its proportionality to the highest vote
					float colorVal = (acc[a][b][r] / (float)highestValues[r]) * 255;
					Color curColor = new Color((int)colorVal, (int)colorVal, (int)colorVal);
					image.setRGB(a, b, curColor.getRGB());
				}
			}
		}
		
		return image;
	}

}
