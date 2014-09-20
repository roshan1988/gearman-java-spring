package com.roshan.gearman.job.submitters.imagescaling;

import com.roshan.gearman.job.inputs.imagescaling.ImageScaleJobInput;

/**
 * @author Roshan Alexander
 *
 */
public interface ImageScalingJobSubmitter {

	public void submitImageForScaling(ImageScaleJobInput imageScaleJobInput) throws Exception;

}
