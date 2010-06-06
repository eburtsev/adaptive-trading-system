package ats.strategies;

import weka.core.Attribute;
import weka.core.Instances;

// Simple moving average
public class SMA {

	public SMA() {
		super();
	}

	// Get simple moving average
	public void getSMA(Instances instances, int SMADuration) {

		// Create simple moving average "SMA" attribute
		Attribute SMA = new Attribute("SMA");
		instances.insertAttributeAt(SMA, instances.numAttributes() - 1);

		double simpleMovingAverage = 0;
		double sum = 0;

		if (instances.numInstances() >= SMADuration) {
			// Calculate SMA
			for (int i = SMADuration - 1; i < instances.numInstances(); i++) {
				for (int j = 0; j < SMADuration; j++) {
					sum += instances.get(i - j).value(
							instances.numAttributes() - 3);
				}
				simpleMovingAverage = sum / SMADuration;
				sum = 0;

				// Insert SMA into data set
				instances.get(i).setValue(instances.numAttributes() - 2,
						simpleMovingAverage);

			}

			// Calculate simple "non-moving" average for
			// the first "SMADuration - 1" instances
			for (int i = 0; i < SMADuration - 1; i++) {

				double tempSum = 0;
				double average = 0;

				for (int j = 0; j <= i; j++) {
					tempSum += instances.get(j).value(
							instances.numAttributes() - 3);
					average = tempSum / (j + 1);
				}

				instances.get(i).setValue(instances.numAttributes() - 2,
						average);

				average = 0;
				tempSum = 0;
			}
		}
	}
}
