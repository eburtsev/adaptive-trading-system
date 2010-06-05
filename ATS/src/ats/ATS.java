package ats;

public class ATS {

	public ATS() {
		super();
	}

	public void runATS() {

		DataSet dataSet = new DataSet();
		dataSet.generateDataSet();
		dataSet.generateTrainingDataSet();
		dataSet.generateTestDataSet();

	}

	public static void main(String[] args) {

		ATS ats = new ATS();
		ats.runATS();

	}

}
