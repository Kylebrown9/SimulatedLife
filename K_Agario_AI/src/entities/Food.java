package entities;

public class Food extends Entity{
	public static final float FOODSIZE = 1;
	
	private int spoilTime = 1000;
	
	public Food(float x, float y) {
		super(x,y,FOODSIZE);
	}
	
	public void update() {
		spoilTime--;
		if(spoilTime == 0)
			exists = false;
	}
	
	public void eat() {
		exists = false;
	}
	
	public boolean isFood() {
		return true;
	}
}
