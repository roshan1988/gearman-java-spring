package com.roshan.gearman.job.submitters.webcrawling;

import com.roshan.gearman.job.inputs.webcrawling.WebCrawlingJobInput;
import com.roshan.gearman.job.jobresult.webcrawling.WebCrawlingJobResult;
import com.roshan.gearman.job.submitters.AbstractJobSubmitter;
import com.roshan.gearman.utils.GearmanUtils;

/**
 * @author Roshan Alexander
 *
 */
public class WebCrawlingJobSubmitterImpl extends AbstractJobSubmitter<WebCrawlingJobInput, WebCrawlingJobResult>
implements WebCrawlingJobSubmitter {

	/**
	 * @param jobResultClassType
	 */
	public WebCrawlingJobSubmitterImpl() {
		super(WebCrawlingJobResult.class);
	}

	@Override
	public void submitUrlForCrawling(WebCrawlingJobInput webCrawlingJobInput) {
		try {
			byte[] data = GearmanUtils.getBytesFromObject(webCrawlingJobInput);

			if (isGearmanEnabled()) {
				logger.debug("Submitting backgroung job for web crawling. Url : " + webCrawlingJobInput.getWebUrl());
				gearmanClient.submitBackgroundJob(functionName, data);
			} else {
				logger.debug("Submitting local job for image scaling. Url : " + webCrawlingJobInput.getWebUrl());
				submitLocalJob(functionName, webCrawlingJobInput, null);
			}
		} catch (Exception e) {
			logger.error("Error submitting the job for downscaling : Url : " + webCrawlingJobInput.getWebUrl(), e);
			setStatusSubmitFailed(webCrawlingJobInput.getWebUrl(), e);
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
