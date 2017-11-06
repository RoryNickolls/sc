package com.nickolls.sc05;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SpirographController {
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private VBox vBox_hypocycloids;
	
	private List<Hypocycloid> hypocycloidList;
	
	private Color[] hypocycloidColors;
	
	@FXML
	public void initialize()
	{
		//vBox_hypocycloids.getChildren().add(HypocycloidTab.GetNewTab());
		hypocycloidList = new ArrayList<Hypocycloid>();
		hypocycloidList.add(new Hypocycloid(50, 10, 12));
		hypocycloidList.add(new Hypocycloid(12, 50, 10));
		
		hypocycloidColors = new Color[] { Color.INDIANRED, Color.CORNFLOWERBLUE, Color.CHOCOLATE, Color.DARKOLIVEGREEN, Color.DEEPPINK };
		
		final GraphicsContext context = canvas.getGraphicsContext2D();
		CanvasRenderer canvasRenderer = new CanvasRenderer(context, 1f);
		canvasRenderer.start();	
	}
	
	private void draw(GraphicsContext context, double time)
	{
		double shapeOffsetX = canvas.getWidth() / 2;
		double shapeOffsetY = canvas.getHeight() / 2;
		
		int colorIndex = 0;
		for(Hypocycloid hypocycloid : hypocycloidList)
		{
			context.setFill(hypocycloidColors[colorIndex]);
			context.fillRect(hypocycloid.getPenPosX(time) + shapeOffsetX, hypocycloid.getPenPosY(time) + shapeOffsetY, 1, 1);
			colorIndex = (colorIndex + 1) % hypocycloidColors.length;
		}
	}
	
	private class CanvasRenderer extends Thread
	{
		private GraphicsContext context;
		private double time;
		private float resolution;
		
		public CanvasRenderer(GraphicsContext context, float resolution)
		{
			this.context = context;
			this.resolution = resolution;
			
			this.setDaemon(true);
		}
		
		@Override
		public void run() {
			while(true)
			{
				draw(context, time);
				time += resolution;
				
				try {
					Thread.sleep((long) (( 1 / (float)500) * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
