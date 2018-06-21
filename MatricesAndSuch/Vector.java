
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

	public Vector sigmoid() {
		double[] fVector = new double[dimensions()[0]];
		for (int i = 0; i < rows; i++) {
			fVector[i] = 1 / (1 + Math.exp(fullVector[i][0]));
		}
		Vector f = new Vector(fVector);
		return f;
	}

	public Vector sigmoidDerivative() {
		double[] fVector = new double[dimensions()[0]];
		double d,etothenegativez;
		for (int i = 0; i < rows; i++) {
        	etothenegativez = Math.exp(-fullVector[i][0]);
        	d = etothenegativez / (Math.pow(1 + etothenegativez, 2));
			fVector[i] = d;
        }
		Vector f = new Vector(fVector);
		return f;
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

	public Vector scalarMultiplication(double k) {
		double[] fVector = new double[dimensions()[0]];
		for (int i = 0; i < rows; i++) {
			fVector[i] = fullVector[i][0] * k;
		}
		Vector f = new Vector(fVector);
		return f;
	}

	public Vector exp(double k) {
		double[] fVector = new double[dimensions()[0]];
		for (int i = 0; i < rows; i++) {
			fVector[i] = Math.pow(fullVector[i][0], k);
		}
		Vector f = new Vector(fVector);
		return f;
	}


	public Vector scalarAddition(double k) {
		double[] fVector = new double[dimensions()[0]];
		for (int i = 0; i < rows; i++) {
			fVector[i] = fullVector[i][0] + k;
		}
		Vector f = new Vector(fVector);
		return f;
	}

	public Matrix transpose() {
		double[][] rd = new double[cols][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rd[j][i] = fullVector[i][j];
			}
		}
		Matrix r = new Matrix(rd);
		return r;
	}


	public Vector add(Vector otherVector) {
		double[] fVector = new double[dimensions()[0]];
		if (checkDimensionsEqual(otherVector)) {
			for (int i = 0; i < rows; i++) {
				fVector[i] += fullVector[i][0] + otherVector.get(i, 0);
			}
			Vector r = new Vector(fVector);
			return r;
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

	public Vector correspondingMultiplication(Vector otherVector) {
		double[] fVector = new double[dimensions()[0]];
		if (checkDimensionsEqual(otherVector)) {
			for (int i = 0; i < rows; i++) {
				fVector[i] = get(i, 0) * otherVector.get(i, 0);
			}
			Vector r = new Vector(fVector);
			return r;
		}
		throw new IllegalArgumentException();
	}

	public Matrix iterativeDivision(Vector denominatorVector) {
		double[][] fMatrix = new double[dimensions()[0]][denominatorVector.dimensions()[0]];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < denominatorVector.dimensions()[0]; j++) {
				fMatrix[i][j] = get(i, 0) / denominatorVector.get(j, 0);
			}
		}
		Matrix r = new Matrix(fMatrix);
		return r;
	}

	// Prerequisite: othervector is of the form 1 x n
	public Matrix cross(Matrix otherVector) {
		if (this.dimensions()[1] == otherVector.dimensions()[0]) {
			double[][] r = new double[this.dimensions()[0]][otherVector.dimensions()[1]];
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < r[0].length; j++) {
					r[i][j] = get(i, 0) * otherVector.get(0, j);
				}
			}
			Matrix returnVector = new Matrix(r);
			return returnVector;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
