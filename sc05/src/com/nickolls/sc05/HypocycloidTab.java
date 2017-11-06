package com.nickolls.sc05;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;

public class HypocycloidTab extends TitledPane {
	
	@FXML
	private Slider slider_fixedRadius;
	
	@FXML
	private Slider slider_movingRadius;
	
	@FXML
	private Slider slider_penOffset;
	
	private Hypocycloid trackedHypocycloid;
	
	public HypocycloidTab()
	{
		this.trackedHypocycloid = new Hypocycloid(0, 0, 0);
	}
	
	@FXML
	public void updateHypocycloid()
	{
		this.trackedHypocycloid.setFixedCircleRadius((float)slider_fixedRadius.getValue());
		this.trackedHypocycloid.setMovingCircleRadius((float)slider_movingRadius.getValue());
		this.trackedHypocycloid.setPenOffset((float)slider_penOffset.getValue());
	}
	
	public static HypocycloidTab GetNewTab()
	{
		try {
			return FXMLLoader.load(HypocycloidTab.class.getResource("HypocycloidTab.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
