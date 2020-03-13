package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

import entities.LearningAgent;

public class ArrayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	LearningAgent agent;
	float[] array = null;
	
	public void setAgent(LearningAgent a) {
		agent = a;
	}
	
	public void paintComponent(Graphics g) {		
		if(agent == null || !agent.exists())
			return;
		
		array = agent.getInput();
//		System.out.println(Arrays.toString(array));
		Dimension dim = getSize();
		
		int width = dim.width/array.length;
		
		for(int i=0; i<array.length; i++) {
			g.setColor(getColor(array[i]));
			g.fillRect(i*width, 0, (i+1)*width, dim.height);
		}
	}
	
	public static Color getColor(float val) {
		assert val >= 0 && val <= 1 : "color range";
		
		if(val < 0 || val > 1 || Float.isNaN(val)) {
//			System.err.println("getColor() color value incorrect: " + val);
//			System.err.println(Arrays.toString(Thread.currentThread().getStackTrace()));
			return Color.WHITE;
		}
//		assert (val < 0 || val > 1 || Float.isNaN(val)) : "getColor() color value incorrect: " + val;
		
//		return new Color((int)(val*255),(int)(val*255),255);
		return new Color(Color.HSBtoRGB(1-val, 0.5f, 0.5f));
	}
}
