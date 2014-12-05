package eu.ueb.acem.services.application;

import java.io.Serializable;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * A bean to manage ChainEdit Version.
 */
public class ApplicationService implements InitializingBean, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5370486400815436620L;

	/**
	 * The default copyright.
	 */
	private static final String DEFAULT_COPYRIGHT = "Copyright (c) 2014 Université Européenne de Bretagne";

	/**
	 * The default vendor.
	 */
	private static final String DEFAULT_VENDOR = "G.Colbert";

	/**
	 * A logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationService.class);
	/**
	 * The version major number.
	 */
	private Integer versionMajorNumber;
	/**
	 * The version minor number.
	 */
	private Integer versionMinorNumber;
	/**
	 * The version update.
	 */
	private Integer versionUpdate;

	/**
	 * Bean constructor.
	 */
	public ApplicationService() {
	}

	@Override
	public void afterPropertiesSet() {
		Validate.notNull(this.versionMajorNumber,
				"property versionMajorNumber of class "
						+ this.getClass().getName() + " can not be null");
		Validate.notNull(this.versionMinorNumber,
				"property versionMinorNumber of class "
						+ this.getClass().getName() + " can not be null");
		Validate.notNull(this.versionUpdate,
				"property versionUpdateNumber of class "
						+ this.getClass().getName() + " can not be null");
		logger.info("Starting Chainedit version " + getVersion() + "...");
	}

	/**
	 * @return The composed version
	 */
	public String getVersion() {
		return versionMajorNumber + "." + versionMinorNumber + "."
				+ versionUpdate;
	}

	/**
	 * @return The versionMajorNumber
	 */
	public Integer getVersionMajorNumber() {
		return versionMajorNumber;
	}

	/**
	 * @return The versionMinorNumber
	 */
	public Integer getVersionMinorNumber() {
		return versionMinorNumber;
	}

	/**
	 * @return The versionUpdate
	 */
	public Integer getVersionUpdate() {
		return versionUpdate;
	}

	/**
	 * @param versionMajorNumber
	 *            The versionMajorNumber to set.
	 */
	public void setVersionMajorNumber(final int versionMajorNumber) {
		this.versionMajorNumber = Integer.valueOf(versionMajorNumber);
	}

	/**
	 * @param versionMinorNumber
	 *            The versionMinorNumber to set.
	 */
	public void setVersionMinorNumber(final int versionMinorNumber) {
		this.versionMinorNumber = Integer.valueOf(versionMinorNumber);
	}

	/**
	 * @param versionUpdate
	 *            The versionUpdate to set.
	 */
	public void setVersionUpdate(final int versionUpdate) {
		this.versionUpdate = Integer.valueOf(versionUpdate);
	}

}
