package ats.classifiers;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesianLogisticRegression;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.graphvisualizer.GraphVisualizer;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import ats.DataSet;

public class BayesNetwork {

	private BayesianLogisticRegression bayesNet = null;

	public BayesNetwork() {
		super();
	}

	// Build classifier
	public void buildBayesNet(Instances trainingData) {

		try {
			bayesNet = new BayesianLogisticRegression(); // new instance of
															// bayesian network
			bayesNet.buildClassifier(trainingData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Classify test data set
	public void classify(Instances testData) {

		ArrayList<Double> classificationResultSet = new ArrayList<Double>();
		Double classificationResult = null;

		for (int i = 0; i < testData.numInstances(); i++) {

			try {
				classificationResult = bayesNet.classifyInstance(testData
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
			BayesianLogisticRegression untrainedDecitionTree = new BayesianLogisticRegression();
			eval.crossValidateModel(untrainedDecitionTree, newData, newData
					.numInstances(), new Random(1));
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	  public void getInternalModel(Instances trainingData) {
	  
		/*  
		BayesNet cls = new BayesNet();
		cls.buildClassifier(data);
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
		*/  
	  
	  }
	 

}
