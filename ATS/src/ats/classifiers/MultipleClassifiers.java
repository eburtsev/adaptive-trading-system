package ats.classifiers;

import java.util.ArrayList;

import weka.core.Instances;

public class MultipleClassifiers {

	private C4_5DecisionTree c4_5Tree = new C4_5DecisionTree();
	private NaiveBayesTree nbTree = new NaiveBayesTree();
	private LRTree lrTree = new LRTree();

	private ArrayList<ArrayList<Double>> classIndices = new ArrayList<ArrayList<Double>>();

	public MultipleClassifiers(C4_5DecisionTree c4_5Tree,
			NaiveBayesTree nbTree, LRTree lrTree) {
		this.c4_5Tree = c4_5Tree;
		this.nbTree = nbTree;
		this.lrTree = lrTree;
	}

	public void applyClassifiers(Instances testData) {

		// Get baseline class indices from test dataset
		ArrayList<Double> baseline = new ArrayList<Double>();
		for (int i = 0; i < testData.numInstances(); i++) {
			baseline.add(testData.get(i).value(testData.numAttributes() - 1));
		}

		classIndices.add(0, baseline);
		classIndices.add(1, c4_5Tree.getClassificationResultSet());
		classIndices.add(2, nbTree.getClassificationResultSet());
		classIndices.add(3, lrTree.getClassificationResultSet());

		/*
		 * System.out.println("\nBaseline: " + baseline.size() + "\nC4_5Tree: "
		 * + c4_5Tree.getClassificationResultSet().size() + "\nNBTree: " +
		 * nbTree.getClassificationResultSet().size() + "\nLRTree: " +
		 * lrTree.getClassificationResultSet().size());
		 */

		ArrayList<Double> multipleClassificationResultSet = new ArrayList<Double>();
		int one_two_three = 0, two_three = 0, none = 0;
		for (int i = 0; i < baseline.size(); i++) {

			System.out.println("Baseline: " + classIndices.get(0).get(i)
					+ " C4_5Tree: " + classIndices.get(1).get(i) + " NBTree: "
					+ classIndices.get(2).get(i) + " LRTree: "
					+ classIndices.get(3).get(i));

			if (classIndices.get(0).get(i).equals(classIndices.get(1).get(i))
					|| classIndices.get(0).get(i).equals(
							classIndices.get(3).get(i))) {
				multipleClassificationResultSet.add(i, classIndices.get(1).get(
						i));
				one_two_three++;
			} else if (classIndices.get(2).get(i).equals(
					classIndices.get(3).get(i))) {
				multipleClassificationResultSet.add(i, classIndices.get(2).get(
						i));
				two_three++;
			} else {
				multipleClassificationResultSet.add(i, classIndices.get(0).get(
						i));
				none++;
			}
		}

		classIndices.add(4, multipleClassificationResultSet);

		double score = 0;
		for (int i = 0; i < baseline.size(); i++) {
			System.out.println("score: " + classIndices.get(0).get(i)
					+ classIndices.get(4).get(i));
			if (classIndices.get(0).get(i).equals(classIndices.get(4).get(i))) {
				score++;
			}
		}
		score = score / 78.0;
		System.out.println("\none_two: " + one_two_three);
		System.out.println("\ntwo_three: " + two_three);
		System.out.println("\nnone: " + two_three);
		System.out.println("\nscore: " + score * 100
				+ " % Correctly Classified Instances");
	}

}
