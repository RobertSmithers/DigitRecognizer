/*
Assumptions: pixels have a greyscale value from 0 to 1
*/

// Somewhere need imputs to hold greyscale values
// Hidden layer has 36 nodes

import java.util.Random;

public class Net {

    // ****private Vector inputs; ****
    private Matrix weightInputHidden, weightHiddenOutput, delta1, delta2;
    private Matrix weightHiddenOutputTranspose;
    private Vector Inputs, Hsum, Hresult, Osum, Oresult;
    private Vector err3, err2;
    private double learningRate;

    // number of rows in image, number of columns in image, number of nodes in hidden layer
    public Net(int imgrows, int imgcols, int numNodesinH1, double learningRate) {
        Random generator = new Random(1);
        double[][] wIH = new double[numNodesinH1][(imgrows * imgcols)];
        double[][] d1 = new double[numNodesinH1][(imgrows * imgcols)];
        for (int i = 0; i < wIH.length; i++) {
            for (int j = 0; j < wIH[0].length; j++) {
                wIH[i][j] = generator.nextDouble();
            }
        }
        weightInputHidden = new Matrix(wIH);
        delta1 = new Matrix(d1);
        // System.out.println(weightInputHidden);
        // System.out.println(delta1);

        // number of nodes in hidden layer * 10 outputs
        double[][] wHO = new double[10][numNodesinH1];
        double[][] d2 = new double[10][numNodesinH1];
        for (int i = 0; i < wHO.length; i++) {
            for (int j = 0; j < wHO[0].length; j++) {
                wHO[i][j] = generator.nextDouble();
            }
        }
        weightHiddenOutput = new Matrix(wHO);
        delta2 = new Matrix(d2);
        // System.out.println(weightHiddenOutput);
        // System.out.println(delta2);

        this.learningRate = learningRate;
        // System.out.println(learningRate);
    }

    public double sigmoid(double z) {
        double s = 1 / (1 + Math.exp(-z));
        return s;
    }

    public double sigmoidDerivative(double z) {
        double d,etothenegativez;
        etothenegativez = Math.exp(-z);
        d = etothenegativez / (Math.pow(1 + etothenegativez, 2));
        return d;
    }

    public void forwardPropogation(Vector input) {
        Inputs = input;
        Hsum = weightInputHidden.cross(Inputs);
        Hresult = Hsum.sigmoid();
        Osum = weightHiddenOutput.cross(Hresult);
        Oresult = Osum.sigmoid();
    }

    public void backPropogation(Vector input, Vector expected) {
        err3 = Oresult.add(expected.scalarMultiplication(-1));
        weightHiddenOutputTranspose = weightHiddenOutput.transpose();
        err2 = weightHiddenOutputTranspose.cross(err3).correspondingMultiplication(Hsum.sigmoidDerivative());

        // Check for issues maybe
        delta2 = delta2.add(err3.cross(Hresult.transpose()));
        delta1 = delta1.add(err2.cross(Inputs.transpose()));

        // Subtract delta from weights (might have to add)
        weightInputHidden = weightInputHidden.add(delta1.scalarMultiplication(/*-1 * */learningRate));
        weightHiddenOutput = weightHiddenOutput.add(delta2.scalarMultiplication(/*-1 * */learningRate));
    }

    public void testTrain(int runs) {
        Random generator = new Random(4);
        double[] inp = new double[784];
        double[] inp2 = new double[784];
        for (int i = 0; i < 784; i++) {
            inp[i] = generator.nextDouble();
            inp2[i] = generator.nextDouble();
        }
        double[] exp = {0,1.0,0,0,0,0,0,0,0,0};
        double[] exp2 = {1.0,0,0,0,0,0,0,0,0,0};

        Vector in = new Vector(inp);
        Vector in2 = new Vector (inp2);
        Vector expected = new Vector(exp);
        Vector expected2 = new Vector(exp2);

        for (int i = 0; i < runs; i++) {
            if (i % 2 == 0) {
                forwardPropogation(in);
                backPropogation(in, expected);
            } else {
                forwardPropogation(in2);
                backPropogation(in2, expected2);
            }
            if (i % 100 == 0 || i % 102 == 0) {
                System.out.println(Oresult);
            }
        }
    }

    public static void main(String[] args) {
        Net a = new Net(28, 28, 36, .05);
        a.testTrain(1000);
    }
    /*public double everything(Vector input, Vector expected) {
        // Forward propogation
        Vector Hsum = weightInputHidden.cross(input);
        Vector Hresult = Hsum.sigmoid();

        // These are vectors
        Vector Osum = weightHiddenOutput.cross(Hresult);
        Vector Oresult = Osum.sigmoid();

        // Backpropogation

        // Error
        Vector err = Oresult.add(expected.scalarMultiplication(-1));
        Vector deltaOutputSum = Osum.sigmoidDerivative().correspondingMultiplication(err);

        Vector deltaWeightsHO = deltaOutputSum.iterativeDivision(Hresult);
        // THIS LINE IS DEFINITELY WRONG
        Vector deltaHiddenSum =  weightHiddenOutput.exp(-1).scalarMultiplication(deltaOutputSum).correspondingMultiplication(Hsum.sigmoidDerivative());
    }*/

    // train()

}
