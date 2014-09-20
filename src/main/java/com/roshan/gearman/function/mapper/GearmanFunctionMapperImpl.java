package com.roshan.gearman.function.mapper;

import java.util.Map;

import org.gearman.GearmanFunction;

/**
 * @author Roshan Alexander
 *
 */
public class GearmanFunctionMapperImpl implements GearmanFunctionMapper {

	private Map<String, GearmanFunction> functionMapper;

	@Override
	public GearmanFunction getGearmanFunction(String functionName) {
		return functionMapper.get(functionName);
	}

	/**
	 * @return the functionMapper
	 */
	public Map<String, GearmanFunction> getFunctionMapper() {
		return functionMapper;
	}

	/**
	 * @param functionMapper
	 *            the functionMapper to set
	 */
	public void setFunctionMapper(Map<String, GearmanFunction> functionMapper) {
		this.functionMapper = functionMapper;
	}

}
