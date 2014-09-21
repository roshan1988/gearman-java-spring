package com.roshan.gearman.job.submitters.webcrawling;

import com.roshan.gearman.job.inputs.webcrawling.WebCrawlingJobInput;

/**
 * @author Roshan Alexander
 *
 */
public interface WebCrawlingJobSubmitter {

	public void submitUrlForCrawling(WebCrawlingJobInput webCrawlingJobInput);

}
