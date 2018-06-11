public class Matrix {
	private int rows, cols;
	private double[][] fullMatrix;

	public Matrix(double[][] fullMatrix) {
		this.fullMatrix = fullMatrix;
		rows = fullMatrix.length;
		cols = fullMatrix[0].length;
	}

	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.cols = columns;
		fullMatrix = new double[rows][columns];
	}

	public int[] dimensions() {
		int[] dims = { rows, cols };
		return dims;
	}

	public double get(int r, int c) {
		return fullMatrix[r][c];
	}

	private boolean checkDimensionsEqual(Matrix otherMatrix) {
		for (int i = 0; i < dimensions().length; i++) {
			if (dimensions()[i] != otherMatrix.dimensions()[i]) {
				return false;
			}
		}
		return true;
	}

	// for testing
	public String toString() {
		String r = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				r += get(i, j) + " ";
			}
			r += "\n";
		}
		return r;
	}

	public void add(Matrix otherMatrix) {
		if (checkDimensionsEqual(otherMatrix)) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					fullMatrix[i][j] += otherMatrix.get(i, j);
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Vector[] getRowVectors() {
		Vector[] r = new Vector[rows];
		int i = 0;
		for (double[] j : fullMatrix) {
			r[i] = new Vector(j);
			i++;
		}
		return r;
	}
	
	public void scalarMultiplication(double k) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				fullMatrix[i][j] *= k;
			}
		}
	}
	
	public Vector cross(Vector otherVector) {
		if (this.dimensions()[1] == otherVector.dimensions()[0]) {
			double[][] r = new double[this.dimensions()[0]][otherVector.dimensions()[1]];
			Vector[] grv = getRowVectors();
			for (int i = 0; i < r.length; i++) {
				r[i][0] = grv[i].dot(otherVector);
			}
			Vector returnVector = new Vector(r);
			return returnVector;
		} else {
			throw new IllegalArgumentException();
		}
	}

}
