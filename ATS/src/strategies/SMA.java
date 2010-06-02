package strategies;

import java.util.ArrayList;

// simple moving average
public class SMA {

	private int SMA;
	private ArrayList closingPriceSet; 

	public SMA() {
		super();
	}

	// Get simple moving average
	public int getSMA(int SMADuration) {

		closingPriceSet = new ArrayList(SMADuration);
		
		// Generate closing price set for the past "SMADuration" days
		for (int i = 0; i < SMADuration; i++) {
			closingPriceSet.add(i, 0);
		}

		// Calculate SMA
		for (int i = 0; i < closingPriceSet.size(); i++) {
			SMA += (Integer) closingPriceSet.get(i);
			SMA = SMA / closingPriceSet.size();

		}

		return SMA;

	}

}
