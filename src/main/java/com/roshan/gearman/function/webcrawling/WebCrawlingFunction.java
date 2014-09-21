package com.roshan.gearman.function.webcrawling;

import org.gearman.GearmanFunctionCallback;

import com.roshan.gearman.function.AbstractGearmanFunction;
import com.roshan.gearman.job.inputs.webcrawling.WebCrawlingJobInput;
import com.roshan.gearman.job.jobresult.webcrawling.WebCrawlingJobResult;

/**
 * @author Roshan Alexander
 *
 */
public class WebCrawlingFunction extends AbstractGearmanFunction<WebCrawlingJobInput, WebCrawlingJobResult> {

	@Override
	public Class<WebCrawlingJobInput> getInputClassType() {
		return WebCrawlingJobInput.class;
	}

	@Override
	public WebCrawlingJobResult doWork(String function, WebCrawlingJobInput jobInput, GearmanFunctionCallback callback) {
		// TODO Actual logic for web crawling
		// TODO Remove the sleep() function that was used to mimic the web
		// crawling function
		try {
			Thread.sleep(5000);
			System.out.println("Doing web crawling for url : " + jobInput.getWebUrl());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// TODO replace the dummy created result object with actual
		WebCrawlingJobResult crawlingJobResult = new WebCrawlingJobResult();
		crawlingJobResult.setCrawledWebContent("Custom crawled content");
		crawlingJobResult.setHttpStatus(200);
		return crawlingJobResult;
	}

}
