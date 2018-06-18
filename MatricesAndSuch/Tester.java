
public class Tester {
	public static void main(String[] args) {
		double[][] c1 = { { 2, 3 }, { 4, 8 } };
		double[] c2 = { 3, 6 };
		Matrix m1 = new Matrix(c1);
		Vector v2 = new Vector(c2);
		m1.exp(-1);
		NeuralNet n = new NeuralNet();
	}
}
