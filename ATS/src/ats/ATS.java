package ats;

import ats.classifiers.BayesNetwork;
import ats.classifiers.C4_5DecisionTree;
import ats.classifiers.LRTree;
import ats.classifiers.LazyBayesRules;
import ats.classifiers.MultipleClassifiers;
import ats.classifiers.NaiveBayesTree;

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
		
		C4_5DecisionTree c4_5Tree = new C4_5DecisionTree();
		c4_5Tree.buildDecisionTree(dataSet.getTrainingDataSet());
		
		NaiveBayesTree nbTree = new NaiveBayesTree();
		nbTree.buildNBTree(dataSet.getTrainingDataSet());

		LRTree lrTree = new LRTree();
		lrTree.buildLRTree(dataSet.getTrainingDataSet());

		BayesNetwork bayesNet = new BayesNetwork();
		bayesNet.buildBayesNet(dataSet.getTrainingDataSet());

		LazyBayesRules lbr = new LazyBayesRules();
		lbr.buildLBR(dataSet.getTrainingDataSet());

		// Classify test data set
		c4_5Tree.classify(dataSet.getTestDataSet());
		nbTree.classify(dataSet.getTestDataSet());
		lrTree.classify(dataSet.getTestDataSet());
		bayesNet.classify(dataSet.getTestDataSet());
		lbr.classify(dataSet.getTestDataSet());
				
		// Evaluate classifier through cross validation
		c4_5Tree.crossValidate();
		nbTree.crossValidate();
		lrTree.crossValidate();
		bayesNet.crossValidate();
		lbr.crossValidate();
		
		// Visualize classifier internal model built upon completion of training  session
		c4_5Tree.getInternalModel(dataSet.getTrainingDataSet());
		nbTree.getInternalModel(dataSet.getTrainingDataSet());
		lrTree.getInternalModel(dataSet.getTrainingDataSet());
		bayesNet.getInternalModel(dataSet.getTrainingDataSet());

		MultipleClassifiers mc = new MultipleClassifiers(c4_5Tree, nbTree, lrTree);
		mc.applyClassifiers(dataSet.getTestDataSet());
	}

	public static void main(String[] args) {

		ATS ats = new ATS();
		ats.runATS();
	}
}
