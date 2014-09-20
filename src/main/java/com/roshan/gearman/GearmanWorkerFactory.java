package com.roshan.gearman;

import java.net.Inet4Address;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.gearman.Gearman;
import org.gearman.GearmanFunction;
import org.gearman.GearmanLostConnectionPolicy;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

import com.roshan.gearman.function.mapper.GearmanFunctionMapper;

/**
 * A factory bean that creates {@link GearmanWorker} objects.
 *
 * The workers are created based upon the properties specified in
 * gearman.properties
 *
 * @author Roshan Alexander
 *
 */
public class GearmanWorkerFactory implements FactoryBean<GearmanWorker[]>, InitializingBean {

	private Gearman gearman;
	private GearmanServer[] servers;
	private GearmanFunctionMapper gearmanFunctionMapper;

	private GearmanWorker[] gearmanWorkers;

	private static Properties gearmanProperties = new Properties();

	String workerSetName;

	public static final String BASE_PROPERTY_STRING = "com.roshan.gearman.workers.";
	public static final String COUNT_PROPERTY_STRING = ".count";
	public static final String MAX_CONCURRENT_JOBS_COUNT = ".max.concurrent.jobs";
	public static final String RECONNECT_PERIOD = ".reconnect.period";
	public static final String FUNCTIONS_PROPERTY_STRING = ".functions";

	public static final int DEFAULT_WORKERS_COUNT = 10;

	// TODO Get logger instance
	public static final Logger logger = null;

	@Override
	public void afterPropertiesSet() throws Exception {

		int workersCount = 0;
		String functionNames = null;
		Integer maximumConcurrency = null;
		Long reconnectPeriod = null;
		GearmanLostConnectionPolicy lostConnectionPolicy = null;
		String clientIdPrefix = workerSetName + "_" + Inet4Address.getLocalHost() + "_";

		ClassPathResource propsResource = new ClassPathResource("conf/gearman.properties");
		gearmanProperties.load(propsResource.getInputStream());

		String workersCountStr = gearmanProperties.getProperty(BASE_PROPERTY_STRING + workerSetName
				+ COUNT_PROPERTY_STRING);
		if ((workersCountStr != null) && !workersCountStr.isEmpty()) {
			workersCount = Integer.parseInt(workersCountStr);
		} else {
			workersCount = DEFAULT_WORKERS_COUNT;
		}

		functionNames = gearmanProperties.getProperty(BASE_PROPERTY_STRING + workerSetName + FUNCTIONS_PROPERTY_STRING);

		maximumConcurrency = getMaximumConcurrency(workerSetName);

		reconnectPeriod = getReconnectPeriod(workerSetName);

		logger.info("Initializing and starting the gearman workers for worker set : " + workerSetName
				+ ". Number of workers to start : " + workersCount);
		gearmanWorkers = new GearmanWorker[workersCount];
		for (int i = 0; i < workersCount; i++) {
			logger.info("Creating worker " + (i + 1) + " for worker set : " + workerSetName);
			GearmanWorker worker = gearman.createGearmanWorker();

			if (maximumConcurrency != null) {
				worker.setMaximumConcurrency(maximumConcurrency);
			}
			if (reconnectPeriod != null) {
				worker.setReconnectPeriod(reconnectPeriod, TimeUnit.MILLISECONDS);
			}
			if (lostConnectionPolicy != null) {
				worker.setLostConnectionPolicy(lostConnectionPolicy);
			}
			if (clientIdPrefix != null) {
				worker.setClientID(clientIdPrefix + i);
			}

			if (servers != null) {
				for (GearmanServer server : servers) {
					worker.addServer(server);
				}
			}

			attachFunctionsToWorker(worker, functionNames);

			gearmanWorkers[i] = worker;
			logger.info("Successfully created worker " + (i + 1) + " for worker set : " + workerSetName);
		}
	}

	/**
	 * @param workerSetName
	 * @return
	 */
	private Integer getMaximumConcurrency(String workerSetName) {
		String maxConcurrencyString = gearmanProperties.getProperty(BASE_PROPERTY_STRING + workerSetName
				+ MAX_CONCURRENT_JOBS_COUNT);
		if (maxConcurrencyString != null) {
			return Integer.valueOf(maxConcurrencyString);
		}
		return null;
	}

	/**
	 * @param workerSetName
	 * @return
	 */
	private Long getReconnectPeriod(String workerSetName) {
		String reconnectPeriodString = gearmanProperties.getProperty(BASE_PROPERTY_STRING + workerSetName
				+ RECONNECT_PERIOD);
		if (reconnectPeriodString != null) {
			return Long.valueOf(reconnectPeriodString);
		}
		return null;
	}

	/**
	 * This will attach the functions to the given worker. Multiple functions
	 * are given on a single string separated by ','
	 *
	 * @param worker
	 * @param functionNames
	 */
	private void attachFunctionsToWorker(GearmanWorker worker, String functionNames) {
		if (functionNames != null) {
			String[] functionNamesArray = functionNames.split(",");
			for (String functionName : functionNamesArray) {
				GearmanFunction function = gearmanFunctionMapper.getGearmanFunction(functionName.trim());
				if (function != null) {
					worker.addFunction(functionName, function);
				} else {
					logger.error("GearmanFunction does not exist for function name : " + functionName
							+ ". Continuing with adding other functions to the worker.");
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * @return the gearman
	 */
	public Gearman getGearman() {
		return gearman;
	}

	/**
	 * @param gearman
	 *            the gearman to set
	 */
	public void setGearman(Gearman gearman) {
		this.gearman = gearman;
	}

	/**
	 * @return the servers
	 */
	public GearmanServer[] getServers() {
		return servers;
	}

	/**
	 * @param servers
	 *            the servers to set
	 */
	public void setServers(GearmanServer[] servers) {
		this.servers = servers;
	}

	/**
	 * @return the gearmanWorkers
	 */
	public GearmanWorker[] getGearmanWorkers() {
		return gearmanWorkers;
	}

	/**
	 * @param gearmanWorkers
	 *            the gearmanWorkers to set
	 */
	public void setGearmanWorkers(GearmanWorker[] gearmanWorkers) {
		this.gearmanWorkers = gearmanWorkers;
	}

	@Override
	public GearmanWorker[] getObject() throws Exception {
		return gearmanWorkers;
	}

	@Override
	public Class<GearmanWorker[]> getObjectType() {
		return GearmanWorker[].class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * @return the gearmanFunctionMapper
	 */
	public GearmanFunctionMapper getGearmanFunctionMapper() {
		return gearmanFunctionMapper;
	}

	/**
	 * @param gearmanFunctionMapper
	 *            the gearmanFunctionMapper to set
	 */
	public void setGearmanFunctionMapper(GearmanFunctionMapper gearmanFunctionMapper) {
		this.gearmanFunctionMapper = gearmanFunctionMapper;
	}

	/**
	 * @return the workerSetName
	 */
	public String getWorkerSetName() {
		return workerSetName;
	}

	/**
	 * @param workerSetName
	 *            the workerSetName to set
	 */
	public void setWorkerSetName(String workerSetName) {
		this.workerSetName = workerSetName;
	}

}
