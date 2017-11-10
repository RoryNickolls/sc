package sc06;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class CircleDetector {
	
	public BufferedImage houghTransform(BufferedImage image, int minRadius, int maxRadius)
	{
		int[][][] acc = new int[image.getWidth()][image.getHeight()][maxRadius - minRadius];
		int[] highestValues = new int[maxRadius - minRadius];
		for(int x = 0; x < acc.length; x++)
		{
			for(int y = 0; y < acc[0].length; y++)
			{
				Color pixelColor = new Color(image.getRGB(x, y));
				if(pixelColor.getRed() == 255)
				{
					for(int r = minRadius; r < maxRadius; r++)
					{
						for(int angle = 0; angle < 360; angle++)
						{
							int a = (int)(x - r * Math.cos(angle * Math.PI / 180));
							int b = (int)(y - r * Math.sin(angle * Math.PI / 180));
							
							if(a >= 0 && b >= 0 && a < image.getWidth() && b < image.getHeight())
							{
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
		
		for(int a = 0; a < acc.length; a++)
		{
			for(int b = 0; b < acc[0].length; b++)
			{
				for(int r = 0; r < acc[0][0].length; r++)
				{
					float colorVal = (acc[a][b][r] / (float)highestValues[r]) * 255;
					Color curColor = new Color((int)colorVal, (int)colorVal, (int)colorVal);
					image.setRGB(a, b, curColor.getRGB());
				}
			}
		}
		
		return image;
	}

}
