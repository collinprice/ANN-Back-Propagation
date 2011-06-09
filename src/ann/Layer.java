package ann;

import java.util.Random;

/**********************************************************************
 * 
 * Collin Price
 * cp06vz @ brocku.ca
 * 
 * Layer
 * A Layer stores neurons. It can be used as any layer of a neural network.
 * 
 * Dec. 1, 2010
 * 
 **********************************************************************/

public class Layer {

	private static Random rand;// = new Random(500);
	private double learning_rate = 0.5;
	private double momentum_rate = 0.9;
	private double threshold = 0.5;
	
	private Neuron[] neurons;
	private Layer prev;
	private Layer next;
	
	public Layer(int size) {
		neurons = new Neuron[size];
	} // constructor
	
	/**
	 * Initialises the Random for all layers.
	 * @param s
	 */
	public static void setSeed(int s) {
		rand = new Random(s);
	} // setSeed
	
	/**
	 * Sets the next and previous layer pointers.
	 * @param in
	 * @param out
	 */
	public void setLayers(Layer in, Layer out) {
		prev = in;
		next = out;
	} // setLayers
	
	/**
	 * 
	 * @return number of neurons on the layer
	 */
	public int size() {
		return neurons.length;
	} // size

	/**
	 * Initialises the connection weights from [-1,+1]
	 */
	public void init() {
		if (prev == null) {
			for (int i = 0; i < neurons.length; i++) {
				neurons[i] = new Neuron(threshold, 1);
			}
		} else {
			for (int i = 0; i < neurons.length; i++) {
				neurons[i] = new Neuron(threshold, prev.size());
				double[] con = neurons[i].getIncomingConnections();
				for (int j = 0; j < con.length; j++) {
					con[j] = (rand.nextDouble() * 2) - 1;
				}
			}
			
		}
	} // init

	/**
	 * Used for setting the values of the input layer.
	 * @param input
	 */
	public void setNodes(int[] input) {
		for (int i = 0; i < input.length; i++) {
			neurons[i].setOutput(input[i]);
		}
	} // setNodes

	/**
	 * Feeds forward the nodes in the layer. Each nodes output is updated.
	 */
	public void updateNodes() {
		double[] output = prev.getOutput();
		for (int i = 0; i < neurons.length; i++) {
			double[] cons = neurons[i].getIncomingConnections();
			double sum = 0.0;
			
			for (int j = 0; j < cons.length; j++) {
				if (j == cons.length-1) {
					sum += 1.0 * cons[j];
				} else {
					sum += output[j] * cons[j];
				}
			}
			
			neurons[i].activationSigmoid(sum);
		}
	} // updateNodes
	
	/**
	 * Returns the output of the neurons in the layer.
	 * @return
	 */
	public double[] getOutput() {
		double[] output = new double[neurons.length];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = neurons[i].getOutput();
		}
		
		return output;
	} // getOutput

	/**
	 * Updates the error values for each neuron in the layer.
	 * @param expected
	 */
	public void calcError(int expected) {
		if (next == null) {
			for (Neuron n : neurons) {
				n.setError((expected - n.getActivation()));
			}
		} else {
			for (int i = 0; i < neurons.length; i++) {
				double sum = 0.0;
				double[] cons = next.getConnectionWeights(i);
				double[] errors = next.getErrors();
				
				for (int j = 0; j < errors.length; j++) {
					sum += cons[j] * errors[j];
				}
				
				neurons[i].setError(sum);
			}
		}
	} // calcError
	
	/**
	 * Returns the error values for each neuron in the layer.
	 * @return
	 */
	public double[] getErrors() {
		double[] e = new double[neurons.length];
		for (int i = 0; i < neurons.length; i++) {
			e[i] = neurons[i].getError();
		}
		return e;
	} // getErrors

	/**
	 * 
	 * @param n
	 * @return the connection weights that come from the given node
	 */
	public double[] getConnectionWeights(int n) {
		double[] cons = new double[neurons.length];
		
		for (int i = 0; i < neurons.length; i++) {
			double[] c = neurons[i].getIncomingConnections();
			cons[i] = c[n];
		}
		
		return cons;
	} // getConnectionWeights

	/**
	 * Performs back propagation. It updates the connection weights using the error values.
	 * @param expected
	 */
	public void updateWeights(int expected) {
		
		if (next == null) {
			double[] output = prev.getOutput();
			for (Neuron n : neurons) {
				double[] cons = n.getIncomingConnections();
				for (int i = 0; i < cons.length; i++) { // each incoming connection
					if (i == cons.length-1) {
						cons[i] += learning_rate * (expected - n.getOutput()) * n.derivative(n.getIn()) * 1.0;
					} else {
						cons[i] += learning_rate * (expected - n.getOutput()) * n.derivative(n.getIn()) * output[i];
					}
				}
				n.setIncomingConnections(cons);
				n.setError(expected - n.getOutput());
			}
		} else {
			double[] output = prev.getOutput();
			for (int j = 0; j < neurons.length; j++) {
				double[] cons = neurons[j].getIncomingConnections();
				for (int i = 0; i < cons.length; i++) { // each incoming connection
					double sum = 0.0;
					Neuron[] next_neurons = next.getNeurons();
					for (Neuron e : next_neurons) {
						sum += (expected - e.getOutput()) * e.derivative(e.getIn()) * e.getIncomingConnections()[j];
					}
					if (i == cons.length-1) {
						cons[i] += learning_rate * sum * neurons[j].derivative(neurons[j].getIn()) * 1.0;
					} else {
						cons[i] += learning_rate * sum * neurons[j].derivative(neurons[j].getIn()) * output[i];
					}
				}
				neurons[j].setIncomingConnections(cons);
				neurons[j].setError(expected - neurons[j].getOutput());
			}
		}
		
	} // updateWeights
	
	/**
	 * Returns a pointer to each neuron in the layer.
	 * @return
	 */
	public Neuron[] getNeurons() {
		return neurons;
	} // getNeurons
	
} // Layer
