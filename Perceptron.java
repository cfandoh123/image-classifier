public class Perceptron {
    int numberOfInputs;
    double[] weights;

    // Creates a perceptron with n inputs and initializes weights to 0.
    public Perceptron(int n) {
        numberOfInputs = n;
        weights = new double[n];
    }

    // Returns the number of inputs n.
    public int numberOfInputs() {
        return numberOfInputs;
    }

    // Returns the weighted sum of the weight vector and x.
    public double weightedSum(double[] x) {
        if (x.length != numberOfInputs) {
            throw new IllegalArgumentException("Input vector size must match the number of inputs.");
        }

        double sum = 0.0;
        for (int i = 0; i < numberOfInputs; i++) {
            sum += weights[i] * x[i];
        }
        return sum;
    }

    // Predicts the label (+1 or -1) of input x.
    public int predict(double[] x) {
        double sum = weightedSum(x);
        if (sum > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    // Trains this perceptron on the labeled (+1 or -1) input x.
    public void train(double[] x, int label) {
        if (x.length != numberOfInputs) {
            throw new IllegalArgumentException("Input vector size must match the number of inputs.");
        }

        int prediction = predict(x);
        if (prediction != label) {
            // Update weights if prediction is incorrect
            for (int i = 0; i < numberOfInputs; i++) {
                weights[i] += label * x[i];
            }
        }
    }

    // Returns a String representation of the weight vector.
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < numberOfInputs; i++) {
            sb.append(weights[i]);
            if (i < numberOfInputs - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        int n = 3;

        double[] training1 = {  3.0,  4.0,  5.0 };  // yes
        double[] training2 = {  2.0,  0.0, -2.0 };  // no
        double[] training3 = { -2.0,  0.0,  2.0 };  // yes
        double[] training4 = {  5.0,  4.0,  3.0 };  // no

        Perceptron perceptron = new Perceptron(n);
        System.out.println(perceptron);
        perceptron.train(training1, +1);
        System.out.println(perceptron);
        perceptron.train(training2, -1);
        System.out.println(perceptron);
        perceptron.train(training3, +1);
        System.out.println(perceptron);
        perceptron.train(training4, -1);
        System.out.println(perceptron);
    }
}
