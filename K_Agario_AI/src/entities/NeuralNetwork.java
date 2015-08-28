package entities;




public class NeuralNetwork {
	float[][][] weights;
	float[][] nodes;
	private int fitness=0;
	int iW, oW;
	int[] hlW;
	
	public NeuralNetwork(int iW, int[] hlW, int oW) {
		this.iW = iW;
		this.hlW = hlW;
		this.oW = oW;
		
        //*******************************************************
        if(hlW == null) {
        	weights = new float[1][][];
        	nodes = new float[2][];
        	weights[0] = new float[iW][oW];
        } else {
        	weights = new float[hlW.length+1][][];
            nodes = new float[hlW.length+2][];
        	
	        //First
	        weights[0] = new float[iW][hlW[0]];
	        
	        //Middle
	        for(int i=1; i<hlW.length; i++) {
	               weights[i] = new float[hlW[i-1]][hlW[i]];
	        }

	        weights[weights.length-1] = new float[hlW[hlW.length-1]][oW];
        }
	}
	
	public float[] evaluate(float[] input) {
//		System.out.println(Arrays.toString(input));
		assert iW == input.length : "Invalid Input width";
		nodes[0] = input;
		
		for (int i = 1; i <= weights.length; i++) {
			float[][] temp = MatrixMath.multiply(nodes[i-1], weights[i-1]);
			nodes[i] = activationForLayer(temp);
		}

		return nodes[nodes.length-1];
	}	

	public float[] activationForLayer(float[][] m) {
		float[] output = new float[m[0].length];

		for (int i = 0; i < m[0].length; i++) {
			output[i] = sigmoid(m[0][i]);
		}
		return output;
	}
	
	public float sigmoid(float val) {
		float out = (float) ((Math.exp(val)-Math.exp(-val)) / (Math.exp(val)+Math.exp(-val)));
//		float out = 1.0 / (1.0 + Math.exp(-(val)));
		if(Float.isNaN(out)) {
			System.out.println(this.toString());
			return 0;
		}
		
		return out;
	}
	
	public void setWeight() {
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights[i].length; j++) {
				for(int k = 0; k < weights[i][j].length; k++) {
					weights[i][j][k] = (float)Math.random() * 2 - 1;
				}
			}
		}
	}
	
	public void mutate() {
		float[][] temp1 = weights[(int) (Math.random() * weights.length)];
		float[] temp2 = temp1[(int) (Math.random() * temp1.length)];
		temp2[(int) (Math.random() * temp2.length)] += (Math.random()/2.0 - 0.25);
	}
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		for(int i = 0; i < weights.length; i++) {
			out.append("Weight ");
			out.append((int)(i + 1));
			out.append("\n");
			for(int j = 0; j < weights[i].length; j++) {
				for(int k = 0; k < weights[i][j].length; k++) {
					out.append(weights[i][j][k]);
					out.append("\t");
				}
				out.append("\n");
			}
			
			out.append("*************************************************************************\n");
			out.append("Nodes\n");
			for(float[] f1 : nodes) {
				if(f1 == null)
					continue;
					
				for(float f2 : f1) {
					out.append(f2);
					out.append("\t");
				}
				out.append("\n");
			}
		}
		
		out.append("*************************************************************************");
		
		return out.toString();
	}
	
	public void setFitness(int f) {
		fitness = f;
	}
	
	public int getFitness() {
		return fitness;
	}
	
	public NeuralNetwork clone() {
		NeuralNetwork out = new NeuralNetwork(iW,hlW,oW);
		out.weights = this.weights.clone();
		return out;
	}
}