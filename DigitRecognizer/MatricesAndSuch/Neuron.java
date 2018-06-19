import java.util.ArrayList;

public class Neuron {
	
	private double activationValue, weight;
	private String name;
	private ArrayList<Neuron> outputNeurons = new ArrayList<Neuron>();
	private double[] inputs;
	private int numInputs;
	
	public Neuron(double activationValue, double weight, String name, int numInputs) {
		this.activationValue = activationValue;
		this.weight = weight;
		this.name = name;
		this.numInputs = numInputs;
		
		inputs = new double[numInputs];
	}
	
	
	
}
