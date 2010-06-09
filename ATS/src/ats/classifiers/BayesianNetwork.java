package ats.classifiers;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import ats.DataSet;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.gui.graphvisualizer.BIFFormatException;
import weka.gui.graphvisualizer.GraphVisualizer;

public class BayesianNetwork {

	private BayesNet bayesNet = null;

	public BayesianNetwork() {
		super();
	}
	
	// Build classifier
	public void buildBayesNet(Instances trainingData) {
		
		try {
			String[] options = new String[2];
			options[0] = "-Q weka.classifiers.bayes.net.search.SearchAlgorithm"; // Search algorithm
			options[1] = "-E weka.classifiers.bayes.net.estimate.SimpleEstimator"; // Estimator algorithm
			bayesNet = new BayesNet(); 
			bayesNet.setOptions(options);
			bayesNet.buildClassifier(trainingData); // Train classifier
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
				classificationResult = bayesNet.distributionForInstance(testData
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

		Instances newData = new Instances(DataSet.instances);

		// Remove closing prize "close" attribute
		newData.deleteAttributeAt(0);
		
		// Randomize data set
		newData.randomize(newData.getRandomNumberGenerator(1));

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
		
		// train classifier
		BayesNet cls = new BayesNet();
		try {
			cls.buildClassifier(trainingData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// display graph
		GraphVisualizer gv = new GraphVisualizer();
		try {
			gv.readBIF(cls.graph());
		} catch (BIFFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame jf = new JFrame("BayesNet graph");
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setSize(800, 600);
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(gv, BorderLayout.CENTER);
		jf.setVisible(true);
		// layout graph
		gv.layoutGraph();
	}

}
