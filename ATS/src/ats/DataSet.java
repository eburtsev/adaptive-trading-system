package ats;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataSet {

	public DataSource source = null;
	public Instances instances = null;

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

		try {
			instances = source.getDataSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			if (instances.get(i + 1).value(instances.numAttributes() - 3) > instances
					.get(i).value(instances.numAttributes() - 3)) {
				instances.get(i).setValue(instances.numAttributes() - 1,
						"purchase");
			} else if (instances.get(i + 1)
					.value(instances.numAttributes() - 3) < instances.get(i)
					.value(instances.numAttributes() - 3)) {
				instances.get(i)
						.setValue(instances.numAttributes() - 1, "sale");
			} else if (instances.get(i + 1)
					.value(instances.numAttributes() - 3) == instances.get(i)
					.value(instances.numAttributes() - 3)) {
				instances.get(i).setValue(instances.numAttributes() - 1,
						"retain");
			}
		}

		// Make the last attribute be the class
		instances.setClassIndex(instances.numAttributes() - 1);

		// Print header and instances
		System.out.println("\nDataset:\n");
		System.out.println(instances);
		System.out.println(instances.numInstances());

	}

}