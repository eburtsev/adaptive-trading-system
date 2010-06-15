package ats.classifiers;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.LMT;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import ats.DataSet;

// A decision tree with logistic regression functions at the leaves
public class LRTree {

	private LMT lrTree = null;
	ArrayList<Double> classificationResultSet = new ArrayList<Double>();

	public LRTree() {
		super();
	}

	// Build classifier
	public void buildLRTree(Instances trainingData) {

		try {
			lrTree = new LMT();
			lrTree.buildClassifier(trainingData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Classify test data set
	public void classify(Instances testData) {

		Double classificationResult = null;

		for (int i = 0; i < testData.numInstances(); i++) {

			try {
				classificationResult = lrTree.classifyInstance(testData.get(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			classificationResultSet.add(i, classificationResult);
		}

		for (int i = 0; i < classificationResultSet.size(); i++) {
			System.out.println("LRTree:" + classificationResultSet.get(i));
		}
	}

	// Evaluate classifier through cross validation
	public void crossValidate() {

		Instances newData = new Instances(new DataSet().getInstances());
		// Remove closing price "close" attribute
		newData.deleteAttributeAt(0);

		try {
			Evaluation eval = new Evaluation(newData);
			LMT untrainedLRTree = new LMT();
			eval.crossValidateModel(untrainedLRTree, newData, newData
					.numInstances(), new Random(1));
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getInternalModel(Instances trainingData) {

		try {
			// train classifier
			LMT cls = new LMT();
			cls.buildClassifier(trainingData);
			// display tree
			TreeVisualizer tv = new TreeVisualizer(null, cls.graph(),
					new PlaceNode2());
			JFrame jf = new JFrame("Weka Classifier Tree Visualizer: J48");
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setSize(800, 600);
			jf.getContentPane().setLayout(new BorderLayout());
			jf.getContentPane().add(tv, BorderLayout.CENTER);
			jf.setVisible(true);
			// adjust tree
			tv.fitToScreen();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Double> getClassificationResultSet() {
		return classificationResultSet;
	}

}
