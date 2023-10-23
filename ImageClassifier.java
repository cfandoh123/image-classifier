import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageClassifier {

    public static double[] extractFeatures(Picture picture) {
        // Extract features from the picture and return as a 1D array
        // You can implement this function to convert the picture to a feature vector.
        return null;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: ImageClassifier <training_file> <testing_file> <output_file>");
            System.exit(1);
        }

        // Parse command line arguments
        String trainingFile = args[0];
        String testingFile = args[1];
        String outputFile = args[2];

        // Read training data from the training file
        MultiPerceptron multiPerceptron = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(trainingFile));
            int numClasses = Integer.parseInt(br.readLine());
            String[] imageSize = br.readLine().split(" ");
            int width = Integer.parseInt(imageSize[0]);
            int height = Integer.parseInt(imageSize[1]);
            multiPerceptron = new MultiPerceptron(numClasses, width * height);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String imageFile = parts[0];
                int label = Integer.parseInt(parts[1]);
                Picture picture = new Picture(imageFile);
                double[] features = extractFeatures(picture);
                multiPerceptron.trainMulti(features, label);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading training data: " + e.getMessage());
            System.exit(1);
        }

        // Read testing data from the testing file and classify
        int correct = 0;
        int total = 0;
        StringBuilder misclassifiedImages = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(testingFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String imageFile = parts[0];
                int label = Integer.parseInt(parts[1]);
                Picture picture = new Picture(imageFile);
                double[] features = extractFeatures(picture);
                int prediction = multiPerceptron.predictMulti(features);

                if (prediction == label) {
                    correct++;
                } else {
                    misclassifiedImages.append(imageFile).append(" (Predicted: ").append(prediction).append(", Actual: ").append(label).append(")\n");
                }

                total++;
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading testing data: " + e.getMessage());
            System.exit(1);
        }

        // Calculate the test error rate
        double errorRate = 1.0 - (double) correct / total;

        // Output results to the specified output file
        try {
            String result = "Test Error Rate: " + String.format("%.2f%%", errorRate * 100) + "\n";
            if (misclassifiedImages.length() > 0) {
                result += "Misclassified Images:\n" + misclassifiedImages.toString();
            }

            Files.write(Paths.get(outputFile), result.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
            System.exit(1);
        }
    }
}
