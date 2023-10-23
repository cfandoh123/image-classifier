import java.util.Random;

public class MultiPerceptron {
    private int numberOfClasses;
    private int numberOfInputs;
    private Perceptron[] perceptrons;

    // Creates a multi-perceptron object with m classes and n inputs.
    public MultiPerceptron(int m, int n) {
        numberOfClasses = m;
        numberOfInputs = n;
        perceptrons = new Perceptron[m];
        for (int i = 0; i < m; i++) {
            perceptrons[i] = new Perceptron(n);
        }
    }

    // Returns the number of classes m.
    public int numberOfClasses() {
        return numberOfClasses;
    }

    // Returns the number of inputs n.
    public int numberOfInputs() {
        return numberOfInputs;
    }

    // Returns the predicted label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x) {
        Random random = new Random();
        int predictedLabel = 0;
        double maxWeightedSum = perceptrons[0].weightedSum(x);

        for (int i = 1; i < numberOfClasses; i++) {
            double weightedSum = perceptrons[i].weightedSum(x);
            if (weightedSum > maxWeightedSum) {
                maxWeightedSum = weightedSum;
                predictedLabel = i;
            } else if (weightedSum == maxWeightedSum) {
                // If there's a tie, choose randomly among tied perceptrons.
                if (random.nextBoolean()) {
                    predictedLabel = i;
                }
            }
        }

        return predictedLabel;
    }

    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int label) {
        perceptrons[label].train(x, 1);
        for (int i = 0; i < numberOfClasses; i++) {
            if (i != label) {
                perceptrons[i].train(x, -1);
            }
        }
    }

    // Returns a String representation of this MultiPerceptron.
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < numberOfClasses; i++) {
            sb.append(perceptrons[i].toString());
            if (i < numberOfClasses - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        int m = 2;
        int n = 3;

        double[] training1 = {  3.0,  4.0,  5.0 };  // class 1
        double[] training2 = {  2.0,  0.0, -2.0 };  // class 0
        double[] training3 = { -2.0,  0.0,  2.0 };  // class 1
        double[] training4 = {  5.0,  4.0,  3.0 };  // class 0

        MultiPerceptron perceptron = new MultiPerceptron(m, n);
        System.out.println(perceptron);
        perceptron.trainMulti(training1, 1);
        System.out.println(perceptron);
        perceptron.trainMulti(training2, 0);
        System.out.println(perceptron);
        perceptron.trainMulti(training3, 1);
        System.out.println(perceptron);
        perceptron.trainMulti(training4, 0);
        System.out.println(perceptron);

        double[] testing1 = { -1.0, -2.0, 3.0 };
        double[] testing2 = { 2.0, -5.0, 1.0 };

        System.out.println(perceptron.predictMulti(testing1));
        System.out.println(perceptron);
        System.out.println(perceptron.predictMulti(testing2));
        System.out.println(perceptron);
    }
}
