package com.roshan.gearman.job.inputs.webcrawling;

import javax.xml.bind.annotation.XmlRootElement;

import com.roshan.gearman.job.inputs.GearmanJobInput;

/**
 * @author Roshan Alexander
 *
 */
@XmlRootElement
public class WebCrawlingJobInput implements GearmanJobInput {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String webUrl;

	@Override
	public String retreiveJobTrackId() {
		return webUrl;
	}

	/**
	 * @return the webUrl
	 */
	public String getWebUrl() {
		return webUrl;
	}

	/**
	 * @param webUrl
	 *            the webUrl to set
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

}
