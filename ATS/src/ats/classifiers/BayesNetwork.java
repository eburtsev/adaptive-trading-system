package ats.classifiers;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import ats.DataSet;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.gui.graphvisualizer.BIFFormatException;
import weka.gui.graphvisualizer.GraphVisualizer;

public class BayesNetwork {

	private BayesNet bayesNet = null;
	ArrayList<double[]> classificationResultSet = new ArrayList<double[]>();

	public BayesNetwork() {
		super();
	}

	// Build classifier
	public void buildBayesNet(Instances trainingData) {

		try {
			bayesNet = new BayesNet();
			// Train classifier
			bayesNet.buildClassifier(trainingData);
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
				classificationResult = bayesNet
						.distributionForInstance(testData.get(i));
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
			BayesNet untrainedBayesNet = new BayesNet();
			eval.crossValidateModel(untrainedBayesNet, newData, newData
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
			BayesNet cls = new BayesNet();
			trainingData.deleteAttributeAt(trainingData.numAttributes() - 2);
			cls.buildClassifier(trainingData);
			// display graph
			GraphVisualizer gv = new GraphVisualizer();
			gv.readBIF(cls.graph());
			JFrame jf = new JFrame("BayesNet graph");
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setSize(800, 600);
			jf.getContentPane().setLayout(new BorderLayout());
			jf.getContentPane().add(gv, BorderLayout.CENTER);
			jf.setVisible(true);
			// layout graph
			gv.layoutGraph();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BIFFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
