// future optimization, make all the matrix methods return new matrices maybe
// alternative keep changing the original matrix, but make a copy outside of this class


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

	public Matrix transpose() {
		double[][] rd = new double[cols][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rd[j][i] = fullMatrix[i][j];
			}
		}
		Matrix r = new Matrix(rd);
		return r;

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

	public Matrix exp(double k) {
		double[][] fm = new double[dimensions()[0]][dimensions()[1]];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				fm[i][j] = Math.pow(fullMatrix[i][j],k);
			}
		}
		Matrix r = new Matrix(fm);
		return r;
	}

	public Matrix add(Matrix otherMatrix) {
		double[][] fm = new double[dimensions()[0]][dimensions()[1]];
		if (checkDimensionsEqual(otherMatrix)) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					fm[i][j] = fullMatrix[i][j] + otherMatrix.get(i, j);
				}
			}
			Matrix r = new Matrix(fm);
			return r;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Matrix scalarAddition(double k) {
		double[][] fm = new double[dimensions()[0]][dimensions()[1]];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				fm[i][j] = fullMatrix[i][j] + k;
			}
		}
		Matrix r = new Matrix(fm);
		return r;
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

	public Matrix scalarMultiplication(double k) {
		double[][] fm = new double[dimensions()[0]][dimensions()[1]];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				fm[i][j] = fullMatrix[i][j] * k;
			}
		}
		Matrix r = new Matrix(fm);
		return r;
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

	public Matrix correspondingMultiplication(Matrix otherMatrix) {
		double[][] fMatrix = new double[dimensions()[0]][dimensions()[1]];
		if (checkDimensionsEqual(otherMatrix)) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					fMatrix[i][j] = get(i, j) * otherMatrix.get(i, j);
				}
			}
			Matrix r = new Matrix(fMatrix);
			return r;
		}
		throw new IllegalArgumentException();
	}


	// public Matrix correspondingMultiplication(Vector otherVector) {
	// 	double[][] fMatrix = new double[dimensions()[0]][dimensions()[1]];
	// 	if (checkDimensionsEqual(otherMatrix)) {
	// 		for (int i = 0; i < rows; i++) {
	// 			for (int j = 0; j < cols; j++) {
	// 				fMatrix[i][j] = get(i, j) * otherMatrix.get(i, j);
	// 			}
	// 		}
	// 		Matrix r = new Matrix(fMatrix);
	// 		return r;
	// 	}
	// 	throw new IllegalArgumentException();
	// }
}
