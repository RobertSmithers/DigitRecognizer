/*
Assumptions: pixels have a greyscale value from 0 to 1
*/

// Somewhere need imputs to hold greyscale values
// Hidden layer has 36 nodes

import java.util.Random;

public class NeuralNet {

    // ****private Matrix inputs; ****
    private Matrix weightInputHidden, weightHiddenOutput;

    // number of rows in image, number of columns in image, number of nodes in hidden layer
    public NeuralNet(int imgrows, int imgcols, int numNodesinH1) {
        Random generator = new Random(1);
        double[][] wIH = new double[numNodesinH1][(imgrows * imgcols)];
        for (int i = 0; i < wIH.length; i++) {
            for (int j = 0; j < wIH[0].length; j++) {
                wIH[i][j] = generator.nextDouble();
            }
        }
        weightInputHidden = new Matrix(wIH);
        // System.out.println(weightInputHidden);

        // number of nodes in hidden layer * 10 outputs
        double[][] wHO = new double[10][numNodesinH1];
        for (int i = 0; i < wHO.length; i++) {
            for (int j = 0; j < wHO[0].length; j++) {
                wHO[i][j] = generator.nextDouble();
            }
        }
        weightHiddenOutput = new Matrix(wHO);
        // System.out.println(weightHiddenOutput);
    }

    // For testing only
    public NeuralNet() {
        double[][] wIH = {{0.8,0.2},{0.4,0.9}, {0.3,0.5}};
        weightInputHidden = new Matrix(wIH);
        double[][] wHO = {{0.3,0.5,0.9}};
        weightHiddenOutput = new Matrix(wHO);
        System.out.println(weightInputHidden);
    }

/*    public double sigmoid(double z) {
        double s = 1 / (1 + Math.exp(-z));
        return s;
    }*/

    public double sigmoidDerivative(double z) {
        double d,etothenegativez;
        etothenegativez = Math.exp(-z);
        d = etothenegativez / (Math.pow(1 + etothenegativez, 2));
        return d;
    }

    public double everything(Vector input, Vector expected) {
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
    }

    // For testing only
    // THIS IS DEFINIELY WRONG
    /*public double everythingTest(Vector input, double expected) {
        // Forward propogation
        Vector Hsum = weightInputHidden.cross(input);
        Vector Hresult = Hsum.sigmoid();

        //for test these are 1 x 1s, for not test they're vectors
        Vector Osum = weightHiddenOutput.cross(Hresult);
        Vector Oresult = Osum.sigmoid();

        // Backpropogation

        // the err and the deltaOutputSum are vectors in not test
        double err = Oresult.get(0,0) - expected;
        double deltaOutputSum = sigmoidDerivative(Osum.get(0,0)) * err;
        Vector deltaWeightsHO = Hresult.exp(-1).scalarMultiplication(deltaOutputSum);
        // THIS LINE IS DEFINITELY WRONG
        Vector deltaHiddenSum =  weightHiddenOutput.exp(-1).scalarMultiplication(deltaOutputSum).correspondingMultiplication(Hsum.sigmoidDerivative());
    }*/

    // train()

}
