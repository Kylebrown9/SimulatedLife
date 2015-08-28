package entities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import main.RenderFrame;

public class World {
	public static final int SPAWNDISTANCE = 100;
	public static final int SPAWNLOCATIONS = 10;
	public static final int STARTINGFOOD = 50;
	public static final int MAXPOP = 5;
	public static final int MINPOP = 5;
	public static final int WALL = -1;
	public static final int NIBBLESIZE = 4;
	
	List<Food> allFood;
	List<Entity> allThings;
	List<LearningAgent> newLAgents;
	int popCounter = 0;
	LearningAgent best = null;
	
	public World() {
		allFood = new LinkedList<Food>();
		allThings = new LinkedList<Entity>();
		newLAgents = new LinkedList<LearningAgent>();

		for(int i=0; i<MAXPOP; i++) {
			spawnAgent();
		}
		
		for(int i = 0; i < STARTINGFOOD; i++) {
			spawnFood();
		}
		
		allThings.addAll(newLAgents);
		allThings.addAll(allFood);
		newLAgents = new LinkedList<LearningAgent>();
	}
	
	public void update() {
		Entity test;
		
		LearningAgent current;
		float currentT, currentW;
		
		Entity other;
		float otherT, otherW;

		float dist;
		float sW, sT, left;
		
		float[] pixel;
		float[] depth = new float[LearningAgent.SEGMENTS];
		
		best = null;
		int maxCount = 0;
		
		popCounter = 0;
		//O(n)
		for(int i=0; i<allThings.size(); i++) {
			test = allThings.get(i);
			
			if(test.isFood())
				continue;
			
			popCounter++;
			current = (LearningAgent) test;	//Set current
			currentT = current.getT();	//find the theta value for current
			currentW = current.getW();	//find the width value for current
			
			pixel = new float[LearningAgent.IW];	//create new pixel and depth arrays
			
			sW = currentW/LearningAgent.SEGMENTS;	//find the sector width for current
			left = currentT-(currentW/2.0f);//find the angle of the left vision edge
			
			//O(k)
			for(int a=0; a<LearningAgent.SEGMENTS; a++) {
				pixel[a] = WALL;
				depth[a] = Float.MAX_VALUE;	//set all depth values to max
			}

//			System.out.println("First Print: " + Arrays.toString(pixel));
			
			//O(n-1+b)
			for(int j=0; j<allThings.size(); j++) {
				if(i == j)	//continue if current is being compared to itself
					continue;
				
				other = allThings.get(j);
				
				dist = other.distTo(current);		//calculate the distance from current to other
				otherT = current.thetaTo(other);	//find the angle to other from current
				otherW = current.wTo(other,dist);	//find the width of the sector to other from current
				
				//**************Edibility***********************************
				if(dist < (other.getR() + current.getR())) {
					if(current.getR() > other.getR()) {
						current.grow(NIBBLESIZE);
					} else {
						current.grow(-NIBBLESIZE);
					}
					
					if(other.isFood()) {
						Food f = (Food) other;
						f.eat();
					}
				}
				
				//*************Visibility***********************************
				if(MyMath.angleOverlap(currentT, currentW, otherT, otherW)) {
					//O(k)
					for(int k=0; k<LearningAgent.SEGMENTS; k++) {
						sT = left + 0.5f*sW + k*sW;	//calculate the theta for the k sector
						if(MyMath.angleOverlap(sT, sW, otherT, otherW) && dist < depth[k]) {	//check to see if the sector overlaps with the object
																								//and is infront of the the closest thing seen by this sector
							pixel[k] = other.getColorVal();	//set this sectors sensory neuron to the color of other
//							System.out.println("Second print: " + Arrays.toString(pixel));
							depth[k] = dist;	//set the depth to the dist to other
						}
					}
				}
			}
			current.setInputs(pixel);	//update current with the calculated inputs
//			System.out.println(current.exists);
			if(current.getCount() > maxCount) {
				best = current;
			}
		}
		
		ListIterator<Entity> allIt = allThings.listIterator();
		Entity thing;
		while(allIt.hasNext()) {
			thing = allIt.next();
			if(!thing.exists()) 
				allIt.remove();
			else
				thing.update();
		}
		
		maybeSpawnFood();
		int count = 0;
		while(getPopulation() + count < MINPOP) {
			spawnAgent();
			count++;
		}
		allThings.addAll(newLAgents);
		newLAgents = new LinkedList<LearningAgent>();	
	}
	
	public void spawnAgent() {
		boolean flag = true;
		while(flag) {
			float randomX = (float) (Math.random() * (RenderFrame.WORLDW-2*LearningAgent.STARTING_R)) + LearningAgent.STARTING_R;
			float randomY = (float) (Math.random() * (RenderFrame.WORLDH-2*LearningAgent.STARTING_R)) + LearningAgent.STARTING_R;
			
			if(checkOverlap(randomX, randomY, LearningAgent.STARTING_R)) {
				LearningAgent newAgent;
				
				if(best == null)
					newAgent = new LearningAgent(randomX, randomY);
				else 
					newAgent = best.clone(randomX, randomY);
				
				newLAgents.add(newAgent);
				flag = false;
			}
		}
	}
	
	public void maybeSpawnFood() {
		if(Math.random() < 0.10)
			spawnFood();
	}
	
	public void spawnFood() {
		float randomX = (float) (Math.random() * (RenderFrame.WORLDW-2*LearningAgent.STARTING_R)) + LearningAgent.STARTING_R;
		float randomY = (float) (Math.random() * (RenderFrame.WORLDH-2*LearningAgent.STARTING_R)) + LearningAgent.STARTING_R;
		Food newFood = new Food(randomX, randomY);
		allThings.add(newFood);
	}
	
	public boolean checkOverlap(float x, float y, float r) {
		Entity test;
		LearningAgent current;
		float deltax, deltay, dist;
		
		for(int i = 0; i < allThings.size(); i++) {
			test = allThings.get(i);
			
			if(test.isFood())
				continue;
			
			current = (LearningAgent) test;
			
			deltax = x - current.getX();
			deltay = y - current.getY();
			dist = (deltax*deltax + deltay*deltay);
			
			if(dist < (r + current.getR())*(r + current.getR())) {
				return false;
			}
		}
		return true;
	}
	
	public int getPopulation() {
		return popCounter;
	}
	
	public boolean hasRoom() {
		return getPopulation() < MAXPOP;
	}
	
	public List<Entity> getEntities() {
		return allThings;
	}
	
//	public List<LearningAgent> getLAgents() {
//		return lAgents;
//	}
}
