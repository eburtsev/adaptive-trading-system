package ats;

import java.util.ArrayList;
import java.util.List;

import ats.strategies.Strategies;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataSet {

	private DataSource source = null;
	public static Instances instances = null;
	private Instances testDataSet = null;
	private Instances trainingDataSet = null;

	public DataSet() {
		super();
	}

	public void generateDataSet() {

		// Read all the instances in the file (ARFF, CSV, XRFF, ...)
		try {
			source = new DataSource("data\\jblu.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create data set
		try {
			instances = source.getDataSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Reverse the order of instances in the data set to place them in
		// chronological order
		for (int i = 0; i < (instances.numInstances() / 2); i++) {
			instances.swap(i, instances.numInstances() - 1 - i);
		}

		// Remove "volume", "low price", "high price", "opening price" and
		// "data" from data set
		instances.deleteAttributeAt(instances.numAttributes() - 1);
		instances.deleteAttributeAt(instances.numAttributes() - 2);
		instances.deleteAttributeAt(instances.numAttributes() - 2);
		instances.deleteAttributeAt(instances.numAttributes() - 2);
		instances.deleteAttributeAt(instances.numAttributes() - 2);

		// Create list to hold nominal values "purchase", "sale", "retain"
		List my_nominal_values = new ArrayList(3);
		my_nominal_values.add("purchase");
		my_nominal_values.add("sale");
		my_nominal_values.add("retain");

		// Create nominal attribute "classIndex"
		Attribute classIndex = new Attribute("classIndex", my_nominal_values);

		// Add "classIndex" as an attribute to each instance
		instances.insertAttributeAt(classIndex, instances.numAttributes());

		// Set the value of "classIndex" for each instance
		for (int i = 0; i < instances.numInstances() - 1; i++) {
			if (instances.get(i + 1).value(instances.numAttributes() - 2) > instances
					.get(i).value(instances.numAttributes() - 2)) {
				instances.get(i).setValue(instances.numAttributes() - 1,
						"purchase");
			} else if (instances.get(i + 1)
					.value(instances.numAttributes() - 2) < instances.get(i)
					.value(instances.numAttributes() - 2)) {
				instances.get(i)
						.setValue(instances.numAttributes() - 1, "sale");
			} else if (instances.get(i + 1)
					.value(instances.numAttributes() - 2) == instances.get(i)
					.value(instances.numAttributes() - 2)) {
				instances.get(i).setValue(instances.numAttributes() - 1,
						"retain");
			}
		}

		// Make the last attribute be the class
		instances.setClassIndex(instances.numAttributes() - 1);

		// Calculate and insert technical analysis attributes into data set
		Strategies strategies = new Strategies();
		strategies.applyStrategies();

		// Print header and instances
		System.out.println("\nDataset:\n");
		System.out.println(instances);
		System.out.println(instances.numInstances());

	}

	// Create 70% training data set
	public void generateTrainingDataSet() {

		trainingDataSet = new Instances(instances);
		int size = trainingDataSet.numInstances();

		// Remove closing prize "close" attribute
		trainingDataSet.deleteAttributeAt(0);

		// Randomize data set
		trainingDataSet.randomize(trainingDataSet.getRandomNumberGenerator(1));

	}

	// Create 30% test data set
	public void generateTestDataSet() {

		testDataSet = new Instances(instances);
		int size = testDataSet.numInstances();

		// Remove closing prize "close" attribute
		testDataSet.deleteAttributeAt(0);

		// Randomize data set
		testDataSet.randomize(testDataSet.getRandomNumberGenerator(1));

	}

	public Instances getInstances() {
		Instances instancesCopy = new Instances(instances);
		return instancesCopy;
	}

	public Instances getTrainingDataSet() {
		return trainingDataSet;
	}

	public Instances getTestDataSet() {
		return testDataSet;
	}

}