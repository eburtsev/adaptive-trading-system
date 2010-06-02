package ats;

import java.io.File;

import weka.core.converters.CSVLoader;

public class ATS {
	
	private File jblu;
	private CSVLoader loader = new CSVLoader();
	
	
	
	loader.setSource(jblu);
	
	public ATS() {
		super();
	}
	
	

}
