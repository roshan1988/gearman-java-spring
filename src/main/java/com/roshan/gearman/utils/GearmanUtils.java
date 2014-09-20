package com.roshan.gearman.utils;

import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.roshan.gearman.job.jobresult.DefaultGearmanJobResult;

/**
 * @author Roshan Alexander
 *
 */
public class GearmanUtils {

	// TODO Get logger Instance
	public static final Logger logger = null;

	/**
	 * Used to convert an object into its byte array representation. If the
	 * input entity is of type {@link DefaultGearmanJobResult} , then the
	 * {@code getBytes()} method will be used for getting the byte array. For
	 * all the other class types, Jacksons object mapper is used to convert the
	 * object to json string representation. The {@code getBytes()} on the json
	 * string is called to get the byte array.
	 *
	 * @param entity
	 * @return the byte array representation of the object
	 * @throws Exception
	 */
	public static byte[] getBytesFromObject(Object entity) throws Exception {
		if (entity == null) {
			return null;
		}
		if (entity instanceof DefaultGearmanJobResult) {
			return ((DefaultGearmanJobResult) entity).getBytes();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString;
		try {
			jsonString = objectMapper.writeValueAsString(entity);
			return jsonString.getBytes();
		} catch (Exception e) {
			logger.error("Cant convert the Object of type : " + entity.getClass() + " to JSON string", e);
			throw e;
		}
	}

	/**
	 * Used to obtain the object from the byte array representation. If the
	 * class type is {@link DefaultGearmanJobResult}, a new instance of
	 * {@link DefaultGearmanJobResult} wrapping the byte array will be returned.
	 * For all the other objects, the byte array will be converted to json
	 * string.The Jackson object mapper will be used to convert the json string
	 * to the object specified by the class type.
	 *
	 * @param bytes
	 * @param classType
	 *            The target object type
	 * @return object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <P> P getObjectFromBytes(byte[] bytes, Class<P> classType) throws Exception {
		if (bytes == null) {
			return null;
		} else if (classType.equals(DefaultGearmanJobResult.class)) {
			return (P) new DefaultGearmanJobResult(bytes);
		}
		ObjectMapper mapper = new ObjectMapper();
		P object = mapper.readValue(new String(bytes), classType);
		return object;
	}

}
