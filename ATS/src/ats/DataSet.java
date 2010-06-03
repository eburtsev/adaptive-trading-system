package ats;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataSet {

	public DataSet() {
		super();
	}

	public void generateDataSet() {

		// Read all the instances in the file (ARFF, CSV, XRFF, ...)
		DataSource source = null;
		try {
			source = new DataSource("data\\jblu.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instances instances = null;
		try {
			instances = source.getDataSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Make the last attribute be the class
		instances.setClassIndex(instances.numAttributes() - 1);

		// Print header and instances
		System.out.println("\nDataset:\n");
		System.out.println(instances);
		System.out.println(instances.numInstances());

	}

}