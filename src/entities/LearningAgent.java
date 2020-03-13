package entities;

import static main.RenderFrame.WORLDH;
import static main.RenderFrame.WORLDW;

import java.awt.Graphics;

public class LearningAgent extends Entity {
	public static final float STARTING_R = 15;
	
	public static final int IW = 41;
	public static final int[] HLW = {20,4};
	public static final int OW = 2;
	
	public static final int SEGMENTS = IW-1;
	
	public static final float MAXROT = 1f;
	public static final float MAXFOR = 10f;
	
	public static final float MAXR = 35f;
	public static final float MINR = 10f;
	public static final float SPLITR = (float)Math.sqrt(MAXR*MAXR/2.0);
	public static final float COLORSCALE = 1.0f/(MAXR-MINR);
	public static final float SHRINKRATE = -1.0f;

	private NeuralNetwork net;
	
	private float theta;
	private float w = (float)Math.toRadians(100);
	
	private float[] input;
	private float[] output;
	private float turn = 0;
	private float mov = 0;
	
	private float xMov;
	private float yMov;
//	private boolean collisionFlag = false;
	
	private int age = 0;
//	private World world;
	
	public LearningAgent(float initX, float initY) {
		super(initX, initY, STARTING_R);
		
		net = new NeuralNetwork(IW,HLW,OW);
		net.setWeight();
		
		theta = (float)Math.random()*(float)Math.PI*2;
	}
	
	public LearningAgent(float initX, float initY, NeuralNetwork n) {
		super(initX, initY, STARTING_R);
		
		net = n;
		net.mutate();
		
		theta = (float)Math.random()*(float)Math.PI*2;
	}
	
	public void setInputs(float[] input) {
		this.input = input;
		this.input[this.input.length-1] = getR()*COLORSCALE;
		output = net.evaluate(this.input);
		
//		mov  = (output[0]+1)/2;

		mov  = ((output[0]+1)/2)*f(getR());
		turn = output[1];
		
		theta += (MAXROT*turn);
		while(theta > MyMath.PIx2) {
			theta -= MyMath.PIx2;
		}
		while(theta < 0) {
			theta += MyMath.PIx2;
		}
		
		xMov = (MAXFOR*mov) * (float)Math.cos(theta);
		yMov = (MAXFOR*mov) * (float)Math.sin(theta);
	}
	
	public void grow(float amount) {
		if(amount >= 0) {
			float newR = (float)Math.sqrt(amount*amount + getR()*getR());
			if(newR <= MAXR) {
				setR(newR);
			}
		} else {
			float newR = (float)Math.sqrt(getR()*getR() - (amount*amount));
			if (newR < MINR) {
				setR(MINR);
				exists = false;
			} else {
				setR(newR);
			}
		}
	}
	
	public void update() {
		grow(SHRINKRATE);
		age++;
		
//		if(collisionFlag)
//			return;
		
		if(getR() < getX()+xMov && getR() < Math.abs(getX()+xMov-WORLDW))
			changeX(xMov);
		
		if(getR() < getY()+yMov && getR() < Math.abs(getY()+yMov-WORLDH))
			changeY(yMov);
	}
	
	public void drawOn(Graphics g) {
		super.drawOn(g);
//		g.setColor(Color.GREEN);
//		g.fillArc(
//				(int)(getX()-getR()+RenderFrame.WORLDM), 
//				(int)(getY()-getR()+RenderFrame.WORLDM), 
//				(int)(2*getR()), 
//				(int)(2*getR()),
//				(int)Math.toDegrees(theta)-5,
//				10);
	}

	
	public LearningAgent clone(float newX, float newY) {
		return new LearningAgent(newX,newY,net.clone());
	}
	
	public float[] getInput() {return input;}
	public float getW() {return w;}
	public float getT() {return theta;}
//	public void setCollision(boolean val) {collisionFlag = val;}
	public int getCount() {return age;}
	
	public static float f(float r) {
		return -0.5f*(r-MINR)/(MAXR-MINR) + 1;
	}
}
