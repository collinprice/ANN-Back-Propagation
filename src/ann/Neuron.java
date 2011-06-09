package ann;

/**********************************************************************
 * 
 * Collin Price
 * cp06vz @ brocku.ca
 * 
 * Neuron
 * 
 * Dec. 1, 2010
 * 
 **********************************************************************/

public class Neuron {

	private double error;
	private double activation_result;
	private double threshold;
	private double output;
	private double[] connections; // incoming connections
	private double in; // sum of inputs
	
	public Neuron (double threshold, int cons) {
		this.threshold = threshold;
		connections = new double[cons+1];
	} // constructor
	
	/**
	 * 
	 * @param input sum of weights * output
	 */
	public void activationSigmoid(double input) {
		in = input;
		input += threshold;
		input = 1/(1+Math.exp(-input));
		activation_result = input;
		output = activation_result;
	} // activation
	
	/**
	 * 
	 * @param input
	 * @return the derivative of the sigmoid function
	 */
	public double derivative(double input) {
		return Math.exp(-input) * Math.pow((1+Math.exp(-input)), -2);
	} // derivative
	
	/**
	 * 
	 * @return the input that was given to the sigmoid function
	 */
	public double getIn() {
		return in;
	} // getIn
	
	/**
	 * updates the neurons threshold value
	 * 
	 * @param learning
	 */
	public void updateThreshold(double learning) {
		threshold = threshold + learning;
	} // updateThreshold
	
	/**
	 * Gets the neurons output
	 * 
	 * @return
	 */
	public double getOutput() {
		return output;
	} // getOutput
	
	/**
	 * sets output. used for input nodes.
	 * 
	 * @param input
	 */
	public void setOutput(int input) {
		output = input;
	} // setOutput
	
	/**
	 * Returns the neurons output
	 * @return
	 */
	public double getActivation() {
		return activation_result;
	} // getActivation
	
	/**
	 * Returns the neurons error value
	 * @return
	 */
	public double getError() {
		return error;
	} // getError
	
	/**
	 * 
	 * @param expected the expected result
	 */
	public void setError(double e) {
		error = e;
	} // setError
	
	/**
	 * Returns the neurons input connections
	 * @return
	 */
	public double[] getIncomingConnections() {
		return connections;
	} // getIncomingConnections

	/**
	 * Sets the input connection weights
	 * @param cons
	 */
	public void setIncomingConnections(double[] cons) {
		connections = cons;
	} // setIncomingConnections
	
} // Neuron
