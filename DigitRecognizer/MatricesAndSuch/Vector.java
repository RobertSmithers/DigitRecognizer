
public class Vector {
	private int rows, cols;
	private double[][] fullVector;

	public Vector(int r) {
		rows = r;
		cols = 1;
		fullVector = new double[rows][cols];
	}
	
	public Vector(double[] fVector) {
		rows = fVector.length;
		cols = 1;
		this.fullVector = new double[rows][cols];
		for (int i = 0; i < fVector.length; i++) {
			fullVector[i][0] = fVector[i];
		}
	}

	public Vector(double[][] fullVector) {
		this.fullVector = fullVector;
		rows = fullVector.length;
		cols = 1;
	}

	public int[] dimensions() {
		int[] dims = { rows, cols };
		return dims;
	}

	public double get(int r, int c) {
		return fullVector[r][c];
	}

	private boolean checkDimensionsEqual(Vector otherVector) {
		for (int i = 0; i < dimensions().length; i++) {
			if (dimensions()[i] != otherVector.dimensions()[i]) {
				return false;
			}
		}
		return true;
	}

	// for testing
	public String toString() {
		String r = "[";
		for (int i = 0; i < rows - 1; i++) {
			r += get(i, 0) + ", ";
		}
		r += get(rows - 1, 0) + "]";
		return r;
	}

	public void scalarMultiplication(double k) {
		for (int i = 0; i < rows; i++) {
			fullVector[i][0] *= k;
		}

	}

	public void add(Vector otherVector) {
		if (checkDimensionsEqual(otherVector)) {
			for (int i = 0; i < rows; i++) {
				fullVector[i][0] += otherVector.get(i, 0);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public double dot(Vector otherVector) {
		double p = 0.0;
		if (checkDimensionsEqual(otherVector)) {
			for (int i = 0; i < rows; i++) {
				p += get(i, 0) * otherVector.get(i, 0);
			}
			return p;
		}
		throw new IllegalArgumentException();
	}
}
