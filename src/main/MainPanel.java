package main;

import static main.RenderFrame.MAINH;
import static main.RenderFrame.MAINW;
import static main.RenderFrame.WORLDH;
import static main.RenderFrame.WORLDM;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import entities.Entity;
import entities.LearningAgent;
import entities.World;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
//	private World w;
	private LearningAgent selected;
	private List<Entity> entities;
	
	public MainPanel(World w) {
//		this.w = w;
		entities = w.getEntities();
	}
	
	public void paintComponent(Graphics g) {
		assert g != null : "WTF";
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,0,MAINW,MAINH);
		
		for(Entity e : entities) {
			e.drawOn(g);
		}
		
		if(selected != null && selected.exists()) {
			g.setColor(Color.RED);
			float sW = selected.getW()/LearningAgent.IW;	//find the sector width for current
			float left = selected.getT()-selected.getW()/2.0f;
			for(int i=0; i<=LearningAgent.IW; i++) {
//				selected.drawArcOn(g, left+i*sW);
				selected.drawRayOn(g, left+i*sW);
			}
			
			//Redraw selected
			selected.drawOn(g);
		}
		
		//Border
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, MAINW, WORLDM);			//top
		g.fillRect(0, WORLDM, WORLDM, WORLDH);		//left
		g.fillRect(0, MAINH-WORLDM, MAINW, WORLDM);//bottom
		g.fillRect(MAINW-WORLDM, 0, WORLDM, MAINH);	//right
	}
	
	public void setAgent(LearningAgent a) {
		selected = a;
	}
	
}
