package ats.classifiers;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.LBR;
import weka.core.Instances;
import ats.DataSet;

public class LazyBayesRules {

	private LBR lbr = null;
	ArrayList<double[]> classificationResultSet = new ArrayList<double[]>();

	public LazyBayesRules() {
		super();
	}

	// Build classifier
	public void buildLBR(Instances trainingData) {

		try {
			lbr = new LBR();
			// Train LBR classifier
			lbr.buildClassifier(trainingData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Classify test data set
	public void classify(Instances testData) {

		double[] classificationResult = null;

		for (int i = 0; i < testData.numInstances(); i++) {

			try {
				classificationResult = lbr.distributionForInstance(testData
						.get(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			classificationResultSet.add(i, classificationResult);
		}

	}

	// Evaluate classifier through cross validation
	public void crossValidate() {

		Instances newData = new Instances(new DataSet().getInstances());
		// Remove closing price "close" attribute
		newData.deleteAttributeAt(0);

		try {
			Evaluation eval = new Evaluation(newData);
			LBR untrainedLBR = new LBR();
			eval.crossValidateModel(untrainedLBR, newData, newData
					.numInstances(), new Random(1));
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Get classification result
	public ArrayList<double[]> getClassificationResultSet() {
		return classificationResultSet;
	}

}
