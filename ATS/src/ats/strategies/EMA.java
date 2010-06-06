package ats.strategies;

import weka.core.Attribute;
import weka.core.Instances;

// Exponential moving average
public class EMA {

	public EMA() {
		super();
	}

	// Get exponential moving average
	public void getEMA(Instances instances, int EMADuration) {

		// Create exponential moving average "EMA" attribute
		Attribute EMA = new Attribute("EMA");
		instances.insertAttributeAt(EMA, instances.numAttributes() - 1);

		double simpleMovingAverage = 0;
		double exponentialMovingAverage = 0;
		double sum = 0;
		// Weighting multiplier
		double multiplier = (double) (2 / ((double)EMADuration + 1));

		if (instances.numInstances() >= EMADuration) {

			// Simple moving average of the first "EMADuration" instances
			for (int i = 0; i < EMADuration; i++) {
				sum += instances.get(i).value(instances.numAttributes() - 4);
			}
			simpleMovingAverage = sum / EMADuration;
			sum = 0;

			instances.get(EMADuration - 1).setValue(
					instances.numAttributes() - 2, simpleMovingAverage);
			simpleMovingAverage = 0;

			for (int i = EMADuration; i < instances.numInstances(); i++) {
				// Calculate EMA
				exponentialMovingAverage = (instances.get(i).value(
						instances.numAttributes() - 4) - instances.get(i - 1)
						.value(instances.numAttributes() - 2))
						* multiplier
						+ instances.get(i - 1).value(
								instances.numAttributes() - 2);

				// Insert EMA into data set
				instances.get(i).setValue(instances.numAttributes() - 2,
						exponentialMovingAverage);
			}

			// Calculate simple "non-moving" average for
			// the first "EMADuration - 1" instances
			for (int i = 0; i < EMADuration - 1; i++) {

				double tempSum = 0;
				double average = 0;

				for (int j = 0; j <= i; j++) {
					tempSum += instances.get(j).value(
							instances.numAttributes() - 4);
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
