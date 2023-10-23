# Image Classifier using MultiPerceptron
This Java project demonstrates an image classifier using the MultiPerceptron data type. The project includes three main components: MultiPerceptron for multi-class classification, feature extraction for images, and a client program (ImageClassifier.java) that trains the classifier and tests its accuracy.

### Features
###### MultiPerceptron: 
The project includes a MultiPerceptron class for multi-class classification. This class allows you to train and predict labels for multi-class data using perceptrons.

###### Feature Extraction:
The ImageClassifier program provides a method to extract features from grayscale images. These features are converted into one-dimensional arrays suitable for use with the MultiPerceptron.

###### Training and Testing:
The client program, ImageClassifier.java, reads training data from a file and uses it to train the MultiPerceptron. It then reads testing data to make predictions and computes the test error rate.

### Input Data Format
The project expects input data files in a specific format:

Training Data File:

The first line contains the number of classes.
The second line contains the width and height of the images.
Each remaining line contains the name of an image file (e.g., a handwritten digit image) and its integer label.
Testing Data File:

The format is the same as the training data file, but this data is used to evaluate the classifier's performance.

### Results

The ImageClassifier program will output the following information:

Test Error Rate: The fraction of test images that the classifier misclassified.
Misclassified Images: A list of filenames, true labels, and incorrectly predicted labels for any misclassified images.
