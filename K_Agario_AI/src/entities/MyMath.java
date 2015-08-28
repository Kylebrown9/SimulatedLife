package entities;

public class MyMath {
	static final float PIx2 = (float)Math.PI*2f;
	static final float PI = (float)Math.PI;
	
	public static void main(String [] args) {

		//*************************************************
		
//		System.out.println(angleDelta(Math.toRadians(angdeg),Math.toRadians(angdeg) ));
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(90),
//				(float)Math.toRadians(20),
//				(float)Math.toRadians(110),
//				(float)Math.toRadians(21)));
//		
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(90),
//				(float)Math.toRadians(20),
//				(float)Math.toRadians(110),
//				(float)Math.toRadians(19)));
//		
//		//*************************************************
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(90),
//				(float)Math.toRadians(60),
//				(float)Math.toRadians(60),
//				(float)Math.toRadians(61)));
//		
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(90),
//				(float)Math.toRadians(30),
//				(float)Math.toRadians(60),
//				(float)Math.toRadians(29)));
//		
//
//		//*************************************************
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(70),
//				(float)Math.toRadians(160),
//				(float)Math.toRadians(270),
//				(float)Math.toRadians(161)));
//		
//		
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(70),
//				(float)Math.toRadians(80),
//				(float)Math.toRadians(270),
//				(float)Math.toRadians(81)));
		
//		System.out.println(angleOverlap(
//				(float)Math.toRadians(90),
//				(float)Math.toRadians(20),
//				(float)Math.toRadians(110),
//				(float)Math.toRadians(21)));
	}
	
	public static float angleDelta(float theta1, float theta2) {
		
		while(theta1 < 0)
			theta1 += PIx2;
		
		while(theta2 < 0)
			theta2 += PIx2;
		
		float max = Math.max(theta1, theta2);
		float min = Math.min(theta1, theta2);
		float difference = max - min;
		
		if(difference > PI) {
			return PIx2 - max + min;
		} else {
			return difference;
		}
	}
	
	public static boolean angleOverlap(float theta1, float w1, float theta2, float w2) {
		return angleDelta(theta1, theta2) < (w1 + w2) / 2.0;
	}
}
