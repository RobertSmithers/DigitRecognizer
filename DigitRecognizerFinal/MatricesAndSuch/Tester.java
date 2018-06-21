public class Tester {
	public static void main(String[] args) {
		double[][] c1 = { { 2, 4 }, { 4, 8 } };
		double[] c2 = { 3, 6 };
		Matrix m1 = new Matrix(c1);
		Vector v2 = new Vector(c2);
		System.out.println(m1);
		System.out.println(v2);
		System.out.println(m1.cross(v2));
	}
}
