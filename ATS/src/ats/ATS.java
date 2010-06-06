package ats;

import ats.classifiers.DecisionTree;
import weka.classifiers.trees.J48;

public class ATS {

	public ATS() {
		super();
	}

	public void runATS() {

		// Generate training and test data sets
		DataSet dataSet = new DataSet();
		dataSet.generateDataSet();
		dataSet.generateTrainingDataSet();
		dataSet.generateTestDataSet();

		// Build classifiers
		DecisionTree decisionTree = new DecisionTree();
		decisionTree.buildDecisionTree(dataSet.getTrainingDataSet());

		// Classify test data set
		decisionTree.classify(dataSet.getTestDataSet());

		// Evaluate classifier through cross validation
		decisionTree.crossValidate();

	}

	public static void main(String[] args) {

		ATS ats = new ATS();
		ats.runATS();

	}

}
