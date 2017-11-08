package sc06;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EdgeDetector {
	
	public EdgeDetector()
	{
		
	}
	
	private BufferedImage convertToGreyscale(ImageIcon iconImage)
	{
		// get local graphics configuration
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice dev = env.getDefaultScreenDevice();
		GraphicsConfiguration conf = dev.getDefaultConfiguration();
		
		// create an empty image that uses the local configuration
		BufferedImage image = conf.createCompatibleImage(iconImage.getIconWidth(), iconImage.getIconHeight());
		// paint the icon into the graphics of the empty image
		iconImage.paintIcon(null, image.createGraphics(), 0, 0);
		// create a greyscale type image to convert
		BufferedImage grey = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		// create a convert operation from the icon's colorspace to the greyscale image's colorspace
		ColorConvertOp convert = new ColorConvertOp(image.getColorModel().getColorSpace(), grey.getColorModel().getColorSpace(), null);
		// filter the icon into the greyscale image
		convert.filter(image, grey);
		
		return grey;
	}
	
	public BufferedImage parseImage(ImageIcon icon)
	{
		BufferedImage greyscale = convertToGreyscale(icon);
		
		final int[][] xKernel = new int[][] { 
			{ 1, 0, -1 },
			{ 2, 0, -2 },
			{ 1, 0, -1 } 
		};
		
		final int[][] yKernel = new int[][] { 
			{ 1, 0, -1 },
			{ 2, 0, -2 },
			{ 1, 0, -1 } 
		};
		
		float[][] pixelValues = new float[greyscale.getWidth() - xKernel[0].length][greyscale.getHeight() - yKernel.length];
		float highestValue = 0;
		for(int x = 0; x < pixelValues.length; x++)
		{
			for(int y = 0; y < pixelValues[0].length; y++)
			{
				int[][] pixelData = new int[3][3];
				for(int i = x; i < x + pixelData.length; i++)
				{
					for(int j = y; j < y + pixelData[0].length; j++)
					{
						Color pixelColor = new Color(greyscale.getRGB(i, j));
						// all components of the color should be the same as it is greyscale, so just get red for brightness
						pixelData[i - x][j - y] = pixelColor.getRed();
					}
				}
				
				double gx = MatrixUtil.convolve(xKernel, pixelData);
				double gy = MatrixUtil.convolve(yKernel, pixelData);
				double g = Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2));
				if(g > highestValue)
				{
					// keep track of the highest value for g
					highestValue = (float)g;
				}
				pixelValues[x][y] = (float)g;
			}
		}
		
		// now loop through and create an image with the g values as redness
		BufferedImage edgeImage = new BufferedImage(pixelValues.length, pixelValues[0].length, BufferedImage.TYPE_3BYTE_BGR);
		for(int x = 0; x < pixelValues.length; x++)
		{
			for(int y = 0; y < pixelValues[0].length; y++)
			{
				// create the value of red in the color proportionally of the value of g
				int red = (int)((pixelValues[x][y] / highestValue) * 255);
				Color color = new Color(red, 0, 0);
				edgeImage.setRGB(x, y, color.getRGB());
			}
		}
		
		return edgeImage;
	}
	
	public static void main(String[] args)
	{
		ImageIcon img = new ImageIcon("crowd.png");
		
		EdgeDetector detector = new EdgeDetector();
		BufferedImage outputImg = detector.parseImage(img);
		File output = new File("output.png");
		try {
			ImageIO.write(outputImg, "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
