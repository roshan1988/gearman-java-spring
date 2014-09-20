package com.roshan.gearman;

import org.apache.logging.log4j.Logger;

/**
 *
 * This class is used to create and start the gearman workers. The workers are
 * created according to the properties specified in the gearman.properties file
 *
 * @author Roshan Alexander
 *
 */
public class GearmanBootstrapper extends AbstractBootstrapper {

	/**
	 * @throws Exception
	 */
	protected GearmanBootstrapper() throws Exception {
		super();
	}

	// TODO Find how to get logger instance
	private static final Logger logger = null;

	private static final String PROCESS_NAME = "Gearman Workers Bootstrapper Process";

	@Override
	protected String[] getApplicationContextConfigurationFiles() {
		return new String[] { "spring/applicationContext-service.xml", "spring/applicationContext-batch.xml" };
	}

	public static void main(String[] args) throws Exception {
		GearmanBootstrapper gearmanBootstrapper = new GearmanBootstrapper();
	}

}
