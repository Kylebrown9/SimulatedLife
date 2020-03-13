package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import entities.Entity;
import entities.LearningAgent;
import entities.World;

public class RenderFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	public static final int WORLDH = 400;
	public static final int WORLDW = 700;
	public static final int WORLDM = WORLDH/5;
	
	public static final int MAINH = 2*WORLDM + WORLDH;
	public static final int MAINW = 2*WORLDM + WORLDW;
	
	public static final int ARRAYH = MAINH/4;
	
	MainPanel m;
	ArrayPanel a;
	LearningAgent thing;
	boolean painting = false;
	List<Entity> entities;
	
	public static void main(String[] args) {
		World w = new World();
		RenderFrame rF = new RenderFrame(w);
		rF.setVisible(true);
		rF.addKeyListener(rF);
		
		rF.entities = w.getEntities();
		
		for(int i=0; i<1; ) {
			w.update();
//			System.out.println(w.getEntities());
			
			if(rF.painting) {
				rF.repaint();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public RenderFrame(World w) {
		m = new MainPanel(w);
		a = new ArrayPanel();
		
		Container contentPane = getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		layout.putConstraint(SpringLayout.NORTH, m, 0, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, m, 0, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, m, 0, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, m, MAINH, SpringLayout.NORTH, contentPane);
		
		layout.putConstraint(SpringLayout.NORTH, a, 0, SpringLayout.SOUTH, m);
		layout.putConstraint(SpringLayout.EAST, a, 0, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, a, 0, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, a, ARRAYH, SpringLayout.SOUTH, m);
		
//		layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, a);
//		layout.putConstraint(SpringLayout.EAST, contentPane, MAINW, SpringLayout.WEST, contentPane);
		
		contentPane.add(m);
		contentPane.add(a);
		
		this.setSize(MAINW, MAINH+ARRAYH);
		this.setMinimumSize(new Dimension(MAINW, MAINH+ARRAYH));
	}
	
	public void paint(Graphics g) {
		if(thing != null && thing.exists()) 
			thing = null;
		
		super.paint(g);
	}
	
	public void selectRandom() {
		Entity test = null;
		int count=0;
		do {
			test = entities.get((int)(Math.random()*entities.size()));
			
			count++;
			if(count > 1000)
				return;
		} while(test.isFood());
		
		thing = (LearningAgent) test;
		a.setAgent(thing);
		m.setAgent(thing);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			selectRandom();
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			painting = !painting;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
