package com.roshan.gearman.function;

import org.apache.logging.log4j.Logger;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;

import com.roshan.gearman.job.inputs.GearmanJobInput;
import com.roshan.gearman.job.jobresult.GearmanJobResult;
import com.roshan.gearman.utils.GearmanUtils;

/**
 * Needs to be implemented by all the classes registering themselves as gearman
 * function.
 *
 * The implementing classes provides the implementation for {@code doWork()}
 * where the actual working logic for the function should reside. All the
 * gearman related functionalities such as serializing and deserializing the
 * objects from and to byte array are abstracted out by this class.
 *
 * @author Roshan Alexander
 *
 */
public abstract class AbstractGearmanFunction<T extends GearmanJobInput, P extends GearmanJobResult> implements
		GearmanFunction {

	private T jobInput;

	// TODO Get Instance of Logger
	public static final Logger logger = null;

	public T getObjectFromBytes(byte[] bytes) throws Exception {
		return GearmanUtils.getObjectFromBytes(bytes, getInputClassType());
	}

	@Override
	public byte[] work(String function, byte[] data, GearmanFunctionCallback callback) throws Exception {
		try {
			jobInput = getObjectFromBytes(data);
		} catch (Exception e) {
			logger.error("Error deserializing the job input object for gearman function : " + function, e);
			throw e;
		}

		logger.debug("Work with Id : " + jobInput.retreiveJobTrackId() + " taken for processing");
		P workResult = doWork(function, jobInput, callback);
		return getBytesFromObject(workResult);
	}

	/**
	 * @param workResult
	 * @return
	 * @throws Exception
	 */
	private byte[] getBytesFromObject(P workResult) throws Exception {
		return GearmanUtils.getBytesFromObject(workResult);
	}

	/**
	 * Returns the input object class type. This will be used internally by the
	 * object mappers to identify the target object type while deserializing
	 * from byte array.
	 *
	 * @return the input object class type
	 */
	public abstract Class<T> getInputClassType();

	/**
	 * The actual working logic for the gearman function happens here.
	 *
	 * @param function
	 * @param jobInput
	 * @param callback
	 * @return
	 */
	public abstract P doWork(String function, T jobInput, GearmanFunctionCallback callback);

}
