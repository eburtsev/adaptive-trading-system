package ats;

import ats.classifiers.BFDecisionTree;
import ats.classifiers.BayesNetwork;
import ats.classifiers.BayesianNetwork;
import ats.classifiers.C4_5DecisionTree;
import ats.classifiers.KNearestNeighbours;
import ats.classifiers.LRTree;
import ats.classifiers.LazyBayesianRules;
import ats.classifiers.MultipleClassifiers;
import ats.classifiers.NaiveBayesTree;
import ats.classifiers.Rules;
import ats.strategies.RSI;

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

		LRTree lrTree = new LRTree();
		lrTree.buildLRTree(dataSet.getTrainingDataSet());

		NaiveBayesTree nbTree = new NaiveBayesTree();
		nbTree.buildNBTree(dataSet.getTrainingDataSet());

		C4_5DecisionTree c4_5Tree = new C4_5DecisionTree();
		c4_5Tree.buildDecisionTree(dataSet.getTrainingDataSet());

		// BayesianNetwork bayesNet = new BayesianNetwork();
		// bayesNet.buildBayesNet(dataSet.getTrainingDataSet());

		// BFDecisionTree bfDecisionTree = new BFDecisionTree();
		// bfDecisionTree.buildDecisionTree(dataSet.getTrainingDataSet());

		// BayesNetwork bayesNet = new BayesNetwork();
		// bayesNet.buildBayesNet(dataSet.getTrainingDataSet());

		// LazyBayesianRules lbr = new LazyBayesianRules();
		// lbr.buildLBR(dataSet.getTrainingDataSet());

		// KNearestNeighbours knn = new KNearestNeighbours();
		// knn.buildKNN(dataSet.getTrainingDataSet());

		// Rules furia = new Rules();
		// furia.buildFURIA(dataSet.getTrainingDataSet());

		// Classify test data set
		c4_5Tree.classify(dataSet.getTestDataSet());
		nbTree.classify(dataSet.getTestDataSet());
		lrTree.classify(dataSet.getTestDataSet());
		// bfDecisionTree.classify(dataSet.getTestDataSet());
		// lbr.classify(dataSet.getTestDataSet());
		// knn.classify(dataSet.getTestDataSet());
		// furia.classify(dataSet.getTestDataSet());

		// Evaluate classifier through cross validation
		// nbt.crossValidate();
		// c4_5DecisionTree.crossValidate();
		// lrTree.crossValidate();
		// bfDecisionTree.crossValidate();
		// lbr.crossValidate();
		// knn.crossValidate();
		// furia.crossValidate();

		// Visualize classifier internal model built upon completion of training
		// session
		// c4_5DecisionTree.getInternalModel(dataSet.getTrainingDataSet());
		// nbt.getInternalModel(dataSet.getTrainingDataSet());
		// lrTree.getInternalModel(dataSet.getTrainingDataSet());

		MultipleClassifiers mc = new MultipleClassifiers(c4_5Tree, nbTree, lrTree);
		mc.applyClassifiers(dataSet.getTestDataSet());
	}

	public static void main(String[] args) {

		ATS ats = new ATS();
		ats.runATS();

	}
}
