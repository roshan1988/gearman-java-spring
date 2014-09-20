package com.roshan.gearman.job.submitters;

import org.apache.logging.log4j.Logger;
import org.gearman.GearmanClient;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;

import com.roshan.gearman.function.mapper.GearmanFunctionMapper;
import com.roshan.gearman.job.inputs.GearmanJobInput;
import com.roshan.gearman.job.jobresult.GearmanJobResult;
import com.roshan.gearman.utils.GearmanUtils;

/**
 * @author Roshan Alexander
 *
 */
public abstract class AbstractJobSubmitter<T extends GearmanJobInput, P extends GearmanJobResult> {

	private boolean gearmanEnabled;

	protected String functionName;

	protected GearmanClient gearmanClient;

	private GearmanFunctionMapper gearmanFunctionMapper;

	// TODO Get logger instance
	public static final Logger logger = null;

	Class<P> jobResultClassType;

	public AbstractJobSubmitter(Class<P> jobResultClassType) {
		this.jobResultClassType = jobResultClassType;
	}

	public P submitLocalJob(String functionName, T dataObject, GearmanFunctionCallback callback) throws Exception {
		GearmanFunction function = gearmanFunctionMapper.getGearmanFunction(functionName);
		byte[] inputBytes = GearmanUtils.getBytesFromObject(dataObject);
		byte[] resultBytes = function.work(functionName, inputBytes, callback);
		return getResultObjectFromBytes(resultBytes);
	}

	public P getResultObjectFromBytes(byte[] bytes) throws Exception {
		return GearmanUtils.getObjectFromBytes(bytes, jobResultClassType);
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
	 * @return the gearmanEnabled
	 */
	public boolean isGearmanEnabled() {
		return gearmanEnabled;
	}

	/**
	 * @param gearmanEnabled
	 *            the gearmanEnabled to set
	 */
	public void setGearmanEnabled(boolean gearmanEnabled) {
		this.gearmanEnabled = gearmanEnabled;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the gearmanClient
	 */
	public GearmanClient getGearmanClient() {
		return gearmanClient;
	}

	/**
	 * @param gearmanClient
	 *            the gearmanClient to set
	 */
	public void setGearmanClient(GearmanClient gearmanClient) {
		this.gearmanClient = gearmanClient;
	}
}
