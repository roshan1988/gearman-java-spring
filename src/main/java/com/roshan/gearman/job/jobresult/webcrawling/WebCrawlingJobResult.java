package com.roshan.gearman.job.jobresult.webcrawling;

import javax.xml.bind.annotation.XmlRootElement;

import com.roshan.gearman.job.jobresult.GearmanJobResult;

/**
 * @author Roshan Alexander
 *
 */
@XmlRootElement
public class WebCrawlingJobResult implements GearmanJobResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String crawledWebContent;

	int httpStatus;

	String httpStatusMessage;

	/**
	 * @return the crawledWebContent
	 */
	public String getCrawledWebContent() {
		return crawledWebContent;
	}

	/**
	 * @param crawledWebContent
	 *            the crawledWebContent to set
	 */
	public void setCrawledWebContent(String crawledWebContent) {
		this.crawledWebContent = crawledWebContent;
	}

	/**
	 * @return the httpStatus
	 */
	public int getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus
	 *            the httpStatus to set
	 */
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the httpStatusMessage
	 */
	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	/**
	 * @param httpStatusMessage
	 *            the httpStatusMessage to set
	 */
	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

}
