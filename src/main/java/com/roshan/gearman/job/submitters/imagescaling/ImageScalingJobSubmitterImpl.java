package com.roshan.gearman.job.submitters.imagescaling;

import org.apache.logging.log4j.Logger;

import com.roshan.gearman.job.inputs.imagescaling.ImageScaleJobInput;
import com.roshan.gearman.job.jobresult.DefaultGearmanJobResult;
import com.roshan.gearman.job.submitters.AbstractJobSubmitter;
import com.roshan.gearman.utils.GearmanUtils;

/**
 * @author Roshan Alexander
 *
 */
public class ImageScalingJobSubmitterImpl extends AbstractJobSubmitter<ImageScaleJobInput, DefaultGearmanJobResult>
implements ImageScalingJobSubmitter {

	/**
	 * @param jobResultClassType
	 */
	public ImageScalingJobSubmitterImpl() {
		super(DefaultGearmanJobResult.class);
	}

	// TODO Get logger instance
	private static Logger logger = null;

	@Override
	public void submitImageForScaling(ImageScaleJobInput imageScaleJobInput) {

		try {
			byte[] data = GearmanUtils.getBytesFromObject(imageScaleJobInput);

			if (isGearmanEnabled()) {
				logger.debug("Submitting backgroung job for image scaling. Url : " + imageScaleJobInput.getImageUrl());
				gearmanClient.submitBackgroundJob(functionName, data);
			} else {
				logger.debug("Submitting local job for image scaling. Url : " + imageScaleJobInput.getImageUrl());
				submitLocalJob(functionName, imageScaleJobInput, null);
			}
		} catch (Exception e) {
			logger.error("Error submitting the job for downscaling : Url : " + imageScaleJobInput.getImageUrl(), e);
			setStatusSubmitFailed(imageScaleJobInput.getImageUrl(), e);
		}
	}

	/**
	 * @param imageUrl
	 * @param e
	 */
	private void setStatusSubmitFailed(String imageUrl, Exception e) {
		// TODO Set the submit status as failed either in DB or log the
		// exception
	}

}
