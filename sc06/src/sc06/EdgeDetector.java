package sc06;

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
	
	public BufferedImage parseImage(ImageIcon iconImage)
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
	
	public static void main(String[] args)
	{
		ImageIcon img = new ImageIcon("icon.png");
		
		EdgeDetector detector = new EdgeDetector();
		BufferedImage greyscale = detector.parseImage(img);
		File output = new File("output.png");
		try {
			ImageIO.write(greyscale, "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
