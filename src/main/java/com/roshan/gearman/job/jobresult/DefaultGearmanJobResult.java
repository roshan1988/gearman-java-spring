package com.roshan.gearman.job.jobresult;

/**
 * Default implementation for the {@link GearmanJobResult}
 *
 * @author Roshan Alexander
 *
 */
public class DefaultGearmanJobResult implements GearmanJobResult {

	private static final long serialVersionUID = 1L;

	byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public DefaultGearmanJobResult(byte[] bytes) {
		this.bytes = bytes;
	}
}
