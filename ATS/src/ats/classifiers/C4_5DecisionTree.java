package ats.classifiers;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import ats.DataSet;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

// C4.5 decision tree classifier 
public class C4_5DecisionTree {

	private J48 decisionTree = null;
	ArrayList<Double> classificationResultSet = new ArrayList<Double>();

	public C4_5DecisionTree() {
		super();
	}

	// Build classifier
	public void buildDecisionTree(Instances trainingData) {

		try {
			String[] options = new String[1];
			options[0] = "-U"; // unpruned tree
			decisionTree = new J48(); // new instance of tree
			decisionTree.setOptions(options);
			// Train decision tree
			decisionTree.buildClassifier(trainingData);
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
				classificationResult = decisionTree.classifyInstance(testData
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
			J48 untrainedDecitionTree = new J48();
			eval.crossValidateModel(untrainedDecitionTree, newData, newData
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
			J48 cls = new J48();
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

	// Get classification result
	public ArrayList<Double> getClassificationResultSet() {
		return classificationResultSet;
	}

}
