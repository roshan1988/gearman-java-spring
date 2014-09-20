package com.roshan.gearman.function.mapper;

import org.gearman.GearmanFunction;

/**
 * Used as a mapper to retrieve the corresponding {@link GearmanFunction} object
 * from the string function name
 *
 * @author Roshan Alexander
 *
 */
public interface GearmanFunctionMapper {

	public GearmanFunction getGearmanFunction(String functionName);

}
