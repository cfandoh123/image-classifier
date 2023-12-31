public class MultiPerceptron {

    private Perceptron[] perceptrons; // perceptron object for each set of input

    // Creates a multi-perceptron object with m classes and n inputs.
    // It creates an array of m perceptrons, each with n inputs.
    public MultiPerceptron(int m, int n) {
        perceptrons = new Perceptron[m];
        for (int i = 0; i < m; i++)
            perceptrons[i] = new Perceptron(n);
    }

    // Returns the number of classes m.
    public int numberOfClasses() {
        return perceptrons.length;
    }

    // Returns the number of inputs n (length of the feature vector).
    public int numberOfInputs() {
        return perceptrons[0].numberOfInputs();
    }

    // Returns the predicted label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x) {
        int maxWeightClassIndex = 0;
        for (int i = 1; i < numberOfClasses(); i++)
            if (perceptrons[maxWeightClassIndex].weightedSum(x) <
                    perceptrons[i].weightedSum(x))
                maxWeightClassIndex = i;
        return maxWeightClassIndex;
    }

    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int label) {
        for (int i = 0; i < numberOfClasses(); i++) {
            if (label == i)
                perceptrons[i].train(x, +1);
            else
                perceptrons[i].train(x, -1);

        }
    }

    // Returns a String representation of this MultiPerceptron, with
    // the string representations of the perceptrons separated by commas
    // and enclosed in parentheses.
    // Example with m = 2 and n = 3: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))
    public String toString() {
        StringBuilder repr = new StringBuilder("(" + perceptrons[0]);
        for (int i = 1; i < this.numberOfClasses(); i++)
            repr.append(", " + perceptrons[i]);
        repr.append(")");
        return repr.toString();
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {

        int m = 2;
        int n = 3;

        double[] training1 = { 3.0, 4.0, 5.0 };  // class 1
        double[] training2 = { 2.0, 0.0, -2.0 };  // class 0
        double[] training3 = { -2.0, 0.0, 2.0 };  // class 1
        double[] training4 = { 5.0, 4.0, 3.0 };  // class 0

        MultiPerceptron perceptron = new MultiPerceptron(m, n);

        StdOut.println("Number of classes: " + perceptron.numberOfClasses());
        StdOut.println("Number of inputs: " + perceptron.numberOfInputs());
        StdOut.println(perceptron);
        perceptron.trainMulti(training1, 1);
        StdOut.println(perceptron);
        perceptron.trainMulti(training2, 0);
        StdOut.println(perceptron);
        perceptron.trainMulti(training3, 1);
        StdOut.println(perceptron);
        perceptron.trainMulti(training4, 0);
        StdOut.println(perceptron);

        double[] testing1 = { -1.0, -2.0, 3.0 };
        double[] testing2 = { 2.0, -5.0, 1.0 };

        StdOut.println(perceptron.predictMulti(testing1));
        StdOut.println(perceptron);
        StdOut.println(perceptron.predictMulti(testing2));
        StdOut.println(perceptron);
    }
}
