package ats.strategies;

import ats.DataSet;

// Technical analysis strategies
public class Strategies {

	public Strategies() {
		super();
	}

	// Calculate and insert technical analysis attributes into data set
	public void applyStrategies() {

		// Simple moving average
		SMA sma = new SMA();
		sma.getSMA(DataSet.instances, 5);

		// Exponential moving average
		EMA ema = new EMA();
		ema.getEMA(DataSet.instances, 10);

	}
}
