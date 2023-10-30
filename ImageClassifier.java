public class ImageClassifier {

    // Creates a feature vector (1D array) from the given picture.
    public static double[] extractFeatures(Picture picture) {

        int width = picture.width(), height = picture.height();
        // store flattened matrix data
        double[] features = new double[width * height];
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
        double[] trainingInput;
        while (!trainingIn.isEmpty()) {
            String filename = trainingIn.readString();
            int label = trainingIn.readInt();
            trainingInput = extractFeatures(new Picture(filename));

            perceptron.trainMulti(trainingInput, label);
        }

        // Prediction

        // we know that the classCount and pic size is the same in training and
        // in testing, so we ignore that component from the testing file
        testingIn.readInt();
        testingIn.readInt();
        testingIn.readInt();
        int total = 0, wrong = 0;

        double[] testingInput;

        while (!testingIn.isEmpty()) {
            String filename = testingIn.readString();
            int label = testingIn.readInt();
            testingInput = extractFeatures(new Picture(filename));

            int predictedVal = perceptron.predictMulti(testingInput);
            total++;
            if (predictedVal != label) {
                wrong++;
                StdOut.println(filename + ", label = " + label
                                       + ", predict = " + predictedVal);
            }


        }

        StdOut.println("test error rate = " + ((double) wrong) / total);
    }
}
