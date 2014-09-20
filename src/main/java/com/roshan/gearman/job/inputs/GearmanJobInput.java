package com.roshan.gearman.job.inputs;

import java.io.Serializable;

/**
 * This interface needs to be implemented by all the classes that acts as
 * gearman job input.
 *
 * @author Roshan Alexander
 *
 */
public interface GearmanJobInput extends Serializable {

	public String retreiveJobTrackId();

}
