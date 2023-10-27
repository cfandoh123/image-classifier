public class ImageClassifier {

    // Creates a feature vector (1D array) from the given picture.
    public static double[] extractFeatures(Picture picture) {

        int width = picture.width(), height = picture.height();
        double[] features = new double[width * height]; // store flattened matrix data
        int idx = 0;  // linear array index for 'features'
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                features[idx++] = picture.get(col, row).getGreen();

        return features;
    }

    // See below.
    public static void main(String[] args) {
        In trainingIn = new In(args[0]);
        In testingIn = new In(args[1]);

        int trainingClassCount, trainingPicHeight, trainingPicWidth;
        trainingClassCount = trainingIn.readInt();
        trainingPicHeight = trainingIn.readInt();
        trainingPicWidth = trainingIn.readInt();

        MultiPerceptron perceptron =
                new MultiPerceptron(trainingClassCount,
                        trainingPicWidth * trainingPicHeight);

        while (!trainingIn.isEmpty()) {
            String filename = trainingIn.readString();
            int label = trainingIn.readInt();
            double[] trainingInput = extractFeatures(new Picture(filename));

            perceptron.trainMulti(trainingInput, label);
        }

        // Prediction

        // we know that the classCount and pic size is the same in training and
        // in testing so we ignore that compononent from the testing file
        // int testingClassCount, testingPicHeight, testingPicWidth;
        // testingClassCount = trainingIn.readInt();
        // testingPicHeight = trainingIn.readInt();
        // testingPicWidth = trainingIn.readInt();
        trainingIn.readInt();
        trainingIn.readInt();
        trainingIn.readInt();
        int total = 0, right = 0;
        while (!testingIn.isEmpty()) {
            String filename = testingIn.readString();
            int label = testingIn.readInt();
            double[] trainingInput = extractFeatures(new Picture(filename));

            int predictedVal = perceptron.predictMulti(trainingInput);
            total++;
            if (predictedVal == label) right++;

            StdOut.println(filename + ", label = " + label + ", predict = " + predictedVal);
        }

        StdOut.println("test error rate = " + (double) (total - right) / total);
    }
}
