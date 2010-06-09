package ats;

import ats.classifiers.BFDecisionTree;
import ats.classifiers.BayesianNetwork;
import ats.classifiers.C4_5DecisionTree;

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
			C4_5DecisionTree c4_5DecisionTree = new C4_5DecisionTree();
			c4_5DecisionTree.buildDecisionTree(dataSet.getTrainingDataSet());

			// BayesianNetwork bayesNet = new BayesianNetwork();
			// bayesNet.buildBayesNet(dataSet.getTrainingDataSet());
	
			//BFDecisionTree bfDecisionTree = new BFDecisionTree();
			//bfDecisionTree.buildDecisionTree(dataSet.getTrainingDataSet());

		// Classify test data set
			c4_5DecisionTree.classify(dataSet.getTestDataSet());
			// bayesNet.classify(dataSet.getTestDataSet());
			//bfDecisionTree.classify(dataSet.getTestDataSet());

		// Evaluate classifier through cross validation
			c4_5DecisionTree.crossValidate();
			// bayesNet.crossValidate();
			//bfDecisionTree.crossValidate();

		// Visualize classifer internal model built upon completion of training session
			c4_5DecisionTree.getInternalModel(dataSet.getTrainingDataSet());
			// bayesNet.getInternalModel(dataSet.getTrainingDataSet());
	}

	public static void main(String[] args) {

		ATS ats = new ATS();
		ats.runATS();

	}
}
