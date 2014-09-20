package com.roshan.gearman.job.inputs.imagescaling;

import javax.xml.bind.annotation.XmlRootElement;

import com.roshan.gearman.job.inputs.GearmanJobInput;

/**
 * @author Roshan Alexander
 *
 */
@XmlRootElement
public class ImageScaleJobInput implements GearmanJobInput {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String imageUrl;

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String retreiveJobTrackId() {
		return imageUrl;
	}

}
