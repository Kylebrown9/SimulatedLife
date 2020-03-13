package entities;

import java.awt.Graphics;

import main.ArrayPanel;
import main.RenderFrame;

public abstract class Entity {
	private float x,y,r;
	protected boolean exists = true;
	
	public Entity(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public void setX(float newX) {x = newX;}
	public void setY(float newY) {y = newY;}
	public void setR(float newR) {r = newR;}
	
	public float getX() {return x;}
	public float getY() {return y;}
	public float getR() {return r;}

	public void changeX(float changeX) {x += changeX;}
	public void changeY(float changeY) {y += changeY;}
	public void changeR(float changeR) {r += changeR;}
	
	public float thetaTo(Entity other) {
		float deltaY = other.getY()-y;
		float deltaX = other.getX()-x;
		
		if(deltaY == 0 && deltaX == 0)
			System.out.println("err");
		
		return (float)Math.atan2(
				other.getY()-y,
				other.getX()-x
			);
	}
	
	public float wTo(Entity other, float distance) {
		return 2*(float)Math.asin(other.getR()/distance);
	}
	
	public float distTo(Entity other) {
		float deltaY = other.getY()-y;
		float deltaX = other.getX()-x;
		return (float)Math.sqrt(deltaY*deltaY + deltaX*deltaX);
	}
	
	public void drawOn(Graphics g) {
		g.setColor(ArrayPanel.getColor(getColorVal()));
		g.fillOval(
				(int)(x-r+RenderFrame.WORLDM), 
				(int)(y-r+RenderFrame.WORLDM), 
				(int)(2*r), 
				(int)(2*r));
	}
	
	public void drawArcOn(Graphics g, float theta) {
		int degrees = (int)Math.toDegrees(theta);
		g.fillArc(
				(int)(x-1000+RenderFrame.WORLDM), 
				(int)(y-1000+RenderFrame.WORLDM), 
				(int)(2*1000), 
				(int)(2*1000),
				degrees-1,
				1);
	}
	
	public void drawRayOn(Graphics g, float theta) {
		g.drawLine((int)x+RenderFrame.WORLDM,
				   (int)y+RenderFrame.WORLDM,
				   (int)(1000f*(float)Math.cos(theta))+(int)x,
				   (int)(1000f*(float)Math.sin(theta))+(int)y);
	}
	
	public float getColorVal() {
		return r*LearningAgent.COLORSCALE;
	}
	

	public boolean exists() {return exists;}
	public boolean isFood() {return false;}
	
	public String toString() {
		return "x: " + x + " y: " + y + " r: " + r;
	}
	
	public abstract void update();
}