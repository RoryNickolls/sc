package sc06;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Main {
	
	public static void main(String[] args)
	{
		String inputFile = "coins";
		ImageIcon img = new ImageIcon(inputFile + ".png");
		try {
			EdgeDetector detector = new EdgeDetector();
			CircleDetector circ = new CircleDetector();
		
			BufferedImage edgeDetect = detector.detectEdges(img, 40);
			ImageIO.write(edgeDetect, "png", new File(inputFile + "_output_edge.png"));
			BufferedImage circDetect = circ.houghTransform(edgeDetect, 3, 30, 20);
		
			ImageIO.write(circDetect, "png", new File(inputFile + "_output_circles.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
