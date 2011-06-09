package ann;

import java.util.Scanner;

import assign3.ann.Problem;

/**********************************************************************
 * 
 * Collin Price
 * cp06vz @ brocku.ca
 * 
 * Network
 * Stores a 3 layer neural network.
 * 
 * Dec. 1, 2010
 * 
 **********************************************************************/

public class Network {

	private Layer[] network;
	
	/**
	 * 
	 * @param input number of input neurons
	 * @param hidden number of hidden neurons
	 * @param output number of output neurons
	 */
	public Network (int input, int hidden, int output, int s) {
		Layer.setSeed(s);
		network = new Layer[3];
		network[0] = new Layer(input);
		network[1] = new Layer(hidden);
		network[2] = new Layer(output);

		network[0].setLayers(null, network[1]);
		network[1].setLayers(network[0], network[2]);
		network[2].setLayers(network[1], null);
		
		network[0].init();
		network[1].init();
		network[2].init();
	} // constructor
	
	/**
	 * Trains the network using back propagation.
	 * @param in input layer
	 * @param out expected output
	 */
	private void feedForward(int[] in, int out) {
		network[0].setNodes(in);
		network[1].updateNodes();
		network[2].updateNodes();
		
		network[2].updateWeights(out);
		network[1].updateWeights(out);
	} // feedForward
	
	/**
	 * Runs the network through a feed forward to get the output.
	 * @param in input neurons
	 * @param out expected output
	 */
	private void testNetwork(int[] in, int out) {
		network[0].setNodes(in);
		network[1].updateNodes();
		network[2].updateNodes();
		
		System.out.println("Output = " + network[2].getOutput()[0] + " : Expected " + out);
	} // testNetwork
	
	/**
	 * Returns the error at the output layer.
	 * @return
	 */
	private double getError() {
		return network[2].getErrors()[0];
	} // getError
	
	public static void main (String args[]) {		
		Scanner in = new Scanner(System.in);
		
		int epoch = 0;
		double error_value = 0.0;
		int tests = 0;
		double output_error = 0.0;
		int seed = 0;
		
		System.out.println("Enter a seed for the Random.");
		seed = in.nextInt();
		System.out.println("How many times should the network be trained? (Epoch)");
		epoch = in.nextInt();
		System.out.println("What error value should the testing stop at?");
		error_value = in.nextDouble();
		
		Network net = new Network(4,4,1,seed);
		System.out.println("Training Network");
		for (int j = 0; j < epoch; j++) {
			System.out.println("Round " + j);
			double error = 0.0;
			for (int i = 0; i < 16; i++) {
				net.feedForward(Problem.getInput(i), Problem.getOutput(i));
				error += Math.pow(net.getError(),2);
			}
			error /= 12.0;
			error = Math.sqrt(error);
			if (error < error_value) {
				System.out.println("Error reached: " + error);
				break;
			}
			output_error = error;
		}
		
		System.out.println("Output error is " + output_error);
		System.out.println("Testing Network");
		for (int i = 0; i < 16; i++) {
			net.testNetwork(Problem.getInput(i), Problem.getOutput(i));
		}
		
		
	} // main
	
} // Network
