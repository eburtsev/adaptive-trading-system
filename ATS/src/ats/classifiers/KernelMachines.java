package ats.classifiers;

import ats.DataSet;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.Instances;

public class KernelMachines {

	private Instances instances;
	private RBFKernel RBFSupportVector;

	public KernelMachines(Instances instances) {
		this.instances = instances;

		try {
			RBFSupportVector = new RBFKernel(new DataSet().getInstances(), -1, 0.1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
