package entities;

public class MatrixMath {
	
	public static final float CONST = (float)Math.log(2);
	
	public static void main(String[] args) {
		float[][] m1 = {{1},{1}};
		float[] m2 = {1,1};
		
		printMatrix(multiply(m2,m1));
	}
	
	public static float[][] multiply(float[][] m1, float[][] m2) {
		final int m1ColLength = m1[0].length; // m1 columns length
		final int m2RowLength = m2.length; // m2 rows length

		if (m1ColLength != m2RowLength) {
			System.out.println("err");
			System.out.println("a: " + m1.length + " " + m1ColLength);
			System.out.println("b: " + m2RowLength + " " + m2[0].length);
			return null;
		}
			 // matrix multiplication is not possible
		final int mRRowLength = m1.length; // m result rows length
		final int mRColLength = m2[0].length; // m result columns length
		float[][] mResult = new float[mRRowLength][mRColLength];
		for (int i = 0; i < mRRowLength; i++) { // rows from m1
			for (int j = 0; j < mRColLength; j++) { // columns from m2
				for (int k = 0; k < m1ColLength; k++) { // columns from m1
					mResult[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return mResult;
	}

	public static float[][] multiply(float[] m1a, float[][] m2) {
		float[][] m1b = new float[1][m1a.length];
		for(int i=0; i<m1a.length; i++) {
			m1b[0][i] = m1a[i];
		}
		
		float[][] temp = multiply(m1b, m2);
		return temp;
	}
	
	public static float[] collapse(float[][] m) {
		if(m.length == 1 && m[0].length == 1) {
			float[] out = {m[0][0]};
			return out;
		} else if (m.length == 1) {
			float[] out = new float[m[0].length];
			for(int i=0; i<out.length; i++) {
				out[i] = m[0][i];
			}
			return out;
		} else if (m[0].length == 1) {
			float[] out = new float[m.length];
			for(int i=0; i<out.length; i++) {
				out[i] = m[i][0];
			}
			return out;
		} else {
			System.err.println("Failed to collapse array");
			return null;
		}
	}
	
	public static float[][] abs(float[][] m) {
		float[][] copy = new float[m.length][m[0].length];
		
		for(int i=0; i < m.length; i++) {
			for(int j=0; j < m[0].length; j++) {
				copy[i][j] = Math.abs(m[i][j]);
			}
		}
		
		return copy;
	}
	
	public static float[][] transpose(float[] input) {

		float[][] transformedInput = new float[input.length][1];

		// If the input had a length of 4 elements, you would get a matrix with
		// 4 rows and 1 column
		for (int i = 0; i < input.length; i++) {
			transformedInput[i][0] = input[i];
		}
		return transformedInput;
	}
	
	public static float[][] transpose(float[][] input) {

		float[][] transformedInput = new float[input[0].length][input.length];

		for (int i = 0; i < transformedInput.length; i++) {
			for(int j = 0; j < transformedInput[i].length; j++) {
				transformedInput[i][j] = input[j][i];
			}
		}
		return transformedInput;
	}
	
	public static boolean matrixEQ(int[][] m1, int[][] m2) {
		if (m1.length != m2.length || m1[0].length != m2[0].length) {
			return false;
		}

		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m1[0].length; j++) {
				if (m1[i][j] != m2[i][j])
					return false;
			}
		}

		return true;
	}
	
	public static void printMatrix(float[][] m) {
		for(float[] d1 : m) {
			for(float d2 : d1)
				System.out.print(d2 + "\t");
			
			System.out.println();
		}
			
	}

}
