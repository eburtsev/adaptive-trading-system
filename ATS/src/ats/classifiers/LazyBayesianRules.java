package ats.classifiers;

import java.util.ArrayList;
import java.util.Random;

import ats.DataSet;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.LBR;

import weka.core.Instances;

public class LazyBayesianRules {

	private LBR lbr = null;

	public LazyBayesianRules() {
		super();
	}

	// Build classifier
	public void buildLBR(Instances trainingData) {

		try {
			lbr = new LBR();
			lbr.buildClassifier(trainingData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Classify test data set
	public void classify(Instances testData) {

		ArrayList<double[]> classificationResultSet = new ArrayList<double[]>();
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

		for (int i = 0; i < classificationResultSet.size(); i++) {
			System.out.println(classificationResultSet.get(i));
		}
	}

	// Evaluate classifier through cross validation
	public void crossValidate() {

		Instances newData = new Instances(new DataSet().getInstances());

		// Remove closing price "close" attribute
		newData.deleteAttributeAt(0);

		try {
			Evaluation eval = new Evaluation(newData);
			LBR lbr = new LBR();
			eval.crossValidateModel(lbr, newData, newData.numInstances(),
					new Random(1));
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
